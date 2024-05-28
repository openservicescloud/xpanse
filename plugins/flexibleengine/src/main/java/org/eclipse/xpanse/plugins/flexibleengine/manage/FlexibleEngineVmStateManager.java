/*
 * SPDX-License-Identifier: Apache-2.0
 * SPDX-FileCopyrightText: Huawei Inc.
 *
 */

package org.eclipse.xpanse.plugins.flexibleengine.manage;

import com.huaweicloud.sdk.core.auth.ICredential;
import com.huaweicloud.sdk.ecs.v2.EcsClient;
import com.huaweicloud.sdk.ecs.v2.model.BatchRebootServersRequest;
import com.huaweicloud.sdk.ecs.v2.model.BatchRebootServersResponse;
import com.huaweicloud.sdk.ecs.v2.model.BatchStartServersRequest;
import com.huaweicloud.sdk.ecs.v2.model.BatchStartServersResponse;
import com.huaweicloud.sdk.ecs.v2.model.BatchStopServersRequest;
import com.huaweicloud.sdk.ecs.v2.model.BatchStopServersResponse;
import com.huaweicloud.sdk.ecs.v2.model.ShowJobRequest;
import com.huaweicloud.sdk.ecs.v2.model.ShowJobResponse;
import com.huaweicloud.sdk.ecs.v2.model.ShowJobResponse.StatusEnum;
import jakarta.annotation.Resource;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.xpanse.modules.credential.CredentialCenter;
import org.eclipse.xpanse.modules.models.common.enums.Csp;
import org.eclipse.xpanse.modules.models.credential.AbstractCredentialInfo;
import org.eclipse.xpanse.modules.models.credential.enums.CredentialType;
import org.eclipse.xpanse.modules.models.monitor.exceptions.ClientApiCallFailedException;
import org.eclipse.xpanse.modules.orchestrator.servicestate.ServiceStateManageRequest;
import org.eclipse.xpanse.plugins.flexibleengine.common.FlexibleEngineClient;
import org.eclipse.xpanse.plugins.flexibleengine.common.FlexibleEngineRetryStrategy;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.retry.support.RetrySynchronizationManager;
import org.springframework.stereotype.Component;

/**
 * Class that encapsulates all Manager-related public methods of the FlexibleEngine plugin.
 */
@Slf4j
@Component
public class FlexibleEngineVmStateManager {
    @Resource
    private CredentialCenter credentialCenter;
    @Resource
    private FlexibleEngineClient flexibleEngineClient;
    @Resource
    private FlexibleEngineServerManageRequestConverter requestConverter;
    @Resource
    private FlexibleEngineRetryStrategy flexibleEngineRetryStrategy;

    /**
     * Start the FlexibleEngine Ecs server.
     */
    @Retryable(retryFor = ClientApiCallFailedException.class,
            maxAttemptsExpression = "${http.request.retry.max.attempts}",
            backoff = @Backoff(delayExpression = "${http.request.retry.delay.milliseconds}"))
    public boolean startService(ServiceStateManageRequest serviceStateManageRequest) {
        try {
            EcsClient ecsClient = getEcsClient(serviceStateManageRequest);
            BatchStartServersRequest request =
                    requestConverter.buildBatchStartServersRequest(
                            serviceStateManageRequest.getDeployResourceEntityList());
            BatchStartServersResponse response =
                    ecsClient.batchStartServersInvoker(request)
                            .retryTimes(flexibleEngineRetryStrategy.getRetryMaxAttempts())
                            .retryCondition(flexibleEngineRetryStrategy::matchRetryCondition)
                            .backoffStrategy(flexibleEngineRetryStrategy)
                            .invoke();
            return checkEcsExecResultByJobId(ecsClient, response.getJobId());
        } catch (Exception e) {
            String errorMsg = String.format("Start service %s error. %s",
                    serviceStateManageRequest.getServiceId(), e.getMessage());
            int retryCount = Objects.isNull(RetrySynchronizationManager.getContext())
                    ? 0 : RetrySynchronizationManager.getContext().getRetryCount();
            log.error(errorMsg + " Retry count:" + retryCount);
            throw new ClientApiCallFailedException(e.getMessage());
        }
    }

    /**
     * Stop the FlexibleEngine Ecs server.
     */
    @Retryable(retryFor = ClientApiCallFailedException.class,
            maxAttemptsExpression = "${http.request.retry.max.attempts}",
            backoff = @Backoff(delayExpression = "${http.request.retry.delay.milliseconds}"))
    public boolean stopService(ServiceStateManageRequest serviceStateManageRequest) {
        try {
            EcsClient ecsClient = getEcsClient(serviceStateManageRequest);
            BatchStopServersRequest batchStopServersRequest =
                    requestConverter.buildBatchStopServersRequest(
                            serviceStateManageRequest.getDeployResourceEntityList());
            BatchStopServersResponse response =
                    ecsClient.batchStopServersInvoker(batchStopServersRequest)
                            .retryTimes(flexibleEngineRetryStrategy.getRetryMaxAttempts())
                            .retryCondition(flexibleEngineRetryStrategy::matchRetryCondition)
                            .backoffStrategy(flexibleEngineRetryStrategy)
                            .invoke();
            return checkEcsExecResultByJobId(ecsClient, response.getJobId());
        } catch (Exception e) {
            String errorMsg = String.format("Stop service %s error. %s",
                    serviceStateManageRequest.getServiceId(), e.getMessage());
            int retryCount = Objects.isNull(RetrySynchronizationManager.getContext())
                    ? 0 : RetrySynchronizationManager.getContext().getRetryCount();
            log.error(errorMsg + " Retry count:" + retryCount);
            throw new ClientApiCallFailedException(e.getMessage());
        }
    }

    /**
     * Restart the FlexibleEngine Ecs server.
     */
    @Retryable(retryFor = ClientApiCallFailedException.class,
            maxAttemptsExpression = "${http.request.retry.max.attempts}",
            backoff = @Backoff(delayExpression = "${http.request.retry.delay.milliseconds}"))
    public boolean restartService(ServiceStateManageRequest serviceStateManageRequest) {
        try {
            EcsClient ecsClient = getEcsClient(serviceStateManageRequest);
            BatchRebootServersRequest request =
                    requestConverter.buildBatchRebootServersRequest(
                            serviceStateManageRequest.getDeployResourceEntityList());
            BatchRebootServersResponse response =
                    ecsClient.batchRebootServersInvoker(request)
                            .retryTimes(flexibleEngineRetryStrategy.getRetryMaxAttempts())
                            .retryCondition(flexibleEngineRetryStrategy::matchRetryCondition)
                            .backoffStrategy(flexibleEngineRetryStrategy)
                            .invoke();
            return checkEcsExecResultByJobId(ecsClient, response.getJobId());
        } catch (Exception e) {
            String errorMsg = String.format("Restart service %s error. %s",
                    serviceStateManageRequest.getServiceId(), e.getMessage());
            int retryCount = Objects.isNull(RetrySynchronizationManager.getContext())
                    ? 0 : RetrySynchronizationManager.getContext().getRetryCount();
            log.error(errorMsg + " Retry count:" + retryCount);
            throw new ClientApiCallFailedException(e.getMessage());
        }
    }

    private EcsClient getEcsClient(ServiceStateManageRequest serviceStateManageRequest) {
        AbstractCredentialInfo credential =
                credentialCenter.getCredential(Csp.FLEXIBLE_ENGINE, CredentialType.VARIABLES,
                        serviceStateManageRequest.getUserId());
        ICredential icredential = flexibleEngineClient.getCredential(credential);
        return flexibleEngineClient.getEcsClient(icredential,
                serviceStateManageRequest.getRegionName());
    }

    private boolean checkEcsExecResultByJobId(EcsClient ecsClient, String jobId) {
        ShowJobRequest jobRequest = new ShowJobRequest().withJobId(jobId);
        ShowJobResponse response = ecsClient.showJobInvoker(jobRequest)
                .retryTimes(flexibleEngineRetryStrategy.getRetryMaxAttempts()).retryCondition(
                        (resp, ex) -> Objects.nonNull(resp)
                                && !resp.getStatus().equals(StatusEnum.SUCCESS))
                .backoffStrategy(flexibleEngineRetryStrategy)
                .invoke();
        if (response.getStatus().equals(StatusEnum.FAIL)) {
            String errorMsg = String.format(
                    "Manage vm operation failed. JobId: %s reason: %s " + " message: %s", jobId,
                    response.getFailReason(), response.getMessage());
            throw new ClientApiCallFailedException(errorMsg);
        }
        return response.getStatus().equals(StatusEnum.SUCCESS);
    }

}