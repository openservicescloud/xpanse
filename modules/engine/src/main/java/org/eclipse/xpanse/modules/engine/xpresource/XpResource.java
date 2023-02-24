package org.eclipse.xpanse.modules.engine.xpresource;

import lombok.Data;

/**
 * @Description:
 * @ClassName: XpResource
 * @Author: yy
 * @Date: 2023/2/17 15:41
 * @Version: 1.0
 */
@Data
public class XpResource {

    XpResourceKind xpResourceKind;
    String eip;
    String id;
    String region;
    XpResourceProperty xpResourceProperty;

}
