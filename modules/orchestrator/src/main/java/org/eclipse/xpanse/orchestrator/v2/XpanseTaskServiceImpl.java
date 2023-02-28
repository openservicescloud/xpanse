/*
 * SPDX-License-Identifier: Apache-2.0
 * SPDX-FileCopyrightText: Huawei Inc.
 *
 */

package org.eclipse.xpanse.orchestrator.v2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.xpanse.modules.database.v2.ServiceTaskEntity;
import org.eclipse.xpanse.modules.engine.XpanseDeployEngine;
import org.eclipse.xpanse.modules.engine.XpanseDeployResponse;
import org.eclipse.xpanse.modules.engine.XpanseDeployTask;
import org.eclipse.xpanse.modules.engine.XpanseMonitor;
import org.eclipse.xpanse.modules.engine.xpresource.XpResource;
import org.eclipse.xpanse.modules.ocl.loader.data.models.enums.ServiceState;
import org.eclipse.xpanse.modules.ocl.v2.Context;
import org.eclipse.xpanse.modules.ocl.v2.Deployment;
import org.eclipse.xpanse.modules.ocl.v2.Flavor;
import org.eclipse.xpanse.modules.ocl.v2.OclResource;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

/**
 * Main class which orchestrates the OCL request processing. Calls the available plugins to deploy
 * managed service in the respective infrastructure as defined in the OCL.
 */
@Slf4j
@Component
public class XpanseTaskServiceImpl implements XpanseTaskService {

    private static final int STATUS_MSG_MAX_LENGTH = 255;

    private static final String SERVICE_ID = "SERVICE_ID";

    private static final String PLUGIN_NAME = "PLUGIN_NAME";

    private static final String SERVICE_NAME = "SERVICE_NAME";

    private static final String TASK_TYPE = "TASK_TYPE";

    private final DatabaseServiceTaskStorage databaseServiceTaskStorage;

    @Autowired
    private XpanseDeployEngine deployEngine;

    @Autowired
    private XpanseMonitor xpanseMonitor;

    @Autowired
    public XpanseTaskServiceImpl(DatabaseServiceTaskStorage databaseServiceTaskStorage) {
        this.databaseServiceTaskStorage = databaseServiceTaskStorage;
    }

    private ServiceTaskEntity getNewServiceTaskEntity(OclResource oclResource) {
        ServiceTaskEntity serviceTaskEntity = new ServiceTaskEntity();
        UUID uuid = UUID.randomUUID();
        serviceTaskEntity.setId(uuid);
        serviceTaskEntity.setOclResource(oclResource);
        serviceTaskEntity.setIsDeleted(false);
        serviceTaskEntity.setDeployTask(getNewDeployTask(uuid, oclResource));
        return serviceTaskEntity;
    }

    private XpanseDeployTask getNewDeployTask(UUID uuid, OclResource oclResource) {
        XpanseDeployTask task = new XpanseDeployTask();
        task.setTaskId(uuid.toString());
        task.setServiceName(oclResource.getName());
        task.setServiceVersion(oclResource.getService_version());
        task.setState(ServiceState.STARTING.toValue());
        Deployment deployment = oclResource.getDeployment();
        if (Objects.nonNull(deployment)) {
            List<Context> contexts = oclResource.getDeployment().getContext();
            if (!CollectionUtils.isEmpty(contexts)) {
                Map<String, String> contextMap = new HashMap<>();
                for (Context context : contexts) {
                    contextMap.put(context.getName(), context.getValue());
                }
                task.setContext(contextMap);
            }
            if (Objects.nonNull(deployment.getDeployer())) {
                task.setCommand(deployment.getDeployer().getTerraform());
            }
        }
        return task;
    }

    private void updateFailedServiceTaskEntity(ServiceTaskEntity serviceTaskEntity,
            Exception exception) {
        serviceTaskEntity.getDeployTask().setState(ServiceState.FAILED.toString());
        serviceTaskEntity.getDeployTask().setResult(getValidErrorMsg(exception.getMessage()));
    }

    private String getValidErrorMsg(String errorMsg) {
        if (errorMsg.length() > STATUS_MSG_MAX_LENGTH) {
            return errorMsg.substring(0, STATUS_MSG_MAX_LENGTH - 1);
        }
        return errorMsg;
    }

    private void putServiceInfoIntoLogMdc(ServiceTaskEntity serviceTaskEntity) {
        MDC.put(SERVICE_ID, serviceTaskEntity.getId().toString());
    }

    @Override
    public void createService(String serviceName, OclResource oclResource) {
        ServiceTaskEntity serviceTaskEntity =
                this.databaseServiceTaskStorage.getServiceTaskByOclResource(oclResource);
        if (Objects.nonNull(serviceTaskEntity)) {
            throw new RuntimeException(
                    "Service task " + serviceName + " already existed.");
        }

        ServiceTaskEntity newTaskEntity = getNewServiceTaskEntity(oclResource);
        try {
            putServiceInfoIntoLogMdc(newTaskEntity);
            XpanseDeployResponse deployResponse = deployEngine.deploy(
                    newTaskEntity.getDeployTask());
            newTaskEntity.setXpanseDeployResponse(deployResponse);
            this.databaseServiceTaskStorage.store(newTaskEntity);
        } catch (RuntimeException exception) {
            updateFailedServiceTaskEntity(newTaskEntity, exception);
            this.databaseServiceTaskStorage.store(newTaskEntity);
            throw exception;
        }

    }

    @Override
    public void deleteService(String taskId, OclResource oclResource) {
        ServiceTaskEntity serviceTaskEntity =
                this.databaseServiceTaskStorage.getServiceTaskByTaskId(taskId);
        if (Objects.isNull(serviceTaskEntity)) {
            throw new RuntimeException(
                    "Service task " + taskId + " not found.");
        }
        if (serviceTaskEntity.getIsDeleted()) {
            throw new RuntimeException(
                    "Service task " + taskId + " already deleted.");
        }
        try {
            putServiceInfoIntoLogMdc(serviceTaskEntity);
            deployEngine.destroy(serviceTaskEntity.getDeployTask());
            serviceTaskEntity.setIsDeleted(true);
            this.databaseServiceTaskStorage.store(serviceTaskEntity);
//            this.databaseServiceTaskStorage.removeById(serviceTaskEntity.getId());
        } catch (RuntimeException exception) {
            updateFailedServiceTaskEntity(serviceTaskEntity, exception);
            this.databaseServiceTaskStorage.store(serviceTaskEntity);
            throw exception;
        }
    }

    @Override
    public List<ServiceTaskEntity> listServiceTasks() {
        return this.databaseServiceTaskStorage.services();

    }

    @Override
    public ServiceTaskEntity showServiceState(String taskId) {
        return this.databaseServiceTaskStorage.getServiceTaskByTaskId(taskId);
    }

    @Override
    public List<Context> listServiceContexts(String serviceName) {
        List<Context> contexts = new ArrayList<>();
        Context ak = new Context();
        ak.setName("AK");
        ak.setKind("fix");
        ak.setRequired(true);
        ak.setType("String");
        ak.setDescription("Your access key.");
        Map<String, String> akValidators = new HashMap<>();
        akValidators.put("LENGTH", "(1,64)");
        ak.setValidators(akValidators);
        contexts.add(ak);
        Context sk = new Context();
        sk.setName("SK");
        sk.setKind("fix");
        sk.setRequired(true);
        sk.setType("String");
        sk.setDescription("Your security key");
        Map<String, String> skValidators = new HashMap<>();
        skValidators.put("LENGTH", "(63,128)");
        sk.setValidators(skValidators);
        contexts.add(sk);
        Context vpc = new Context();
        vpc.setName("VPC");
        vpc.setRequired(false);
        vpc.setKind("variable");
        vpc.setType("String");
        vpc.setDescription("The vpc you want to deploy, If null, A new vpc will be created.");
        contexts.add(vpc);
        return contexts;
    }

    @Override
    public List<Flavor> listServiceFlavors(String serviceName) {
        List<Flavor> flavors = new ArrayList<>();
        Flavor f1 = new Flavor();
        f1.setName("standalone-kafka");
        f1.setFixedPrice(100);
        Map<String, Object> properties = new HashMap<>();
        properties.put("LENGTH", "(1,64)");
        f1.setProperty(properties);
        flavors.add(f1);

        Flavor f2 = new Flavor();
        f2.setName("3-ecs-kafka");
        f2.setFixedPrice(300);
        Map<String, Object> properties2 = new HashMap<>();
        properties2.put("LENGTH", "(1,64)");
        f2.setProperty(properties2);
        flavors.add(f2);

        Flavor f3 = new Flavor();
        f3.setName("standalone-kafka");
        f3.setFixedPrice(300);
        Map<String, Object> properties3 = new HashMap<>();
        properties3.put("LENGTH", "(1,64)");
        f3.setProperty(properties3);
        flavors.add(f3);
        return flavors;
    }

    @Override
    public String showMonitorData(String vmId) {
        List<ServiceTaskEntity> tasks = listServiceTasks();
        for (ServiceTaskEntity serviceTaskEntity:tasks){
            XpanseDeployTask task = serviceTaskEntity.getXpanseDeployResponse().getTask();
            List<XpResource> xpResources =
                serviceTaskEntity.getXpanseDeployResponse().getResources();
            for (XpResource xpResource:xpResources){
                if (vmId.equals(xpResource.getXpResourceKind().getXpResourceVm().getId())){
                    return xpanseMonitor.cpuUsage(xpResource, task)+xpanseMonitor.memUsage(xpResource, task);
                }
            }
        }
        return null;
    }
}
