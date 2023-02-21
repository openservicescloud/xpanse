package org.eclipse.xpanse.modules.engine.terraform.builder;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.xpanse.modules.engine.AtomBuilder;
import org.eclipse.xpanse.modules.engine.BuilderContext;
import org.eclipse.xpanse.modules.engine.exceptions.BuilderException;
import org.eclipse.xpanse.modules.engine.exceptions.ExecutorException;
import org.eclipse.xpanse.modules.engine.ocl.OclResource;
import org.eclipse.xpanse.modules.engine.terraform.executor.TerraformExecutor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

@Slf4j
public class TFResourceBuilder extends AtomBuilder {

    @Autowired
    private TerraformExecutor tfExecutor;

    public static final String VERSION_SCRIPT =
            "terraform {\n"
                    + "  required_providers {\n"
                    + "    huaweicloud = {\n"
                    + "      source = \"huaweicloud/huaweicloud\"\n"
                    + "      version = \">= 1.20.0\"\n"
                    + "    }\n"
                    + "  }\n"
                    + "}";

    @Override
    public String name() {
        return "Huawei-Cloud-resource-Builder";
    }

    @Override
    public boolean create(BuilderContext context) {
        checkResource(context);
        OclResource xpanseResource = context.getOclResource();
        log.info("Start build cloud resource env ,serviceName:{}.",xpanseResource.getName());
        String workspace = tfExecutor.createWorkspace(xpanseResource.getName());
        tfExecutor.createVerScriptFile(workspace, VERSION_SCRIPT);
        tfExecutor.createScriptFile(workspace, xpanseResource.getDeployment().getDeployer().getScript().get("terraform"));
        log.info("Build cloud resource success, serviceName:{}",xpanseResource.getName());
        return true;
    }

    @Override
    public boolean destroy(BuilderContext context) {
        log.info("Destroying Cloud resources.");
        tfExecutor.destroy(context.getOclResource());
        return true;
    }

    private void checkResource(BuilderContext context){
        if (context == null) {
            log.error("BuilderContext is invalid.");
            throw new BuilderException(this, "Builder context is null.");
        }
        OclResource xpanseResource = context.getOclResource();

        if (context.getOclResource() == null) {
            log.error("XpanseResource is invalid.");
            throw new BuilderException(this, "XpanseResource is null.");
        }
        Map<String, String> script = xpanseResource.getDeployment().getDeployer().getScript();
        if (script.isEmpty() || StringUtils.isBlank(script.get("terraform"))){
            log.error("Deploy is invalid.");
            throw new ExecutorException("");
        }
        Map<String, String> envCtx = context.get(new TFEnvironmentBuilder().name());
        if (envCtx == null) {
            log.error("Dependent builder: {} must build first.", new TFEnvironmentBuilder().name());
            throw new BuilderException(this, "EnvironmentBuilder context is null.");
        }
    }
}
