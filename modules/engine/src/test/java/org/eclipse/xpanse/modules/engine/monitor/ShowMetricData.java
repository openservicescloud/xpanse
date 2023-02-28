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

/**
 * Monitor test.
 */
public class ShowMetricData {

    public static void main(String[] args) {
        String ak = "****";
        String sk = "****";
        String namespace = "SYS.ECS";
        String metricName = "cpu_util";
        String dim0 = "instance_id,****";
        String filter = "average";
        Integer period = 1;
        String from = "1676856420000";
        String to = "1676857020000";

        ICredential auth = new BasicCredentials()
                .withAk(ak)
                .withSk(sk);

        CesClient client = CesClient.newBuilder()
                .withCredential(auth)
                .withRegion(CesRegion.valueOf("cn-southwest-2"))
                .build();
        ShowMetricDataRequest request = new ShowMetricDataRequest()
                .withNamespace(namespace)
                .withMetricName(metricName)
                .withDim0(dim0)
                .withFilter(FilterEnum.valueOf(filter))
                .withPeriod(period)
                .withFrom(Long.valueOf(from))
                .withTo(Long.valueOf(to));
        try {
            ShowMetricDataResponse response = client.showMetricData(request);
            System.out.println(response.toString());
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
    }
}
