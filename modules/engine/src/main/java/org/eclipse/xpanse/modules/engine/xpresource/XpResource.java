/*
 * SPDX-License-Identifier: Apache-2.0
 * SPDX-FileCopyrightText: Huawei Inc.
 *
 */

package org.eclipse.xpanse.modules.engine.xpresource;

import lombok.Data;

/**
 * XpResource class.
 */
@Data
public class XpResource {

    XpResourceKind xpResourceKind;
    String eip;
    String id;
    String region;
    XpResourceProperty xpResourceProperty;

}
