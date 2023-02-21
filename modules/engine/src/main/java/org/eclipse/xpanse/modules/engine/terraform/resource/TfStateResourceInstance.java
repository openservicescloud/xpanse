/*
 * SPDX-License-Identifier: Apache-2.0
 * SPDX-FileCopyrightText: Huawei Inc.
 *
 */

package org.eclipse.xpanse.modules.engine.terraform.resource;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.Map;

/**
 * TfStateResourceInstance class.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class TfStateResourceInstance {

    public Map<String, Object> attributes;

}
