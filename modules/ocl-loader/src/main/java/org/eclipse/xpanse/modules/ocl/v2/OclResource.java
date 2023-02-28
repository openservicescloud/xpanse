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
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Data;


/**
 * Defines the OclResource model of the service.
 */
@Data
@Valid
public class OclResource {

    @NotNull
    @NotBlank
    @NotEmpty
    @Schema(description = "Name of the xpanse")
    private String version;
    @NotNull
    @NotBlank
    @NotEmpty
    @Schema(description = "Name of the service")
    private String name;
    @NotNull
    @NotBlank
    @NotEmpty
    @Schema(description = "Description of the service")
    private String description;
    @NotNull
    @NotBlank
    @NotEmpty
    @Schema(description = "Namespace of the service")
    private String namespace;
    @NotNull
    @NotBlank
    @NotEmpty
    @Schema(description = "Icon of cloud service provider")
    private String icon;
    //CHECKSTYLE OFF: naming
    @NotNull
    @NotBlank
    @NotEmpty
    @Schema(description = "Version of the service")
    private String service_version;
    @NotNull
    @Schema(description = "Cloud service provider info")
    private CloudServiceProvider cloud_service_provider;
    //CHECKSTYLE ON: naming
    @NotNull
    @Schema(description = "Deployment of the service")
    private Deployment deployment;

    @NotNull
    @Schema(description = "List of the service flavor")
    private List<Flavor> flavors;

    @NotNull
    @Schema(description = "Billing of the service")
    private Billing billing;


}
