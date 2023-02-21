package org.eclipse.xpanse.modules.engine.terraform;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.xpanse.modules.engine.BuilderContext;
import org.eclipse.xpanse.modules.engine.XpanseTask;
import org.eclipse.xpanse.modules.engine.handler.XpanseHandler;
import org.eclipse.xpanse.modules.engine.ocl.OclResource;
import org.eclipse.xpanse.modules.engine.ocl.XpanseResource;
import org.eclipse.xpanse.modules.engine.terraform.executor.TerraformExecutor;
import org.eclipse.xpanse.modules.engine.xpresource.XpResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Slf4j
@Component
public class TFXpanseTask implements XpanseTask {

    private String taskId;
    private String state;
    private String command;
    private String response;
    private String result;

    @Autowired
    private XpanseHandler xpanseHandler;

    @Autowired
    private TerraformExecutor tfExecutor;

    @Override
    public List<XpResource> execute(BuilderContext context){
        // builder environment
        tfExecutor.buildEnvironment(context);
        // deploy
        tfExecutor.deploy(context.getOclResource());
        //
        String terraformState = tfExecutor.getTerraformState();

        // TODO
        if(terraformState == null){
            log.warn("Terraform state is empty");
            return null;
        }
        return xpanseHandler.handler(terraformState);
    }




}
