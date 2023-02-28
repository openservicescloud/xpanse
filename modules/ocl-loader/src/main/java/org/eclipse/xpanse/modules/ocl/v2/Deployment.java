/*
 * SPDX-License-Identifier: Apache-2.0
 * SPDX-FileCopyrightText: Huawei Inc.
 *
 */

package org.eclipse.xpanse.modules.ocl.v2;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Data;


/**
 * Defines model of the deployment of the service.
 */
@Data
@Valid
public class Deployment {

    @NotNull
    @Schema(description = "Context of the deployment")
    private List<Context> context;
    @NotNull
    @Schema(description = "Deployer of the deployment")
    private Deployer deployer;

}
