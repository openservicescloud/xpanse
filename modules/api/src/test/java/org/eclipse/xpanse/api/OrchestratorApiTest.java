/*
 * SPDX-License-Identifier: Apache-2.0
 * SPDX-FileCopyrightText: Huawei Inc.
 *
 */

package org.eclipse.xpanse.api;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.xpanse.api.response.Response;
import org.eclipse.xpanse.modules.models.SystemStatus;
import org.eclipse.xpanse.modules.models.enums.Category;
import org.eclipse.xpanse.modules.models.enums.Csp;
import org.eclipse.xpanse.modules.models.enums.HealthStatus;
import org.eclipse.xpanse.modules.models.enums.ServiceState;
import org.eclipse.xpanse.modules.models.resource.Ocl;
import org.eclipse.xpanse.modules.models.service.CreateRequest;
import org.eclipse.xpanse.modules.models.utils.OclLoader;
import org.eclipse.xpanse.modules.models.view.CategoryOclVo;
import org.eclipse.xpanse.modules.models.view.OclDetailVo;
import org.eclipse.xpanse.modules.models.view.RegisteredServiceVo;
import org.eclipse.xpanse.modules.models.view.ServiceDetailVo;
import org.eclipse.xpanse.modules.models.view.ServiceVo;
import org.eclipse.xpanse.orchestrator.OrchestratorService;
import org.eclipse.xpanse.orchestrator.register.RegisterService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * Test for OrchestratorApi.
 */
@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {XpanseTestApplication.class, OrchestratorApi.class,
        OrchestratorService.class, RegisterService.class})
public class OrchestratorApiTest {

    @Autowired
    private OrchestratorApi orchestratorApi;

    @Test
    public void testListCategories() {
        List<Category> categories = Arrays.asList(Category.values());
        List<Category> categoryList = orchestratorApi.listCategories();
        Assertions.assertEquals(categories, categoryList);
    }

    @Test
    public void testRegister() throws Exception {
        OclLoader oclLoader = new OclLoader();
        Ocl ocl = oclLoader.getOcl(new URL("file:./target/test-classes/ocl_test3.yaml"));
        //118185c0-fbb3-4431-9b3d-aa25a34ec6f3      kafka
        //ac0fde89-2304-49b8-a492-3bd82d25acd5      kafka2
        UUID uuidRegister = orchestratorApi.register(ocl);
        log.error(uuidRegister.toString());
        Assertions.assertNotNull(uuidRegister);
    }

    @Test
    public void testUpdate() throws Exception {
        OclLoader oclLoader = new OclLoader();
        Ocl ocl = oclLoader.getOcl(new URL("file:./target/test-classes/ocl_test2.yaml"));
        UUID uuid = UUID.fromString("118185c0-fbb3-4431-9b3d-aa25a34ec6f3");
        Response response = orchestratorApi.update(uuid.toString(), ocl);
        Assertions.assertTrue(response.getSuccess());
    }

    @Test
    public void testFetch() throws Exception {
        String oclLocation = "file:./target/test-classes/ocl_test3.yaml";
        //57fb478c-9aae-4549-b7c6-3b65a8990092
        UUID fetchUuid = orchestratorApi.fetch(oclLocation);
        log.error(fetchUuid.toString());
        Assertions.assertNotNull(fetchUuid);
    }

    @Disabled
    @Test
    public void testFetchUpdate() throws Exception {
        String oclLocation = "file:./target/test-classes/ocl_test4.yaml";
        UUID uuid = UUID.fromString("57fb478c-9aae-4549-b7c6-3b65a8990092");
        Response response = orchestratorApi.fetchUpdate(uuid.toString(), oclLocation);
        Assertions.assertTrue(response.getSuccess());
    }

    @Test
    public void testUnregister() {
        UUID uuid = UUID.fromString("57fb478c-9aae-4549-b7c6-3b65a8990092");
        Response response = orchestratorApi.unregister(uuid.toString());
        Assertions.assertTrue(response.getSuccess());
    }

    @Test
    public void testListRegisteredServices() throws Exception {
        String categoryName = "middleware";
        String cspName = "huawei";
        String serviceName = "kafka";
        String serviceVersion = "v1.0";

        List<RegisteredServiceVo> registeredServiceVos = orchestratorApi.listRegisteredServices(
                categoryName, cspName, serviceName, serviceVersion);
        log.error(registeredServiceVos.toString());
        Assertions.assertNotNull(registeredServiceVos);
    }

    @Test
    public void testListRegisteredServicesTree() {
        String categoryName = "middleware";
        List<CategoryOclVo> categoryOclVos = orchestratorApi.listRegisteredServicesTree(
                categoryName);
        log.error(categoryOclVos.toString());
        Assertions.assertNotNull(categoryOclVos);
    }

    @Test
    public void testDetail() {
        UUID uuid = UUID.fromString("918c59ba-7bb0-4899-8d59-d07e8ea9bba0");

        OclDetailVo detail = orchestratorApi.detail(uuid.toString());
        log.error(detail.toString());
        Assertions.assertNotNull(detail);
    }

    @Test
    public void testHealth() {
        SystemStatus systemStatus = new SystemStatus();
        systemStatus.setHealthStatus(HealthStatus.OK);
        SystemStatus health = orchestratorApi.health();
        Assertions.assertEquals(systemStatus, health);
    }

    @Disabled
    @Test
    public void testServiceDetail() {
        UUID uuid = UUID.fromString("ac0fde89-2304-49b8-a492-3bd82d25acd5");

        ServiceDetailVo serviceDetailVo = orchestratorApi.serviceDetail(uuid.toString());
        log.error(serviceDetailVo.toString());
        Assertions.assertNotNull(serviceDetailVo);
    }

    @Disabled
    @Test
    public void testServices() {
        ServiceVo serviceVo = new ServiceVo();
        serviceVo.setId(UUID.randomUUID());
        serviceVo.setCategory(Category.COMPUTE);
        serviceVo.setName("kafka");
        serviceVo.setVersion("1.0");
        serviceVo.setCsp(Csp.HUAWEI);
        serviceVo.setFlavor("3-2-node-without-zookeeper");
        serviceVo.setServiceState(ServiceState.REGISTERED);
        serviceVo.setCreateTime(new Date());
        serviceVo.setLastModifiedTime(new Date());
        List serviceVoList = new ArrayList<>();
        serviceVoList.add(serviceVo);
        List<ServiceVo> services = orchestratorApi.services();
        log.error(services.toString());
    }

    @Disabled
    @Test
    public void testDeploy() throws Exception {
        OclLoader oclLoader = new OclLoader();
        Ocl ocl = oclLoader.getOcl(new URL("file:./target/test-classes/ocl_test.yaml"));
        CreateRequest createRequest = new CreateRequest();
        createRequest.setId(UUID.fromString("2c2e1530-87bb-49c8-b109-f5146ec9df4b"));
        createRequest.setCategory(Category.MIDDLEWARE);
        createRequest.setServiceName("kafka");
        createRequest.setCustomerServiceName("111");
        createRequest.setVersion("v1.0");
        createRequest.setRegion("cn-southwest-2");
        createRequest.setCsp(Csp.HUAWEI);
        createRequest.setFlavor("3-2-node-without-zookeeper");
        createRequest.setOcl(ocl);
        createRequest.setProperty(new HashMap<>());
        UUID uuid = orchestratorApi.deploy(createRequest);
        Assertions.assertEquals(createRequest.getId(), uuid);
    }

    @Disabled
    @Test
    public void testDestroy() {
        UUID uuid = UUID.randomUUID();
        Response response = orchestratorApi.destroy(uuid.toString());
        Assertions.assertTrue(response.getSuccess());
    }

    @Disabled
    @Test
    public void testOpenApi() throws IOException {
        String uuid = UUID.randomUUID().toString();
        String openApiUrl = "http://localhost:8080/openapi";
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        orchestratorApi.openApi(uuid, response);
    }
}
