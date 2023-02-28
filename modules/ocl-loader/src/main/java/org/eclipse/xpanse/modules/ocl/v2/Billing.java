/*
 * SPDX-License-Identifier: Apache-2.0
 * SPDX-FileCopyrightText: Huawei Inc.
 *
 */

package org.eclipse.xpanse.modules.ocl.v2;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jdk.jfr.Description;
import lombok.Data;

/**
 * Defines the billing model of the service.
 */
@Data
@Valid
public class Billing {

    @NotNull
    @NotBlank
    @NotEmpty
    @Description("The business model of Billing.")
    private String model;
    @NotNull
    @NotBlank
    @NotEmpty
    @Description("The period of Billing.")
    private String period;
    @NotNull
    @NotBlank
    @NotEmpty
    @Description("The currency of Billing.")
    private String currency;
}
