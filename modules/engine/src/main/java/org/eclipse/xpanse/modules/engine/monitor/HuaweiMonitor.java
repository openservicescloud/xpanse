/*
 * SPDX-License-Identifier: Apache-2.0
 * SPDX-FileCopyrightText: Huawei Inc.
 *
 */

package org.eclipse.xpanse.modules.engine.monitor;

import com.huaweicloud.sdk.ces.v1.CesClient;
import com.huaweicloud.sdk.ces.v1.model.ShowMetricDataRequest;
import com.huaweicloud.sdk.ces.v1.model.ShowMetricDataRequest.FilterEnum;
import com.huaweicloud.sdk.ces.v1.model.ShowMetricDataResponse;
import com.huaweicloud.sdk.ces.v1.region.CesRegion;
import com.huaweicloud.sdk.core.auth.BasicCredentials;
import com.huaweicloud.sdk.core.auth.ICredential;
import com.huaweicloud.sdk.core.exception.ConnectionException;
import com.huaweicloud.sdk.core.exception.RequestTimeoutException;
import com.huaweicloud.sdk.core.exception.ServiceResponseException;
import org.eclipse.xpanse.modules.engine.XpanseDeployTask;
import org.eclipse.xpanse.modules.engine.XpanseMonitor;
import org.eclipse.xpanse.modules.engine.monitor.resource.MonitorResource;
import org.eclipse.xpanse.modules.engine.monitor.resource.MonitorResourceHandler;
import org.eclipse.xpanse.modules.engine.xpresource.XpanseResource;
import org.springframework.stereotype.Component;

/**
 * Plugin to git monitor data on Huawei cloud.
 */
@Component
public class HuaweiMonitor implements XpanseMonitor {

    @Override
    public String cpuUsage(XpanseResource xpanseResource, XpanseDeployTask task) {

        String ak = task.getContext().get("AK");
        String sk = task.getContext().get("SK");

        ICredential auth = new BasicCredentials()
                .withAk(ak)
                .withSk(sk);
        MonitorResourceHandler monitorResourceHandler = new MonitorResourceHandler();
        MonitorResource monitorResource = monitorResourceHandler.handler(xpanseResource);
        CesClient client = CesClient.newBuilder()
                .withCredential(auth)
                .withRegion(CesRegion.valueOf(monitorResource.getRegion()))
                .build();
        ShowMetricDataRequest request = new ShowMetricDataRequest()
                .withNamespace(monitorResource.getNamespace())
                .withMetricName("cpu_util")
                .withDim0(monitorResource.getDim0())
                .withFilter(FilterEnum.valueOf(monitorResource.getFilter()))
                .withPeriod(monitorResource.getPeriod())
                .withFrom(Long.valueOf(monitorResource.getFrom()))
                .withTo(Long.valueOf(monitorResource.getTo()));
        try {
            ShowMetricDataResponse response = client.showMetricData(request);
            return response.toString();
        } catch (ConnectionException e) {
            e.printStackTrace();
        } catch (RequestTimeoutException e) {
            e.printStackTrace();
        } catch (ServiceResponseException e) {
            e.printStackTrace();
            System.out.println(e.getHttpStatusCode());
            System.out.println(e.getRequestId());
            System.out.println(e.getErrorCode());
            System.out.println(e.getErrorMsg());
        }
        return null;
    }

    @Override
    public String memUsage(XpanseResource xpanseResource, XpanseDeployTask task) {

        String ak = task.getContext().get("AK");
        String sk = task.getContext().get("SK");

        ICredential auth = new BasicCredentials()
                .withAk(ak)
                .withSk(sk);
        MonitorResourceHandler monitorResourceHandler = new MonitorResourceHandler();
        MonitorResource monitorResource = monitorResourceHandler.handler(xpanseResource);
        CesClient client = CesClient.newBuilder()
                .withCredential(auth)
                .withRegion(CesRegion.valueOf(monitorResource.getRegion()))
                .build();
        ShowMetricDataRequest request = new ShowMetricDataRequest()
                .withNamespace(monitorResource.getNamespace())
                .withMetricName("mem_util")
                .withDim0(monitorResource.getDim0())
                .withFilter(FilterEnum.valueOf(monitorResource.getFilter()))
                .withPeriod(monitorResource.getPeriod())
                .withFrom(Long.valueOf(monitorResource.getFrom()))
                .withTo(Long.valueOf(monitorResource.getTo()));
        try {
            ShowMetricDataResponse response = client.showMetricData(request);
            return response.toString();
        } catch (ConnectionException e) {
            e.printStackTrace();
        } catch (RequestTimeoutException e) {
            e.printStackTrace();
        } catch (ServiceResponseException e) {
            e.printStackTrace();
            System.out.println(e.getHttpStatusCode());
            System.out.println(e.getRequestId());
            System.out.println(e.getErrorCode());
            System.out.println(e.getErrorMsg());
        }
        return null;
    }
}
