package org.eclipse.xpanse.modules.ocl.v2;

import lombok.Data;

@Data
public class TaskResult {
    private String taskId;
    private String state;
    private String response;
    private String result;
}
