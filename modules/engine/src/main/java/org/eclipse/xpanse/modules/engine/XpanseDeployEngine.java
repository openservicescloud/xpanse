package org.eclipse.xpanse.modules.engine;

/**
 *
 */
public interface XpanseDeployEngine {
    XpanseDeployResponse deploy(XpanseDeployTask task);

    void destroy(XpanseDeployTask task);
}
