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
 * TfState class.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class TfState {

    public List<TfStateResource> resources;
}
