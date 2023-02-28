package org.eclipse.xpanse.modules.engine;

import com.huaweicloud.sdk.ces.v1.model.ShowMetricDataResponse;
import org.eclipse.xpanse.modules.engine.xpresource.XpResource;

public interface XpanseMonitor {

    String cpuUsage(XpResource xpResource, XpanseDeployTask task);

    String memUsage(XpResource xpResource, XpanseDeployTask task);

}
