/*
 * SPDX-License-Identifier: Apache-2.0
 * SPDX-FileCopyrightText: Huawei Inc.
 *
 */

package org.eclipse.xpanse.modules.engine.monitor.resource;

import lombok.Data;

/**
 * @Description: Monitor required resource parameters
 * @ClassName: MonitorResource
 * @Author:
 * @Date: 2023/2/20 15:02
 * @Version: 1.0
 */
@Data
public class MonitorResource {

    String region;
    String namespace;
    String metricName;
    String dim0;
    Integer period;
    String filter;
    String from;
    String to;
}
