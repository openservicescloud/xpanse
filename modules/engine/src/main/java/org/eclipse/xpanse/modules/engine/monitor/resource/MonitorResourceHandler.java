/*
 * SPDX-License-Identifier: Apache-2.0
 * SPDX-FileCopyrightText: Huawei Inc.
 *
 */

package org.eclipse.xpanse.modules.engine.monitor.resource;

import java.util.Date;
import org.eclipse.xpanse.modules.engine.xpresource.XpanseResource;
import org.springframework.stereotype.Component;

/**
 * Resource conversion.
 */
@Component
public class MonitorResourceHandler {

    /**
     * resource conversion.
     *
     * @param xpanseResource Service Source.
     * @return MonitorResource Monitor Source.
     */
    public MonitorResource handler(XpanseResource xpanseResource) {
        MonitorResource monitorResource = new MonitorResource();
        String id = xpanseResource.getXpResourceKind().getXpResourceVm().getId();
        monitorResource.setDim0("instance_id," + id);
        monitorResource.setRegion(xpanseResource.getRegion());
        monitorResource.setNamespace("SYS.ECS");
        monitorResource.setPeriod(1);
        monitorResource.setFilter("average");
        monitorResource.setFrom(String.valueOf(new Date().getTime() - 5 * 60 * 1000));
        monitorResource.setTo(String.valueOf(new Date().getTime()));

        return monitorResource;
    }
}
