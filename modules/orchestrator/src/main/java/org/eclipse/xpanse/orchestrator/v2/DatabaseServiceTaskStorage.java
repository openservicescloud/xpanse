/*
 * SPDX-License-Identifier: Apache-2.0
 * SPDX-FileCopyrightText: Huawei Inc.
 *
 */

package org.eclipse.xpanse.orchestrator.v2;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.xpanse.modules.database.v2.ServiceTaskEntity;
import org.eclipse.xpanse.modules.database.v2.ServiceTaskEntityRepository;
import org.eclipse.xpanse.modules.ocl.v2.OclResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Bean to manage all service task to database.
 */
@Component
@Slf4j
public class DatabaseServiceTaskStorage implements ServiceTaskStorage {

    ServiceTaskEntityRepository serviceTaskRepository;

    @Autowired
    public DatabaseServiceTaskStorage(ServiceTaskEntityRepository serviceTaskEntityRepository) {
        this.serviceTaskRepository = serviceTaskEntityRepository;
    }

    @Override
    public ServiceTaskEntity getServiceTaskByTaskId(String taskId) {
        List<ServiceTaskEntity> serviceTaskEntities = services();
        for (ServiceTaskEntity serviceTask : serviceTaskEntities) {
            if (StringUtils.equals(serviceTask.getDeployTask().getTaskId(), taskId)) {
                return serviceTask;
            }
        }
        return null;
    }


    @Override
    public ServiceTaskEntity getServiceTaskByOclResource(OclResource oclResource) {
        List<ServiceTaskEntity> serviceTaskEntities = services();
        for (ServiceTaskEntity serviceTask : serviceTaskEntities) {
            if (StringUtils.equals(getServicePrimaryKey(oclResource),
                    getServicePrimaryKey(serviceTask.getOclResource()))) {
                return serviceTask;
            }
        }
        return null;
    }

    @Override
    public ServiceTaskEntity getServiceTaskByTaskEntity(ServiceTaskEntity serviceTaskEntity) {
        List<ServiceTaskEntity> serviceTaskEntities = services();
        for (ServiceTaskEntity serviceTask : serviceTaskEntities) {
            if (Objects.equals(serviceTask.getId(), serviceTaskEntity.getId())) {
                return serviceTask;
            }
        }
        return null;
    }

    @Override
    public List<ServiceTaskEntity> services() {
        return serviceTaskRepository.findAll();
    }


    @Override
    public void removeById(UUID uuid) {
        serviceTaskRepository.deleteById(uuid);
    }

    @Override
    public void removeByTaskId(String taskId) {
        serviceTaskRepository.deleteById(getServiceTaskByTaskId(taskId).getId());
    }

    @Override
    public void remove(ServiceTaskEntity serviceTaskEntity) {
        serviceTaskRepository.deleteById(getServiceTaskByTaskEntity(serviceTaskEntity).getId());
    }


    @Override
    public void store(ServiceTaskEntity serviceTaskEntity) {
        serviceTaskRepository.save(serviceTaskEntity);
    }

    private String getServicePrimaryKey(OclResource oclResource) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(oclResource.getName()).append("/");
        stringBuilder.append(oclResource.getVersion()).append("/");
        stringBuilder.append(oclResource.getService_version()).append("/");
        if (Objects.nonNull(oclResource.getCloud_service_provider())) {
            stringBuilder.append(oclResource.getCloud_service_provider().getName());
        }
        return stringBuilder.toString();

    }

}
