package org.eclipse.osc.orchestrator.plugin.huaweicloud;

import java.util.Optional;
import org.eclipse.osc.modules.ocl.loader.Ocl;
import org.eclipse.osc.orchestrator.plugin.huaweicloud.builders.HuaweiEnvBuilder;
import org.eclipse.osc.orchestrator.plugin.huaweicloud.builders.HuaweiImageBuilder;
import org.eclipse.osc.orchestrator.plugin.huaweicloud.builders.HuaweiResourceBuilder;

public class BuilderFactory {

    public static final String ENV_BUILDER = "env";

    public static final String BASIC_BUILDER = "basic";

    public Optional<AtomBuilder> createBuilder(String builderType, Ocl ocl) {
        if (builderType.equals(ENV_BUILDER)) {
            HuaweiEnvBuilder envBuilder = new HuaweiEnvBuilder(ocl);
            return Optional.of(envBuilder);
        }
        if (builderType.equals(BASIC_BUILDER)) {
            HuaweiImageBuilder imageBuilder = new HuaweiImageBuilder(ocl);
            HuaweiResourceBuilder resourceBuilder = new HuaweiResourceBuilder(ocl);
            resourceBuilder.addSubBuilder(imageBuilder);
            return Optional.of(resourceBuilder);
        }
        return Optional.empty();
    }
}
