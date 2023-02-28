/*
 * SPDX-License-Identifier: Apache-2.0
 * SPDX-FileCopyrightText: Huawei Inc.
 *
 */

package org.eclipse.xpanse.modules.engine;

import org.eclipse.xpanse.modules.engine.xpresource.XpResource;

/**
 * This interface describes the cpuUsage and memUsage for querying the server.
 */
public interface XpanseMonitor {

    /**
     * Method to git service cpuUsage.
     *
     * @param xpResource the xpanseResource.
     * @param task       the xpanseDeployTask.
     */
    String cpuUsage(XpResource xpResource, XpanseDeployTask task);

    /**
     * Method to git service memUsage.
     *
     * @param xpResource the xpanseResource.
     * @param task       the xpanseDeployTask.
     */
    String memUsage(XpResource xpResource, XpanseDeployTask task);

}
