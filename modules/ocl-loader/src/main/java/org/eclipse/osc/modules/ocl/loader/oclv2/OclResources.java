package org.eclipse.osc.modules.ocl.loader.oclv2;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class OclResources {

   private String provider;
   private String serviceName;
   private String state = "inactive";
   private List<OclResource> resources = new ArrayList<>();

}
