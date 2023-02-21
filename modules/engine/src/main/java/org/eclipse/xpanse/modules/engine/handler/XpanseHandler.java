package org.eclipse.xpanse.modules.engine.handler;

import org.eclipse.xpanse.modules.engine.xpresource.XpResource;

import java.util.List;

/**
 * @Description:
 * @ClassName: XpanseHandler
 * @Author: yy
 * @Date: 2023/2/16 17:02
 * @Version: 1.0
 */
public interface XpanseHandler {

    List<XpResource> handler(String terraformState);

}
