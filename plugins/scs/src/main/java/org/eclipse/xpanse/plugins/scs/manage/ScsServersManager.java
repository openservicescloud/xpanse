/*
 * SPDX-License-Identifier: Apache-2.0
 * SPDX-FileCopyrightText: Huawei Inc.
 */

package org.eclipse.xpanse.plugins.scs.manage;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.xpanse.modules.credential.CredentialCenter;
import org.eclipse.xpanse.modules.database.resource.DeployResourceEntity;
import org.eclipse.xpanse.modules.models.common.enums.Csp;
import org.eclipse.xpanse.modules.models.credential.AbstractCredentialInfo;
import org.eclipse.xpanse.modules.models.credential.enums.CredentialType;
import org.eclipse.xpanse.modules.models.monitor.exceptions.ClientApiCallFailedException;
import org.eclipse.xpanse.modules.orchestrator.servicestate.ServiceStateManageRequest;
import org.eclipse.xpanse.plugins.scs.common.keystone.ScsKeystoneManager;
import org.openstack4j.api.OSClient;
import org.openstack4j.model.common.ActionResponse;
import org.openstack4j.model.compute.Action;
import org.openstack4j.model.compute.RebootType;
import org.openstack4j.model.compute.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.retry.support.RetrySynchronizationManager;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

/**
 * Class to manage state of VMs for SCS plugin.
 */
@Slf4j
@Component
public class ScsServersManager {

    private final ScsKeystoneManager scsKeystoneManager;

    private final CredentialCenter credentialCenter;


    /**
     * Constructor for the MetricsManager bean.
     *
     * @param scsKeystoneManager KeystoneManager bean.
     * @param credentialCenter   credentialCenter bean.
     */
    @Autowired
    public ScsServersManager(ScsKeystoneManager scsKeystoneManager,
                             CredentialCenter credentialCenter) {
        this.scsKeystoneManager = scsKeystoneManager;
        this.credentialCenter = credentialCenter;
    }


    /**
     * Start the SCS VM.
     */
    @Retryable(retryFor = ClientApiCallFailedException.class,
            maxAttemptsExpression = "${http.request.retry.max.attempts}",
            backoff = @Backoff(delayExpression = "${http.request.retry.delay.milliseconds}"))
    public boolean startService(ServiceStateManageRequest request) {
        try {
            List<String> errorMessages = new ArrayList<>();
            OSClient.OSClientV3 osClient = getOsClient(request.getUserId(), request.getServiceId());
            for (DeployResourceEntity resource : request.getDeployResourceEntityList()) {
                Server serverBeforeStart =
                        osClient.compute().servers().get(resource.getResourceId());
                if (serverBeforeStart.getStatus().equals(Server.Status.ACTIVE)) {
                    log.info("Resource with id {} is already started.", resource.getResourceId());
                    continue;
                }
                ActionResponse actionResponse =
                        osClient.compute().servers().action(resource.getResourceId(), Action.START);
                if (actionResponse.isSuccess()) {
                    log.info("Start resource with id {} successfully.", resource.getResourceId());
                } else {
                    String errorMsg = String.format("Start resource %s failed, error: %s",
                            resource.getResourceId(), actionResponse.getFault());
                    log.error(errorMsg);
                    errorMessages.add(errorMsg);
                }
            }
            if (!CollectionUtils.isEmpty(errorMessages)) {
                throw new ClientApiCallFailedException(String.join("\n", errorMessages));
            }
        } catch (Exception e) {
            String errorMsg = String.format("Start service %s error. %s",
                    request.getServiceId(), e.getMessage());
            int retryCount = Objects.isNull(RetrySynchronizationManager.getContext())
                    ? 0 : RetrySynchronizationManager.getContext().getRetryCount();
            log.error(errorMsg + " Retry count:" + retryCount);
            throw new ClientApiCallFailedException(e.getMessage());
        }
        return true;
    }


    /**
     * Stop the SCS VM.
     */
    @Retryable(retryFor = ClientApiCallFailedException.class,
            maxAttemptsExpression = "${http.request.retry.max.attempts}",
            backoff = @Backoff(delayExpression = "${http.request.retry.delay.milliseconds}"))
    public boolean stopService(ServiceStateManageRequest request) {
        try {
            OSClient.OSClientV3 osClient = getOsClient(request.getUserId(), request.getServiceId());
            List<String> errorMessages = new ArrayList<>();
            for (DeployResourceEntity resource : request.getDeployResourceEntityList()) {
                Server serverBeforeStop =
                        osClient.compute().servers().get(resource.getResourceId());
                if (serverBeforeStop.getStatus().equals(Server.Status.SHUTOFF)) {
                    log.info("Resource with id {} is already stopped.", resource.getResourceId());
                    continue;
                }
                ActionResponse actionResponse =
                        osClient.compute().servers().action(resource.getResourceId(), Action.STOP);
                if (actionResponse.isSuccess()) {
                    log.info("Stop resource {} successfully.", resource.getResourceId());
                } else {
                    String errorMsg = String.format("Stop resource %s failed, error: %s",
                            resource.getResourceId(), actionResponse.getFault());
                    log.error(errorMsg);
                    errorMessages.add(errorMsg);
                }
            }
            if (!CollectionUtils.isEmpty(errorMessages)) {
                throw new ClientApiCallFailedException(String.join("\n", errorMessages));
            }
            return true;
        } catch (Exception e) {
            String errorMsg = String.format("Stop service %s error. %s",
                    request.getServiceId(), e.getMessage());
            int retryCount = Objects.isNull(RetrySynchronizationManager.getContext())
                    ? 0 : RetrySynchronizationManager.getContext().getRetryCount();
            log.error(errorMsg + " Retry count:" + retryCount);
            throw new ClientApiCallFailedException(e.getMessage());
        }
    }

    /**
     * Restart the SCS VM.
     */
    @Retryable(retryFor = ClientApiCallFailedException.class,
            maxAttemptsExpression = "${http.request.retry.max.attempts}",
            backoff = @Backoff(delayExpression = "${http.request.retry.delay.milliseconds}"))
    public boolean restartService(ServiceStateManageRequest request) {
        try {
            OSClient.OSClientV3 osClient = getOsClient(request.getUserId(), request.getServiceId());
            List<String> errorMessages = new ArrayList<>();
            for (DeployResourceEntity resource : request.getDeployResourceEntityList()) {
                Server serverBeforeRestart =
                        osClient.compute().servers().get(resource.getResourceId());
                if (!serverBeforeRestart.getStatus().equals(Server.Status.ACTIVE)) {
                    log.error("Resource with id {} could not be restarted when it is inactive.",
                            resource.getResourceId());
                    continue;
                }
                ActionResponse actionResponse = osClient.compute().servers()
                        .reboot(resource.getResourceId(), RebootType.SOFT);
                if (actionResponse.isSuccess()) {
                    log.info("Restart resource with id {} successfully.", resource.getResourceId());
                } else {
                    String errorMsg = String.format("Restart resource %s failed, error: %s",
                            resource.getResourceId(), actionResponse.getFault());
                    log.error(errorMsg);
                    errorMessages.add(errorMsg);
                }
            }
            if (!CollectionUtils.isEmpty(errorMessages)) {
                throw new ClientApiCallFailedException(String.join("\n", errorMessages));
            }
            return true;
        } catch (Exception e) {
            String errorMsg = String.format("Restart service %s error. %s",
                    request.getServiceId(), e.getMessage());
            int retryCount = Objects.isNull(RetrySynchronizationManager.getContext())
                    ? 0 : RetrySynchronizationManager.getContext().getRetryCount();
            log.error(errorMsg + " Retry count:" + retryCount);
            throw new ClientApiCallFailedException(e.getMessage());
        }
    }

    private OSClient.OSClientV3 getOsClient(String userId, UUID serviceId) {
        AbstractCredentialInfo credentialInfo =
                credentialCenter.getCredential(Csp.SCS, CredentialType.VARIABLES, userId);
        return scsKeystoneManager.getAuthenticatedClient(serviceId, credentialInfo);
    }
}



