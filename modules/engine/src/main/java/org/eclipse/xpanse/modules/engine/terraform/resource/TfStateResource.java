/*
 * SPDX-License-Identifier: Apache-2.0
 * SPDX-FileCopyrightText: Huawei Inc.
 *
 */

package org.eclipse.xpanse.modules.engine.terraform.resource;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

/**
 * TfStateResource class.
 **/
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class TfStateResource {

    public String type;

    public String name;

    public List<TfStateResourceInstance> instances;
}
