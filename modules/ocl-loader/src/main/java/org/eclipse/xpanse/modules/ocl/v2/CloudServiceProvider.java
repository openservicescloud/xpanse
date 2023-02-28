/*
 * SPDX-License-Identifier: Apache-2.0
 * SPDX-FileCopyrightText: Huawei Inc.
 *
 */

package org.eclipse.xpanse.modules.ocl.v2;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import jdk.jfr.Description;
import lombok.Data;

/**
 * Defines the model of the cloud service provider.
 */
@Data
@Valid
public class CloudServiceProvider {

    @NotBlank
    @Description("The name of provider.")
    private String name;
    @Description("The list of region.")
    private List<String> regions;
}
