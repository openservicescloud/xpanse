/*
 * SPDX-License-Identifier: Apache-2.0
 * SPDX-FileCopyrightText: Huawei Inc.
 *
 */

package org.eclipse.xpanse.modules.engine.monitor.resource;

import java.util.Date;
import org.eclipse.xpanse.modules.engine.xpresource.XpResource;
import org.springframework.stereotype.Component;

/**
 * @Description: Resource conversion
 * @ClassName: MonitorResourceHandler
 * @Author:
 * @Date: 2023/2/20 15:02
 * @Version: 1.0
 */
@Component
public class MonitorResourceHandler {

    public MonitorResource handler(XpResource xpResource) {
        MonitorResource monitorResource = new MonitorResource();
        String id = xpResource.getXpResourceKind().getXpResourceVm().getId();
        monitorResource.setDim0("instance_id," + id);
        monitorResource.setRegion(xpResource.getRegion());
        monitorResource.setNamespace("SYS.ECS");
        monitorResource.setPeriod(1);
        monitorResource.setFilter("average");
        monitorResource.setFrom(String.valueOf(new Date().getTime() - 5 * 60 * 1000));
        monitorResource.setTo(String.valueOf(new Date().getTime()));

        return monitorResource;
    }
}
