/*
 * SPDX-License-Identifier: Apache-2.0
 * SPDX-FileCopyrightText: Huawei Inc.
 *
 */

package org.eclipse.xpanse.modules.engine;

import java.util.List;
import org.eclipse.xpanse.modules.engine.xpresource.XpanseResource;

/**
 * this Interface Process the execution result of XpanseDeployEngine and return the XpanseResource
 * format.
 */
public interface XpanseHandler {

    List<XpanseResource> handler(XpanseDeployTask task);

}
