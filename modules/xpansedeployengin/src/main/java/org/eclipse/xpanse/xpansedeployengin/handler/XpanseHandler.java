package org.eclipse.xpanse.xpansedeployengin.handler;

import java.util.List;
import org.eclipse.xpanse.xpansedeployengin.xpresource.XpResource;

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
