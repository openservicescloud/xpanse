package org.eclipse.osc.orchestrator.plugin.huaweicloud;

import org.eclipse.osc.modules.ocl.loader.Ocl;
import org.eclipse.osc.orchestrator.plugin.huaweicloud.builders.HuaweiEnvBuilder;
import org.eclipse.osc.orchestrator.plugin.huaweicloud.builders.HuaweiImageBuilder;
import org.eclipse.osc.orchestrator.plugin.huaweicloud.builders.HuaweiResourceBuilder;

public class BuilderFactory {

    public static final String ENV_BUILDER = "env";

    public static final String BASIC_BUILDER = "basic";

    public AtomBuilder createBuilder(String builderType, Ocl ocl) {
        if (builderType.equals(ENV_BUILDER)) {
            return new HuaweiEnvBuilder(ocl);
        }
        if (builderType.equals(BASIC_BUILDER)) {
            return new HuaweiResourceBuilder(ocl);
        }
        throw new IllegalArgumentException("BuilderType:" + builderType + " not supported.");
    }
}
