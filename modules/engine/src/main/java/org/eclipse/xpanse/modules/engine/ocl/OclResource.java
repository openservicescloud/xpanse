package org.eclipse.xpanse.modules.engine.ocl;

import lombok.Data;
import org.eclipse.xpanse.modules.ocl.loader.data.models.Billing;

import java.util.List;

@Data
public class OclResource {
    private String version;
    private String name;
    private String service_version;
    private String description;
    private String namespace;
    private String icon;
    private CloudServiceProvider cloudServiceProvider;
    private Billing billing;
    private List<Flavor> flavors;
    private Deployment deployment;
}
