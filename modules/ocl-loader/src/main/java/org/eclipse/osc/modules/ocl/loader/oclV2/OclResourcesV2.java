package org.eclipse.osc.modules.ocl.loader.oclV2;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class OclResourcesV2 {

    private String state = "inactive";
    private String provider;
    private String serviceName;
    private List<OclResourceV2> resources = new ArrayList<>();

}
