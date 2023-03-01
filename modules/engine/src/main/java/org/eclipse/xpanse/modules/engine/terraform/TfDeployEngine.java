/*
 * SPDX-License-Identifier: Apache-2.0
 * SPDX-FileCopyrightText: Huawei Inc.
 *
 */

package org.eclipse.xpanse.modules.engine.terraform;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.xpanse.modules.engine.XpanseDeployEngine;
import org.eclipse.xpanse.modules.engine.XpanseDeployResponse;
import org.eclipse.xpanse.modules.engine.XpanseDeployTask;
import org.eclipse.xpanse.modules.engine.XpanseHandler;
import org.eclipse.xpanse.modules.engine.terraform.exceptions.ExecutorException;
import org.eclipse.xpanse.modules.engine.xpresource.XpanseResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

/**
 * terraform deployment engine.
 */
@Slf4j
@Component
public class TfDeployEngine implements XpanseDeployEngine {

    public static final String VERSION_FILE_NAME = "version.tf";
    public static final String SCRIPT_FILE_NAME = "resources.tf";
    public static final String ACCESS_KEY = "ACCESS_KEY";
    public static final String SECRET_KEY = "SECRET_KEY";
    public static final String REGION_NAME = "REGION_NAME";
    public static final String ENTERPRISE_PROJECT_ID = "ENTERPRISE_PROJECT_ID";
    public static final String VERSION_SCRIPT =
            "terraform {\n"
                    + "  required_providers {\n"
                    + "    huaweicloud = {\n"
                    + "      source = \"huaweicloud/huaweicloud\"\n"
                    + "      version = \">= 1.20.0\"\n"
                    + "    }\n"
                    + "  }\n"
                    + "}";
    public static final String AFILED = "FAILED";
    public static final String SUCCESS = "SUCCESS";
    private static final String WORKSPACE = "terraform_ws";

    @Autowired
    private XpanseHandler xpanseHandler;

    /**
     * terraform deploy source.
     *
     * @param task source task.
     * @return XpanseDeployResponse Response after resource deployment.
     */
    @Override
    public XpanseDeployResponse deploy(XpanseDeployTask task) {
        String workspace = getWorkspacePath(task.getServiceName(), task.getTaskId());
        // create workspace
        buildWorkspace(workspace);
        createScriptFile(workspace, task.getCommand());
        // deploy source
        TfExecutor executor = new TfExecutor(getEnvVariable(task.getContext()), workspace);
        executor.deploy();
        String tfState = executor.getTerraformState();

        if (tfState == null) {
            task.setState(AFILED);
            task.setResult(AFILED);
        } else {
            task.setState(SUCCESS);
            task.setResult(SUCCESS);
            task.setResponse(tfState);
        }
        XpanseDeployResponse response = new XpanseDeployResponse();
        response.setTask(task);
        List<XpanseResource> xpResourceList = xpanseHandler.handler(task);
        response.setResources(xpResourceList);
        return response;
    }

    /**
     * terraform destroy source.
     *
     * @param task source task.
     */
    @Override
    public void destroy(XpanseDeployTask task) {
        //
        String workspace = getWorkspacePath(task.getServiceName(), task.getTaskId());
        //
        TfExecutor executor = new TfExecutor(getEnvVariable(task.getContext()), workspace);
        executor.destroy();
    }

    private void createScriptFile(String workspace, String script) {
        log.info("start create terraform script");
        String verScriptPath = workspace + File.separator + VERSION_FILE_NAME;
        String scriptPath = workspace + File.separator + SCRIPT_FILE_NAME;
        try {
            try (FileWriter verWriter = new FileWriter(verScriptPath);
                    FileWriter scriptWriter = new FileWriter(scriptPath)) {
                verWriter.write(VERSION_SCRIPT);
                //TODO 格式
                scriptWriter.write(script);
            }
            log.info("terraform script create success");
        } catch (IOException ex) {
            log.error("create version file failed.", ex);
            throw new ExecutorException("create version file failed.", ex);
        }
    }

    private void buildWorkspace(String workspace) {
        log.info("start create workspace");
        File ws = new File(workspace);
        if (!ws.exists() && !ws.mkdirs()) {
            throw new ExecutorException(
                    "Create workspace failed, File path not created: " + ws.getAbsolutePath());
        }
        log.info("workspace create success,Working directory is " + ws.getAbsolutePath());
    }

    private String getWorkspacePath(String name, String taskId) {
        return WORKSPACE + File.separator + name + File.separator + taskId;
    }

    private Map<String, String> getEnvVariable(Map<String, String> context) {
        if (CollectionUtils.isEmpty(context)) {
            throw new ExecutorException("Get env variable failed. context is empty");
        }
        Map<String, String> env = new HashMap<>();
        env.put(ACCESS_KEY, context.get(ACCESS_KEY));
        env.put(SECRET_KEY, context.get(SECRET_KEY));
        env.put(REGION_NAME, context.get(REGION_NAME));
        env.put(ENTERPRISE_PROJECT_ID, context.get(ENTERPRISE_PROJECT_ID));
        return context;
    }
}
