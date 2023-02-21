package org.eclipse.xpanse.modules.engine;

import org.eclipse.xpanse.modules.engine.xpresource.XpResource;

import java.util.List;
import java.util.Queue;

/**
 *
 */
public interface XpanseDeployEngine {
    List<XpResource> execute(Queue<XpanseTask> task);
}
