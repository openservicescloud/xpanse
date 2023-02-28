/*
 * SPDX-License-Identifier: Apache-2.0
 * SPDX-FileCopyrightText: Huawei Inc.
 *
 */

package org.eclipse.xpanse.modules.engine;

import java.util.List;
import lombok.Data;
import org.eclipse.xpanse.modules.engine.xpresource.XpanseResource;

/**
 * Response of the Terraform Deploy Source.
 */
@Data
public class XpanseDeployResponse {

    private XpanseDeployTask task;
    private List<XpanseResource> resources;

}
