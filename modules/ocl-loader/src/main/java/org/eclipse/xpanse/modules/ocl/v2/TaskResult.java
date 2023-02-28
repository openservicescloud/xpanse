/*
 * SPDX-License-Identifier: Apache-2.0
 * SPDX-FileCopyrightText: Huawei Inc.
 *
 */

package org.eclipse.xpanse.modules.ocl.v2;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;


/**
 * Defines model of the result of the service task.
 */
@Data
@Valid
public class TaskResult {

    @NotBlank
    @NotEmpty
    @Schema(description = "The uuid of task.")
    private String taskId;
    @NotBlank
    @NotEmpty
    @Schema(description = "The state of task.")
    private String state;
    @NotBlank
    @NotEmpty
    @Schema(description = "The response of task.")
    private String response;
    @NotBlank
    @NotEmpty
    @Schema(description = "The result of task.")
    private String result;
}
