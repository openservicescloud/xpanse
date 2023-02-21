/*
 * SPDX-License-Identifier: Apache-2.0
 * SPDX-FileCopyrightText: Huawei Inc.
 *
 */

package org.eclipse.xpanse.modules.engine.terraform.builder;

import org.eclipse.xpanse.modules.engine.AtomBuilder;

/**
 * Factory class to instantiate builder object.
 */
public class TFBuilderFactory {

    public static final String ENV_BUILDER = "env";
    public static final String BASIC_BUILDER = "basic";

    /**
     * Factory method that instantiates complete builder object.
     *
     * @param builderType Type of the builder.
     * @return AtomBuilder object.
     */
    public AtomBuilder createBuilder(String builderType) {
        if (builderType.equals(ENV_BUILDER)) {
            return new TFEnvironmentBuilder();
        } else if (builderType.equals(BASIC_BUILDER)) {
            return new TFResourceBuilder();
        }
        throw new IllegalStateException("Builder Type is in valid.");
    }
}
