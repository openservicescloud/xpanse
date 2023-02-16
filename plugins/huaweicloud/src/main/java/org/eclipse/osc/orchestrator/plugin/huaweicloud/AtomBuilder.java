package org.eclipse.osc.orchestrator.plugin.huaweicloud;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.osc.modules.ocl.loader.Ocl;
import org.eclipse.osc.orchestrator.OrchestratorStorage;
import org.eclipse.osc.orchestrator.plugin.huaweicloud.enums.BuilderState;

@Slf4j
public abstract class AtomBuilder {

    private static final long WAIT_AND_CHECK_TIME = 5;
    private static final long BUILDING_TIMEOUT_DEFAULT = 100;
    protected final Ocl ocl;
    private final List<AtomBuilder> subBuilders = new ArrayList<>();
    ObjectMapper objectMapper = new ObjectMapper();
    @Setter
    @Getter
    private BuilderState state;
    @Setter
    @Getter
    private long timeout = TimeUnit.MICROSECONDS.toSeconds(300);
    @Setter
    @Getter
    private String lastFail;
    @Getter
    private AtomBuilder parent;

    public AtomBuilder(Ocl ocl) {
        this.ocl = ocl;
    }

    public String name() {
        return "AtomBuilder";
    }

    /**
     * Add a sub-builder for this. The current builder will not start to build until all the sub
     * builders were successfully built.
     */
    public void addSubBuilder(AtomBuilder builder) {
        builder.parent = this;
        subBuilders.add(builder);
    }

    public List<AtomBuilder> getSubBuilders() {
        return subBuilders;
    }

    /**
     * If return true, that means the builder is working in progress.
     *
     * @return Ether or not need to be waiting.
     */
    private boolean needWaiting() {
        return state == BuilderState.DELETING || state == BuilderState.RUNNING;
    }

    private boolean waitSubBuilders() {
        long startTime = System.currentTimeMillis();
        long timeToWait = TimeUnit.MICROSECONDS.toSeconds(BUILDING_TIMEOUT_DEFAULT);
        if (!subBuilders.isEmpty()) {
            timeToWait = subBuilders.stream()
                .max(Comparator.comparing(AtomBuilder::getTimeout))
                .get()
                .getTimeout();
        }

        while (!subBuilders.isEmpty() && subBuilders.stream().allMatch(AtomBuilder::needWaiting)) {
            try {
                TimeUnit.SECONDS.sleep(WAIT_AND_CHECK_TIME);
            } catch (InterruptedException ex) {
                log.warn("Timeout", ex);
                Thread.currentThread().interrupt();
            }

            if ((System.currentTimeMillis() - startTime) > timeToWait) {
                setState(BuilderState.FAILED);
                setLastFail("Builder Timeout " + name());
                return false;
            }
        }
        return true;
    }

    /**
     * Builders will be started in sequence. Util all the sub builders going to be successful. Then
     * the current builder will start to build.
     *
     * @param ctx The building context for the whole building progress.
     * @return The building result, true or false. More detailed state will be present by @state.
     */
    public boolean build(BuilderContext ctx) {
        boolean succeed = true;
        setState(BuilderState.RUNNING);
        ctx.getOclResources().setState("building");
        storeOclResources(ctx);
        try {
            for (AtomBuilder subBuilder : subBuilders) {
                if (!subBuilder.build(ctx)) {
                    succeed = false;
                    setState(BuilderState.FAILED);
                    log.error("subBuilder:{} build failed.", subBuilder.name());
                    break;
                }
            }
            if (!waitSubBuilders()) {
                log.error("Wait sub builders failed.");
            }
            if (subBuilders.stream()
                .anyMatch(builder -> builder.getState() == BuilderState.FAILED)) {
                succeed = false;
                // Todo: give out the specified failed reason with setLastFail().
            }
            if (!create(ctx)) {
                succeed = false;
            }
            if (!succeed) {
                rollbackAfterBuildFailed(ctx);
                setState(BuilderState.FAILED);
                ctx.getOclResources().setState("failed");
                storeOclResources(ctx);
            } else {
                setState(BuilderState.SUCCESS);
                ctx.getOclResources().setState("success");
                storeOclResources(ctx);
            }
        } catch (Exception e) {
            log.error("AtomBuilder build failed! exception:{}", e.getMessage());
            rollbackAfterBuildFailed(ctx);
            setState(BuilderState.FAILED);
            ctx.getOclResources().setState("failed");
            storeOclResources(ctx);
        }
        return succeed;
    }

    /**
     * Builders will be started to roll back in sequence.
     *
     * @param ctx The building context for the whole building progress.
     */
    private void rollbackAfterBuildFailed(BuilderContext ctx) {
        setState(BuilderState.FAILED);
        AtomBuilder envBuilder = ctx.getBuilderMap().get(BuilderFactory.ENV_BUILDER);
        envBuilder.rollback(ctx);
        AtomBuilder basicBuilder = ctx.getBuilderMap()
            .get(BuilderFactory.BASIC_BUILDER);
        basicBuilder.rollback(ctx);
        ctx.getOclResources().setState("failed");
        storeOclResources(ctx);
    }

    /**
     * Builders will be started to roll back in sequence. The current builder will begin to roll
     * back first. Due to the try-my-best strategy, the @rollback() result may be ignored.
     *
     * @param ctx The building context for the whole building progress.
     * @return The rollback result, true or false. More detailed state will be present by @state.
     */
    public boolean rollback(BuilderContext ctx) {
        setState(BuilderState.DELETING);

        if (!destroy(ctx)) {
            log.error("Builder: {} destroy failed.", name());
            setLastFail("Builder destroy failed." + name());
            setState(BuilderState.FAILED);
        } else {
            setState(BuilderState.PENDING);
        }

        for (AtomBuilder subBuilder : subBuilders) {
            if (!subBuilder.rollback(ctx)) {
                setState(BuilderState.FAILED);
            }
        }

        if (!waitSubBuilders()) {
            log.error("Wait sub builders failed.");
        }

        return getState() == BuilderState.PENDING;
    }


    private void storeOclResources(BuilderContext ctx) {
        String oclResourceStr;
        try {
            oclResourceStr = objectMapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(ctx.getOclResources());
        } catch (JsonProcessingException ex) {
            throw new IllegalStateException("Serial OCL object to json failed.", ex);
        }
        OrchestratorStorage storage = ctx.getStorage();
        if (storage != null) {
            storage.store(ctx.getServiceName(), ctx.getPluginName(), "state", oclResourceStr);
        } else {
            log.warn("storage is null.");
        }
    }

    /**
     * Creating actions for current builder.
     *
     * @param ctx The building context for the whole building progress.
     * @return The creating result, true or false.
     */
    public abstract boolean create(BuilderContext ctx);

    /**
     * Destroying actions for current builder.
     *
     * @param ctx The building context for the whole building progress.
     * @return The creating result, true or false.
     */
    public abstract boolean destroy(BuilderContext ctx);
}
