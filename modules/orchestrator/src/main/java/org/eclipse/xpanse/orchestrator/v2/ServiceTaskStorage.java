/*
 * SPDX-License-Identifier: Apache-2.0
 * SPDX-FileCopyrightText: Huawei Inc.
 *
 */

package org.eclipse.xpanse.orchestrator.v2;

import java.util.List;
import java.util.UUID;
import org.eclipse.xpanse.modules.database.v2.ServiceTaskEntity;
import org.eclipse.xpanse.modules.ocl.v2.OclResource;

/**
 * Interface to be implemented by all runtime storage providers.
 */
public interface ServiceTaskStorage {

    /**
     * Add or upate managed service data to database.
     *
     * @param serviceTaskEntity the managed service id.
     */
    void store(ServiceTaskEntity serviceTaskEntity);

    /**
     * Method to get database entry based on service name and plugin.
     *
     * @param oclResource Name of the managed service.
     * @return Returns the database entry for the provided arguments.
     */
    ServiceTaskEntity getServiceTaskByOclResource(OclResource oclResource);

    /**
     * Method to get database entry based on service name and plugin.
     *
     * @param taskId Name of the managed service.
     * @return Returns the database entry for the provided arguments.
     */
    ServiceTaskEntity getServiceTaskByTaskId(String taskId);

    /**
     * Method to get database entry based on the service name.
     *
     * @param serviceTaskEntity serviceTaskEntity.
     * @return Returns the database entry for the provided arguments.
     */
    ServiceTaskEntity getServiceTaskByTaskEntity(ServiceTaskEntity serviceTaskEntity);

    /**
     * Method to get all stored database entries.
     *
     * @return Returns all rows from the service status database table.
     */
    List<ServiceTaskEntity> services();

    void removeById(UUID taskId);

    void removeByTaskId(String taskId);

    void remove(ServiceTaskEntity serviceTaskEntity);

}
