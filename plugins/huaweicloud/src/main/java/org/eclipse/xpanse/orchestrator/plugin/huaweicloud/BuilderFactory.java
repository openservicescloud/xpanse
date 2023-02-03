/*
 * SPDX-License-Identifier: Apache-2.0
 * SPDX-FileCopyrightText: Huawei Inc.
 *
 */

package org.eclipse.xpanse.orchestrator.plugin.huaweicloud;

import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.xpanse.modules.ocl.loader.data.models.Ocl;
import org.eclipse.xpanse.orchestrator.plugin.huaweicloud.builders.HuaweiEnvBuilder;
import org.eclipse.xpanse.orchestrator.plugin.huaweicloud.builders.HuaweiImageBuilder;
import org.eclipse.xpanse.orchestrator.plugin.huaweicloud.builders.HuaweiResourceBuilder;

/**
 * Factory class to instantiate builder object.
 */
@Slf4j
public class BuilderFactory {

    public static final String ENV_BUILDER = "env";

    public static final String BASIC_BUILDER = "basic";

    public static final String BASIC_BUILDER_DEPRECATED = "basic_deprecated";


    /**
     * Factory method that instantiates complete builder object.
     *
     * @param builderType Type of the builder.
     * @param ocl         Complete Ocl descriptor of the managed service to be deployed.
     * @return AtomBuilder object.
     */
    public Optional<AtomBuilder> createBuilder(String builderType, Ocl ocl) {
        if (builderType.equals(ENV_BUILDER)) {
            return Optional.of(new HuaweiEnvBuilder(ocl));
        }
        if (builderType.equals(BASIC_BUILDER)) {
            return Optional.of(new HuaweiResourceBuilder(ocl));
        }
        if (builderType.equals(BASIC_BUILDER_DEPRECATED)) {
            HuaweiImageBuilder imageBuilder = new HuaweiImageBuilder(ocl);
            HuaweiResourceBuilder resourceBuilder = new HuaweiResourceBuilder(ocl);
            resourceBuilder.addSubBuilder(imageBuilder);
            return Optional.of(resourceBuilder);
        }
        log.info("BuilderType:{} not supported.", builderType);
        return Optional.empty();
    }
}
