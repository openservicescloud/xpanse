package org.eclipse.xpanse.modules.engine;

import lombok.Data;

import java.util.Map;

@Data
public class XpanseDeployTask {
    private String serviceName;
    private String serviceVersion;
    private String taskId;
    private String state;
    private String csp;
    private Map<String, String> context;
    private String command;
    private String response;
    private String result;

}
