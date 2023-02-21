package org.eclipse.xpanse.xpansedeployengin.xpresource;

import java.util.List;
import lombok.Data;

/**
 * @Description:
 * @ClassName: XpResourceKind
 * @Author: yy
 * @Date: 2023/2/17 15:43
 * @Version: 1.0
 */
@Data
public class XpResourceKind {

    List<XpResourceVm> xpResourceVm;
    List<XpResourceContainer> xpResourceContainer;

}
