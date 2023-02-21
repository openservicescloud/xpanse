package org.eclipse.xpanse.modules.engine.terraform.builder;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.xpanse.modules.engine.AtomBuilder;
import org.eclipse.xpanse.modules.engine.BuilderContext;
import org.eclipse.xpanse.modules.engine.exceptions.BuilderException;
import org.springframework.core.env.Environment;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class TFEnvironmentBuilder extends AtomBuilder {

    public static final String ACCESS_KEY = "ACCESS_KEY";
    public static final String SECRET_KEY = "SECRET_KEY";
    public static final String REGION_NAME = "REGION_NAME";
    public static final String ENTERPRISE_PROJECT_ID = "ENTERPRISE_PROJECT_ID";

    @Override
    public String name() {
        return "Env-Builder";
    }

    @Override
    public boolean create(BuilderContext context) {
        log.info("Start build Cloud Variable environment.");
        prepareEnv(context);
        return true;
    }

    @Override
    public boolean destroy(BuilderContext context) {
        log.info("Start Destroy cloud Variable environment.");
        prepareEnv(context);
        return true;
    }

    private boolean needRebuild(BuilderContext context) {
        return context.get(name()) == null;
    }

    private void prepareEnv(BuilderContext context) {
        if (context == null) {
            log.error("Builder Context is null.");
            throw new BuilderException(this, "Builder Context is null.");
        }
        if (!needRebuild(context)) {
            return;
        }
        Environment environment = context.getEnvironment();
        if (environment == null) {
            log.error("configCtx not found, in BuilderContext.");
            throw new BuilderException(this, "configCtx not found, in BuilderContext.");
        }

        String accessKey = environment.getProperty(ACCESS_KEY);
        String secretKey = environment.getProperty(SECRET_KEY);
        String region = environment.getProperty(REGION_NAME);

        Map<String, String> envCtx = new HashMap<>();
        if (accessKey != null) {
            envCtx.put(ACCESS_KEY, accessKey);
        }
        if (secretKey != null) {
            envCtx.put(SECRET_KEY, secretKey);
        }
        if (region != null) {
            envCtx.put(REGION_NAME, region);
        }
        String projectId = environment.getProperty(ENTERPRISE_PROJECT_ID);
        if (projectId != null) {
            envCtx.put(ENTERPRISE_PROJECT_ID, projectId);
        }
        context.put(name(), envCtx);
    }
}
