/*
 * SPDX-License-Identifier: Apache-2.0
 * SPDX-FileCopyrightText: Huawei Inc.
 *
 */

package org.eclipse.xpanse.modules.models.service.deploy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;
import org.eclipse.xpanse.modules.models.common.enums.Category;
import org.eclipse.xpanse.modules.models.common.enums.Csp;
import org.eclipse.xpanse.modules.models.servicetemplate.Ocl;
import org.eclipse.xpanse.modules.models.servicetemplate.enums.ServiceHostingType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test of CreateRequest.
 */
class DeployRequestTest {

    private static final UUID id = UUID.fromString("ed6248d4-2bcd-4e94-84b0-29e014c05137");
    private static final String userId = "userId";
    private static final Category category = Category.COMPUTE;
    private static final String serviceName = "service";
    private static final String customerServiceName = "customerService";
    private static final String version = "1.0";
    private static final String region = "us-east-1";
    private static final Csp csp = Csp.AWS;
    private static final String flavor = "flavor";
    private static final Ocl ocl = new Ocl();
    private static final Map<String, Object> properties = Collections.singletonMap("key", "value");
    private static DeployRequest request;

    @BeforeEach
    void setUp() {
        request = new DeployRequest();
        request.setId(id);
        request.setUserId(userId);
        request.setCategory(category);
        request.setServiceName(serviceName);
        request.setCustomerServiceName(customerServiceName);
        request.setVersion(version);
        request.setRegion(region);
        request.setCsp(csp);
        request.setFlavor(flavor);
        request.setOcl(ocl);
        request.setServiceRequestProperties(properties);
    }

    @Test
    void testGetterAndSetter() {
        assertEquals(id, request.getId());
        assertEquals(userId, request.getUserId());
        assertEquals(category, request.getCategory());
        assertEquals(serviceName, request.getServiceName());
        assertEquals(customerServiceName, request.getCustomerServiceName());
        assertEquals(version, request.getVersion());
        assertEquals(region, request.getRegion());
        assertEquals(csp, request.getCsp());
        assertEquals(flavor, request.getFlavor());
        assertEquals(ocl, request.getOcl());
        assertEquals(properties, request.getServiceRequestProperties());
    }

    @Test
    void testEqualsAndHashCode() {
        assertEquals(request, request);
        assertEquals(request.hashCode(), request.hashCode());

        Object obj = new Object();
        assertNotEquals(request, obj);
        assertNotEquals(request, null);
        assertNotEquals(request.hashCode(), obj.hashCode());

        DeployRequest request1 = new DeployRequest();
        DeployRequest request2 = new DeployRequest();
        assertNotEquals(request, request1);
        assertNotEquals(request, request2);
        assertEquals(request1, request2);
        assertNotEquals(request.hashCode(), request1.hashCode());
        assertNotEquals(request.hashCode(), request2.hashCode());
        assertEquals(request1.hashCode(), request2.hashCode());

        request1.setId(id);
        assertNotEquals(request, request1);
        assertNotEquals(request1, request2);
        assertNotEquals(request.hashCode(), request1.hashCode());
        assertNotEquals(request1.hashCode(), request2.hashCode());

        request1.setUserId(userId);
        assertNotEquals(request, request1);
        assertNotEquals(request1, request2);
        assertNotEquals(request.hashCode(), request1.hashCode());
        assertNotEquals(request1.hashCode(), request2.hashCode());

        request1.setCategory(category);
        assertNotEquals(request, request1);
        assertNotEquals(request1, request2);
        assertNotEquals(request.hashCode(), request1.hashCode());
        assertNotEquals(request1.hashCode(), request2.hashCode());

        request1.setServiceName(serviceName);
        assertNotEquals(request, request1);
        assertNotEquals(request1, request2);
        assertNotEquals(request.hashCode(), request1.hashCode());
        assertNotEquals(request1.hashCode(), request2.hashCode());

        request1.setCustomerServiceName(customerServiceName);
        assertNotEquals(request, request1);
        assertNotEquals(request1, request2);
        assertNotEquals(request.hashCode(), request1.hashCode());
        assertNotEquals(request1.hashCode(), request2.hashCode());

        request1.setVersion(version);
        assertNotEquals(request, request1);
        assertNotEquals(request1, request2);
        assertNotEquals(request.hashCode(), request1.hashCode());
        assertNotEquals(request1.hashCode(), request2.hashCode());

        request1.setRegion(region);
        assertNotEquals(request, request1);
        assertNotEquals(request1, request2);
        assertNotEquals(request.hashCode(), request1.hashCode());
        assertNotEquals(request1.hashCode(), request2.hashCode());

        request1.setCsp(csp);
        assertNotEquals(request, request1);
        assertNotEquals(request1, request2);
        assertNotEquals(request.hashCode(), request1.hashCode());
        assertNotEquals(request1.hashCode(), request2.hashCode());

        request1.setFlavor(flavor);
        assertNotEquals(request, request1);
        assertNotEquals(request1, request2);
        assertNotEquals(request.hashCode(), request1.hashCode());
        assertNotEquals(request1.hashCode(), request2.hashCode());

        request1.setOcl(ocl);
        assertNotEquals(request, request1);
        assertNotEquals(request1, request2);
        assertNotEquals(request.hashCode(), request1.hashCode());
        assertNotEquals(request1.hashCode(), request2.hashCode());

        request1.setServiceRequestProperties(properties);
        assertEquals(request, request1);
        assertNotEquals(request1, request2);
        assertEquals(request.hashCode(), request1.hashCode());
        assertNotEquals(request1.hashCode(), request2.hashCode());
    }

    @Test
    void testToString() {
        DeployRequest request = new DeployRequest();
        request.setId(id);
        request.setUserId(userId);
        request.setCategory(category);
        request.setServiceName(serviceName);
        request.setCustomerServiceName(customerServiceName);
        request.setVersion(version);
        request.setRegion(region);
        request.setCsp(csp);
        request.setFlavor(flavor);
        request.setOcl(ocl);
        request.setServiceHostingType(ServiceHostingType.SERVICE_VENDOR);
        request.setServiceRequestProperties(properties);

        String expectedToString = "DeployRequest(super=DeployRequestBase(" +
                "userId=" + userId +
                ", category=" + category +
                ", serviceName=" + serviceName +
                ", customerServiceName=" + customerServiceName +
                ", version=" + version +
                ", region=" + region +
                ", csp=" + csp +
                ", flavor=" + flavor +
                ", serviceHostingType=" + ServiceHostingType.SERVICE_VENDOR +
                ", ocl=" + ocl +
                ", serviceRequestProperties=" + properties +
                "), id=" + id + ")";
        assertEquals(expectedToString, request.toString());
    }

}
