package org.eclipse.xpanse.modules.engine.ocl;

import lombok.Data;

import java.util.Map;

@Data
public class Deployment {

    private Map<String,String> context;
    private Deployer deployer;

}
