package org.eclipse.xpanse.modules.engine;

import lombok.Data;
import org.eclipse.xpanse.modules.engine.xpresource.XpResource;

import java.util.List;

@Data
public class XpanseDeployResponse {

    private XpanseDeployTask task;
    private List<XpResource> resources;

}
