package org.eclipse.xpanse.modules.ocl.v2;

import lombok.Data;

import java.util.List;

@Data
public class CloudServiceProvider {
    private String name;
    private List<String> regions;
}
