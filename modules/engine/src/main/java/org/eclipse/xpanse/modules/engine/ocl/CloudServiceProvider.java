package org.eclipse.xpanse.modules.engine.ocl;

import lombok.Data;

import java.util.List;

@Data
public class CloudServiceProvider {
    private String name;
    private List<String> region;
}
