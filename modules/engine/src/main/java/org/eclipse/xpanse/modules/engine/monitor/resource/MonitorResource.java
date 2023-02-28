/*
 * SPDX-License-Identifier: Apache-2.0
 * SPDX-FileCopyrightText: Huawei Inc.
 *
 */

package org.eclipse.xpanse.modules.engine.monitor.resource;

import lombok.Data;

/**
 * Monitor required resource parameters.
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
