/*
 * SPDX-License-Identifier: Apache-2.0
 * SPDX-FileCopyrightText: Huawei Inc.
 *
 */

package org.eclipse.xpanse.modules.engine.monitor;

import com.huaweicloud.sdk.aom.v2.AomClient;
import com.huaweicloud.sdk.aom.v2.model.Dimension;
import com.huaweicloud.sdk.aom.v2.model.MetricQueryMeritcParam;
import com.huaweicloud.sdk.aom.v2.model.QueryMetricDataParam;
import com.huaweicloud.sdk.aom.v2.model.ShowMetricsDataRequest;
import com.huaweicloud.sdk.aom.v2.model.ShowMetricsDataResponse;
import com.huaweicloud.sdk.aom.v2.region.AomRegion;
import com.huaweicloud.sdk.core.auth.BasicCredentials;
import com.huaweicloud.sdk.core.auth.ICredential;
import com.huaweicloud.sdk.core.exception.ConnectionException;
import com.huaweicloud.sdk.core.exception.RequestTimeoutException;
import com.huaweicloud.sdk.core.exception.ServiceResponseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Monitor test.
 */
public class BatchListMetricDataDemo {

    public static void main(String[] args) {
        String ak = "****";
        String sk = "****";
        String namespace = "PAAS.AGGR";
        String metricName = "memUsage";
        String name = "instance_id";
        String value = "****";
        Integer period = 60;
        String timerange = "-1.-1.60";
        List<String> statistics = new ArrayList<>();
        statistics.add("average");

        ICredential auth = new BasicCredentials()
                .withAk(ak)
                .withSk(sk);

        AomClient client = AomClient.newBuilder()
                .withCredential(auth)
                .withRegion(AomRegion.valueOf("cn-southwest-2"))
                .build();
        ShowMetricsDataRequest request = new ShowMetricsDataRequest();
        QueryMetricDataParam body = new QueryMetricDataParam();
        List<Dimension> dimensions = new ArrayList<>();
        dimensions.add(
                new Dimension()
                        .withName(name)
                        .withValue(value)
        );
        List<MetricQueryMeritcParam> metrics = new ArrayList<>();
        metrics.add(
                new MetricQueryMeritcParam()
                        .withNamespace(namespace)
                        .withMetricName(metricName)
                        .withDimensions(dimensions)
        );
        body.withMetrics(metrics);
        body.withPeriod(period);
        body.withStatistics(statistics);
        body.withTimerange(timerange);
        request.withBody(body);
        try {
            ShowMetricsDataResponse response = client.showMetricsData(request);
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