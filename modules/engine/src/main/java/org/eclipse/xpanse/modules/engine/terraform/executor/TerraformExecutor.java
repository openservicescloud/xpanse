/*
 * SPDX-License-Identifier: Apache-2.0
 * SPDX-FileCopyrightText: Huawei Inc.
 *
 */

package org.eclipse.xpanse.modules.engine.terraform.executor;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.xpanse.modules.engine.AtomBuilder;
import org.eclipse.xpanse.modules.engine.BuilderContext;
import org.eclipse.xpanse.modules.engine.exceptions.ExecutorException;
import org.eclipse.xpanse.modules.engine.ocl.OclResource;
import org.eclipse.xpanse.modules.engine.terraform.builder.TFBuilderFactory;
import org.eclipse.xpanse.modules.engine.utils.SystemCmd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.Map;

/**
 * Class to encapsulate all Terraform executions.
 */
@Slf4j
@Component
public class TerraformExecutor {

    public static final String WORKSPACE_HOME = "terraform_ws";
    public static final String VERSION_FILE_NAME = "version.tf";
    public static final String SCRIPT_FILE_NAME = "resources.tf";

    private String workspace;
    private Environment environment;
    private Map<String,String> env;

    @Autowired
    public TerraformExecutor(Environment environment,Map<String,String> env){
        this.environment = environment;
        this.env = env;
    }

    public void buildEnvironment(BuilderContext context){
        //TODO 环境是否已经构建
        log.info("Start build environment");
        TFBuilderFactory factory = new TFBuilderFactory();
        AtomBuilder envBuilder = factory.createBuilder(TFBuilderFactory.ENV_BUILDER);
        AtomBuilder basicBuilder = factory.createBuilder(TFBuilderFactory.BASIC_BUILDER);
        context.setEnvironment(environment);
        try {
            envBuilder.build(context);
            basicBuilder.build(context);
            log.info("Environment build success");
        } catch (Exception ex) {
            envBuilder.build(context);
            basicBuilder.rollback(context);
            throw ex;
        }
    }

    /**
     * Creates terraform workspace.
     *
     * @param name name of the folder in terraform workspace.
     */
    public String createWorkspace(String name) {
        log.info("start create workspace");
        File ws = new File(WORKSPACE_HOME + FileSystems.getDefault().getSeparator() + name);
        if (!ws.exists() && !ws.mkdirs()) {
            throw new ExecutorException("Create workspace failed, File path not created: " + ws.getAbsolutePath());
        }
        workspace = ws.getAbsolutePath();
        log.info("workspace create success,Working directory is " + workspace);
        return workspace;
    }

    /**
     * Creates terraform version file on the workspace.
     *
     * @param workspace     terraform workspace.
     * @param versionScript versionScript of the terraform file version.tf
     */
    public void createVerScriptFile(String workspace, String versionScript) {
        log.info("start create version file");
        String verScriptPath = workspace + FileSystems.getDefault().getSeparator() + VERSION_FILE_NAME;
        try {
            try (FileWriter writer = new FileWriter(verScriptPath)) {
                writer.write(versionScript);
            }
            log.info("version file create success,path is " + verScriptPath);
        } catch (IOException ex) {
            log.error("create version file failed.", ex);
            throw new ExecutorException("create version file failed.", ex);
        }
    }

    /**
     * Creates terraform script file on workspace.
     *
     * @param script content of the script file as string.
     */
    public void createScriptFile(String workspace, String script) {
        log.info("start create script file");
        String scriptFile = workspace + FileSystems.getDefault().getSeparator() + SCRIPT_FILE_NAME;
        try {
            try (FileWriter writer = new FileWriter(scriptFile)) {
                writer.write(script);
            }
            log.info("version file create success,path is " + scriptFile);
        } catch (IOException ex) {
            throw new ExecutorException("create script file failed.", ex);
        }
    }

    /**
     * Executes terraform init command.
     *
     * @return true if initialization of terraform is successful. else false.
     */
    public boolean tfInit() {
        StringBuilder out = new StringBuilder();
        boolean exeRet = execute("terraform init", out);
        log.info(out.toString());
        return exeRet;
    }

    /**
     * Executes terraform plan command.
     *
     * @return true if terraform plan creation is successful. else false.
     */
    public boolean tfPlan() {
        // TODO: Dynamic variables need to be supported.
        StringBuilder out = new StringBuilder();
        boolean exeRet = execute("terraform plan", out);
        log.info(out.toString());
        return exeRet;
    }

    /**
     * Executes terraform apply command.
     *
     * @return true if changes are successfully applied. else false.
     */
    public boolean tfApply() {
        // TODO: Dynamic variables need to be supported.
        StringBuilder out = new StringBuilder();
        boolean exeRet = execute("terraform apply -auto-approve", out);
        log.info(out.toString());
        return exeRet;
    }

    /**
     * Executes terraform destroy command.
     *
     * @return true if all resources are successfully destroyed on the target infrastructure.
     * else false.
     */
    public boolean tfDestroy() {
        // TODO: Dynamic variables need to be supported.
        StringBuilder out = new StringBuilder();
        boolean exeRet = execute("terraform destroy -auto-approve", out);
        log.info(out.toString());
        return exeRet;
    }


    private boolean execute(String cmd, StringBuilder stdOut) {
        log.info("Will executing cmd: " + String.join(" ", cmd));
        SystemCmd systemCmd = new SystemCmd();
        systemCmd.setEnv(env);
        systemCmd.setWorkDir(workspace);
        return systemCmd.execute(cmd, stdOut);
    }

    /**
     *  deploy source by terraform
     * @param oclResource
     */
    public void deploy(OclResource oclResource){
        if (!tfInit()) {
            throw new ExecutorException("ServiceName:{} TFExecutor.tfInit failed."+oclResource.getName());
        }
        if (!tfPlan()) {
            throw new ExecutorException("ServiceName:{} TFExecutor.tfPlan failed."+oclResource.getName());
        }
        if (!tfApply()) {
            throw new ExecutorException("ServiceName:{} TFExecutor.tfApply failed."+oclResource.getName());
        }
    }

    /**
     * destroy resource on the cloud
     * @param oclResource
     */
    public void destroy(OclResource oclResource) {
        if (!tfInit()) {
            throw new ExecutorException("ServiceName:{} TFExecutor.tfInit failed."+oclResource.getName());
        }
        if (!tfPlan()) {
            throw new ExecutorException("ServiceName:{} TFExecutor.tfPlan failed."+oclResource.getName());
        }
        if (!tfDestroy()) {
            throw new ExecutorException("ServiceName:{} TFExecutor.tfDestroy failed."+oclResource.getName());
        }
    }

    /**
     * Reads the contents of the "terraform.tfstate" file from the terraform workspace
     *
     * @return file contents as string.
     */
    public String getTerraformState() {
        File tfState = new File(workspace + FileSystems.getDefault().getSeparator()
                + "terraform.tfstate");
        if (!tfState.exists()) {
            log.info("Terraform state file not exists.");
            return null;
        }
        try {
            return Files.readString(tfState.toPath());
        } catch (IOException ex) {
            throw new ExecutorException("Read state file failed.", ex);
        }
    }
}
