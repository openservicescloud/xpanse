/*
 * SPDX-License-Identifier: Apache-2.0
 * SPDX-FileCopyrightText: Huawei Inc.
 *
 */

package org.eclipse.xpanse.modules.engine;

import java.util.Map;
import lombok.Data;

/**
 * Tasks performed by Terraform engine deployment.
 */
@Data
public class XpanseDeployTask {

    private String serviceName;
    private String serviceVersion;
    private String taskId;
    private String state;
    private String csp;
    private Map<String, String> context;
    private String command;
    private String response;
    private String result;

}
