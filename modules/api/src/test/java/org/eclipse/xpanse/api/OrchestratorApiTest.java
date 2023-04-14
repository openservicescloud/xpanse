/*
 * SPDX-License-Identifier: Apache-2.0
 * SPDX-FileCopyrightText: Huawei Inc.
 *
 */

package org.eclipse.xpanse.api;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;
import jakarta.transaction.Transactional;
import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.xpanse.api.response.Response;
import org.eclipse.xpanse.modules.models.SystemStatus;
import org.eclipse.xpanse.modules.models.enums.Category;
import org.eclipse.xpanse.modules.models.enums.Csp;
import org.eclipse.xpanse.modules.models.enums.HealthStatus;
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
@Transactional
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
        UUID uuidRegister = orchestratorApi.register(ocl);
        Thread.sleep(3000);
        log.error(uuidRegister.toString());
        Assertions.assertNotNull(uuidRegister);
    }

    @Test
    public void testUpdate() throws Exception {
        OclLoader oclLoader = new OclLoader();
        Ocl oclRegister = oclLoader.getOcl(new URL("file:./target/test-classes/ocl_test1.yaml"));
        UUID registerUUid = orchestratorApi.register(oclRegister);
        Thread.sleep(3000);

        Ocl oclUpdate = oclLoader.getOcl(new URL("file:./target/test-classes/ocl_test2.yaml"));
        Response response = orchestratorApi.update(registerUUid.toString(), oclUpdate);
        Thread.sleep(3000);
        Assertions.assertTrue(response.getSuccess());
    }

    @Test
    public void testFetch() throws Exception {
        String oclLocation = "file:./target/test-classes/ocl_test1.yaml";
        UUID fetchUuid = orchestratorApi.fetch(oclLocation);
        Thread.sleep(3000);
        log.error(fetchUuid.toString());
        Assertions.assertNotNull(fetchUuid);
    }

    @Test
    public void testFetchUpdate() throws Exception {
        String oclLocationFetch = "file:./target/test-classes/ocl_test1.yaml";
        UUID fetchUuid = orchestratorApi.fetch(oclLocationFetch);
        Thread.sleep(3000);

        String oclLocationUpdate = "file:./target/test-classes/ocl_test2.yaml";
        Response response = orchestratorApi.fetchUpdate(fetchUuid.toString(), oclLocationUpdate);
        Thread.sleep(3000);
        Assertions.assertTrue(response.getSuccess());
    }

    @Test
    public void testUnregister() throws Exception {
        OclLoader oclLoader = new OclLoader();
        Ocl oclRegister = oclLoader.getOcl(new URL("file:./target/test-classes/ocl_test1.yaml"));
        UUID registerUUid = orchestratorApi.register(oclRegister);
        Thread.sleep(3000);

        Response response = orchestratorApi.unregister(registerUUid.toString());
        Assertions.assertTrue(response.getSuccess());
    }

    @Test
    public void testListRegisteredServices() throws Exception {
        OclLoader oclLoader = new OclLoader();
        Ocl oclRegister = oclLoader.getOcl(new URL("file:./target/test-classes/ocl_test1.yaml"));
        UUID registerUUid = orchestratorApi.register(oclRegister);
        Thread.sleep(3000);

        String categoryName = oclRegister.getCategory().name();
        String cspName = oclRegister.getCloudServiceProvider().getName().name();
        String serviceName = oclRegister.getName();
        String serviceVersion = oclRegister.getServiceVersion();
        List<RegisteredServiceVo> registeredServiceVos = orchestratorApi.listRegisteredServices(
                categoryName, cspName, serviceName, serviceVersion);
        log.error(registeredServiceVos.toString());
        Assertions.assertNotNull(registeredServiceVos);
    }

    @Test
    public void testListRegisteredServicesTree() throws Exception {
        OclLoader oclLoader = new OclLoader();
        Ocl oclRegister = oclLoader.getOcl(new URL("file:./target/test-classes/ocl_test1.yaml"));
        UUID registerUUid = orchestratorApi.register(oclRegister);
        Thread.sleep(3000);

        String categoryName = oclRegister.getCategory().name();
        List<CategoryOclVo> categoryOclVos = orchestratorApi.listRegisteredServicesTree(
                categoryName);
        log.error(categoryOclVos.toString());
        Assertions.assertNotNull(categoryOclVos);
    }

    @Test
    public void testDetail() throws Exception {
        OclLoader oclLoader = new OclLoader();
        Ocl oclRegister = oclLoader.getOcl(new URL("file:./target/test-classes/ocl_test1.yaml"));
        UUID registerUUid = orchestratorApi.register(oclRegister);
        Thread.sleep(3000);

        OclDetailVo detail = orchestratorApi.detail(registerUUid.toString());
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
    public void testServices() throws Exception {
        OclLoader oclLoader = new OclLoader();
        Ocl oclRegister = oclLoader.getOcl(new URL("file:./target/test-classes/ocl_test1.yaml"));
        UUID registerUUid = orchestratorApi.register(oclRegister);
        Thread.sleep(3000);

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
    public void testOpenApi() throws Exception {
        OclLoader oclLoader = new OclLoader();
        Ocl oclRegister = oclLoader.getOcl(new URL("file:./target/test-classes/ocl_test1.yaml"));
        UUID registerUUid = orchestratorApi.register(oclRegister);
        Thread.sleep(3000);

        HttpServletResponse realResponse = new HttpServletResponseWrapper(
                Mockito.mock(HttpServletResponse.class));
        orchestratorApi.openApi(registerUUid.toString(), realResponse);
        String openApiDir = System.getProperty("user.dir");
        String openApiPath = openApiDir + "/openapi/" + registerUUid + ".html";
        File file = new File(openApiPath);
        Assertions.assertTrue(file.exists());
    }
}
