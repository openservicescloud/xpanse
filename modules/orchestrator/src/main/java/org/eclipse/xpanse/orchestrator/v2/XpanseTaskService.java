/*
 * SPDX-License-Identifier: Apache-2.0
 * SPDX-FileCopyrightText: Huawei Inc.
 *
 */

package org.eclipse.xpanse.orchestrator.v2;

import java.util.List;
import org.eclipse.xpanse.modules.database.v2.ServiceTaskEntity;
import org.eclipse.xpanse.modules.ocl.v2.Context;
import org.eclipse.xpanse.modules.ocl.v2.Flavor;
import org.eclipse.xpanse.modules.ocl.v2.OclResource;

/**
 * This interface describes orchestrator plugin in charge of interacting with backend fundamental
 * APIs.
 */
public interface XpanseTaskService {

    /**
     * Create service named serviceName using the oclResource.
     *
     * @param serviceName the name of service.
     * @param oclResource the OCLResource model describing the service.
     */
    void createService(String serviceName, OclResource oclResource);


    /**
     * Delete the service using the ID.
     *
     * @param taskId   the service ID to stop.
     * @param oclResource the OCLResource model describing the service.
     */
    void deleteService(String taskId, OclResource oclResource);

    /**
     * List all service task.
     */
    List<ServiceTaskEntity> listServiceTasks();

    /**
     * Show state of service by taskId.
     */
    ServiceTaskEntity showServiceState(String taskId);

    /**
     * List contexts of service by serviceName.
     */
    List<Context> listServiceContexts(String serviceName);


    /**
     * List flavors of service by serviceName.
     */
    List<Flavor> listServiceFlavors(String serviceName);

}
