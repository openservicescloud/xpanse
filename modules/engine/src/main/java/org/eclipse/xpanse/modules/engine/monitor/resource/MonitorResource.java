package org.eclipse.xpanse.modules.engine.monitor.resource;

import lombok.Data;

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
