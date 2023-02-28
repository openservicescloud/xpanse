/*
 * SPDX-License-Identifier: Apache-2.0
 * SPDX-FileCopyrightText: Huawei Inc.
 *
 */

package org.eclipse.xpanse.modules.engine;

/**
 * Xpanse Core Deploy Engine.
 */
public interface XpanseDeployEngine {

    XpanseDeployResponse deploy(XpanseDeployTask task);

    void destroy(XpanseDeployTask task);
}
