/*
 * SPDX-License-Identifier: Apache-2.0
 * SPDX-FileCopyrightText: Huawei Inc.
 *
 */

package org.eclipse.xpanse.api;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import java.net.URL;
import java.util.HashMap;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.xpanse.api.response.ResultCode;
import org.eclipse.xpanse.modules.models.enums.Category;
import org.eclipse.xpanse.modules.models.enums.Csp;
import org.eclipse.xpanse.modules.models.resource.Ocl;
import org.eclipse.xpanse.modules.models.service.CreateRequest;
import org.eclipse.xpanse.modules.models.utils.OclLoader;
import org.eclipse.xpanse.orchestrator.OrchestratorService;
import org.eclipse.xpanse.orchestrator.register.RegisterService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

/**
 * Test for OrchestratorApi.
 */
@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class OrchestratorApiTest {

    ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrchestratorService orchestratorService;

    @MockBean
    private RegisterService registerService;

    @Test
    public void testListCategories() throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.get("/xpanse/register/categories")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        log.error(contentAsString);
    }

    @Test
    public void postRegister_whenValidInput_thenReturns200() throws Exception {
        OclLoader oclLoader = new OclLoader();
        Ocl ocl = oclLoader.getOcl(new URL("file:./target/test-classes/ocl_test.yaml"));
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/xpanse/register")
                                .contentType(MediaType.parseMediaType("application/x-yaml"))
                                .content(objectMapper.writeValueAsString(ocl)))
                .andExpect(status().isOk());
    }

    @Test
    public void postRegister_whenInvalidInput_thenReturns400AndErrResult() throws Exception {
        Ocl ocl = new Ocl();
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/xpanse/register", ocl)
                        .contentType(MediaType.parseMediaType("application/x-yaml"))
                        .content(objectMapper.writeValueAsString(ocl)))
                .andExpect(status().isBadRequest())
                .andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        Assertions.assertTrue(contentAsString.contains(ResultCode.BAD_PARAMETERS.getCode()));
        Assertions.assertTrue(contentAsString.contains(ResultCode.BAD_PARAMETERS.getMessage()));
    }

    @Test
    public void putUpdate_whenValidInput_thenReturns200() throws Exception {
        String id = UUID.randomUUID().toString();
        OclLoader oclLoader = new OclLoader();
        Ocl ocl = oclLoader.getOcl(new URL("file:./target/test-classes/ocl_test.yaml"));
        mockMvc.perform(MockMvcRequestBuilders.put("/xpanse/register/{id}", id, ocl)
                        .contentType(MediaType.parseMediaType("application/x-yaml"))
                        .content(objectMapper.writeValueAsString(ocl)))
                .andExpect(status().isOk());
    }

    @Test
    public void putUpdate_whenInValidInput_thenReturns400AndErrResult() throws Exception {
        String id = UUID.randomUUID().toString();
        Ocl ocl = new Ocl();
        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.put("/xpanse/register/{id}", id, ocl)
                                .contentType(MediaType.parseMediaType("application/x-yaml"))
                                .content(objectMapper.writeValueAsString(ocl)))
                .andExpect(status().isBadRequest())
                .andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        Assertions.assertTrue(contentAsString.contains(ResultCode.BAD_PARAMETERS.getCode()));
        Assertions.assertTrue(contentAsString.contains(ResultCode.BAD_PARAMETERS.getMessage()));
    }

    @Test
    public void testFetch() throws Exception {
        String oclLocation = "file:./target/test-classes/ocl_test.yaml";
        mockMvc.perform(MockMvcRequestBuilders.post("/xpanse/register/file")
                        .param("oclLocation", oclLocation))
                .andExpect(status().isOk());
    }

    @Test
    public void testFetchUpdate() throws Exception {
        String id = UUID.randomUUID().toString();
        String oclLocation = "file:./target/test-classes/ocl_test.yaml";
        mockMvc.perform(MockMvcRequestBuilders.put("/xpanse/register/file/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("oclLocation", oclLocation))
                .andExpect(status().isOk());
    }

    @Test
    public void testUnregister() throws Exception {
        String id = UUID.randomUUID().toString();
        mockMvc.perform(MockMvcRequestBuilders.delete("/xpanse/register/{id}", id))
                .andExpect(status().isOk());
    }

    @Test
    public void testListRegisteredServices() throws Exception {
        String categoryName = "middleware";
        String cspName = "huawei";
        String serviceName = "kafka";
        String serviceVersion = "v1.0";
        mockMvc.perform(MockMvcRequestBuilders.get("/xpanse/register")
                        .param("categoryName", categoryName)
                        .param("cspName", cspName)
                        .param("serviceName", serviceName)
                        .param("serviceVersion", serviceVersion))
                .andExpect(status().isOk());
    }

    @Test
    public void testListRegisteredServicesTree() throws Exception {
        String categoryName = "middleware";
        mockMvc.perform(MockMvcRequestBuilders.get("/xpanse/register/category/{categoryName}",
                        categoryName))
                .andExpect(status().isOk());
    }

    @Test
    public void testDetail() throws Exception {
        String id = UUID.randomUUID().toString();
        mockMvc.perform(MockMvcRequestBuilders.get("/xpanse/register/{id}",
                        id))
                .andExpect(status().isOk());
    }

    @Test
    public void testHealth() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/xpanse/health"))
                .andExpect(status().isOk());
    }

    @Test
    public void testServiceDetail() throws Exception {
        String id = UUID.randomUUID().toString();
        mockMvc.perform(MockMvcRequestBuilders.get("/xpanse/service/{id}",
                        id))
                .andExpect(status().isOk());
    }

    @Test
    public void testServices() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/xpanse/services"))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeploy() throws Exception {
        OclLoader oclLoader = new OclLoader();
        Ocl ocl = oclLoader.getOcl(new URL("file:./target/test-classes/ocl_test.yaml"));
        CreateRequest createRequest = new CreateRequest();
        createRequest.setId(UUID.randomUUID());
        createRequest.setCategory(Category.COMPUTE);
        createRequest.setServiceName("kafka");
        createRequest.setCustomerServiceName("111");
        createRequest.setVersion("v1.0");
        createRequest.setRegion("cn-southwest-2");
        createRequest.setCsp(Csp.HUAWEI);
        createRequest.setFlavor("3-2-node-without-zookeeper");
        createRequest.setOcl(ocl);
        createRequest.setProperty(new HashMap<>());
        String createRequestString = new ObjectMapper().writeValueAsString(createRequest);
        mockMvc.perform(MockMvcRequestBuilders.post("/xpanse/service")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createRequestString))
                .andExpect(status().isAccepted());
    }

    @Test
    public void testDestroy() throws Exception {
        String uuid = UUID.randomUUID().toString();
        mockMvc.perform(MockMvcRequestBuilders.delete("/xpanse/service/{id}", uuid))
                .andExpect(status().isAccepted());
    }

    @Test
    public void testOpenApi() throws Exception {
        String uuid = UUID.randomUUID().toString();
        String openApiUrl = "http://localhost:8080/openapi";
        Mockito.when(registerService.getOpenApiUrl(uuid)).thenReturn(openApiUrl);
        mockMvc.perform(MockMvcRequestBuilders.get("/xpanse/register/openapi/{id}", uuid))
                .andExpect(redirectedUrl(openApiUrl));
    }
}














 