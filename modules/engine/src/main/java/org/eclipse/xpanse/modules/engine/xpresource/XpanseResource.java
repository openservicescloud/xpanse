/*
 * SPDX-License-Identifier: Apache-2.0
 * SPDX-FileCopyrightText: Huawei Inc.
 *
 */

package org.eclipse.xpanse.modules.engine.xpresource;

import lombok.Data;

/**
 * XpanseResource class.
 */
@Data
public class XpanseResource {

    XpResourceKind xpResourceKind;
    String eip;
    String id;
    String region;
    XpResourceProperty xpResourceProperty;

}
