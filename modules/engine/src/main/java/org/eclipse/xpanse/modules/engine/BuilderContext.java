/*
 * SPDX-License-Identifier: Apache-2.0
 * SPDX-FileCopyrightText: Huawei Inc.
 *
 */

package org.eclipse.xpanse.modules.engine;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.eclipse.xpanse.modules.engine.ocl.OclResource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Class to hold all runtime information of the builder.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Component
public class BuilderContext extends HashMap<String, Map<String, String>> {

    private Environment environment;

    private OclResource oclResource;
}
