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
import java.util.Map;
import jdk.jfr.Description;
import lombok.Data;


/**
 * Defines model of the service flavor.
 */
@Data
@Valid
public class Flavor {

    @NotBlank
    @Description("The name of service flavor.")
    private String name;
    @NotNull
    @Description("The fixed price of service flavor.")
    private int fixedPrice;
    @NotEmpty
    @Description("The property map of service flavor.")
    private Map<String, Object> property;

}
