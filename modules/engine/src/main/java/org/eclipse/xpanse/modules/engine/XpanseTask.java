package org.eclipse.xpanse.modules.engine;

import org.eclipse.xpanse.modules.engine.xpresource.XpResource;

import java.util.List;

public interface XpanseTask {
    List<XpResource> execute(BuilderContext context);
}
