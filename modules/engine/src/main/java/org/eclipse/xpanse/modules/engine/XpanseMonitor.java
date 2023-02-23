package org.eclipse.xpanse.modules.engine;

import org.eclipse.xpanse.modules.engine.xpresource.XpResource;

public interface XpanseMonitor {

    String cpuUsage(XpResource xpResource);

    String memUsage(XpResource xpResource);

}
