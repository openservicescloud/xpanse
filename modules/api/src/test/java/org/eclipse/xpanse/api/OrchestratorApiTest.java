/*
 * SPDX-License-Identifier: Apache-2.0
 * SPDX-FileCopyrightText: Huawei Inc.
 *
 */

package org.eclipse.xpanse.api;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;
import java.io.File;
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
        Ocl ocl = oclLoader.getOcl(new URL("file:./target/test-classes/ocl_test1.yaml"));
        //0efc424a-02a4-4a6a-86d8-42b5cf0d4f08      kafka
        UUID uuidRegister = orchestratorApi.register(ocl);
        Thread.sleep(5000);
        log.error(uuidRegister.toString());
        Assertions.assertNotNull(uuidRegister);
    }

    @Test
    public void testUpdate() throws Exception {
        OclLoader oclLoader = new OclLoader();
        Ocl ocl = oclLoader.getOcl(new URL("file:./target/test-classes/ocl_test2.yaml"));
        UUID uuid = UUID.fromString("0efc424a-02a4-4a6a-86d8-42b5cf0d4f08");
        Response response = orchestratorApi.update(uuid.toString(), ocl);
        Thread.sleep(5000);
        Assertions.assertTrue(response.getSuccess());
    }

    @Test
    public void testFetch() throws Exception {
        String oclLocation = "file:./target/test-classes/ocl_test3.yaml";
        //86f8a899-8a32-4299-a039-79af75e3e1c9   kafka2
        UUID fetchUuid = orchestratorApi.fetch(oclLocation);
        Thread.sleep(5000);
        log.error(fetchUuid.toString());
        Assertions.assertNotNull(fetchUuid);
    }

    @Test
    public void testFetchUpdate() throws Exception {
        String oclLocation = "file:./target/test-classes/ocl_test4.yaml";
        UUID uuid = UUID.fromString("86f8a899-8a32-4299-a039-79af75e3e1c9");
        Response response = orchestratorApi.fetchUpdate(uuid.toString(), oclLocation);
        Thread.sleep(5000);
        Assertions.assertTrue(response.getSuccess());
    }

    @Test
    public void testUnregister() {
        UUID uuid = UUID.fromString("0efc424a-02a4-4a6a-86d8-42b5cf0d4f08");
        Response response = orchestratorApi.unregister(uuid.toString());
        Assertions.assertTrue(response.getSuccess());
    }

    @Test
    public void testListRegisteredServices() {
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
        UUID uuid = UUID.fromString("0efc424a-02a4-4a6a-86d8-42b5cf0d4f08");
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
        UUID uuid = UUID.fromString("b0766941-5c5e-44db-ae84-66678a9a59c1");
        ServiceDetailVo serviceDetailVo = orchestratorApi.serviceDetail(uuid.toString());
        log.error(serviceDetailVo.toString());
        Assertions.assertNotNull(serviceDetailVo);
    }

    @Test
    public void testServices() {
        List<ServiceVo> services = orchestratorApi.services();
        log.error(services.toString());
        Assertions.assertNotNull(services);
    }

    @Disabled
    @Test
    public void testDeploy() throws Exception {
        OclLoader oclLoader = new OclLoader();
        Ocl ocl = oclLoader.getOcl(new URL("file:./target/test-classes/ocl_test1.yaml"));
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

    @Test
    public void testOpenApi() throws IOException {
        String uuid = UUID.fromString("0efc424a-02a4-4a6a-86d8-42b5cf0d4f08").toString();
        HttpServletResponse realResponse = new HttpServletResponseWrapper(Mockito.mock(HttpServletResponse.class));
        orchestratorApi.openApi(uuid, realResponse);
        String openApiDir = System.getProperty("user.dir");
        String openApiPath = openApiDir + "/openapi/" + uuid +".html";
        File file = new File(openApiPath);
        Assertions.assertTrue(file.exists());
    }
}
