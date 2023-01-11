package org.eclipse.osc.orchestrator.plugin.huaweicloud;

import org.apache.karaf.minho.boot.Minho;
import org.apache.karaf.minho.boot.service.ConfigService;
import org.apache.karaf.minho.boot.service.ServiceRegistry;
import org.eclipse.osc.modules.ocl.loader.OclLoader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

public class ObsOrchestratorStorageTest {

    @Disabled
    @Test()
    public void onRegisterTest() {
        ObsOrchestratorStorage storage = new ObsOrchestratorStorage();
        Minho minho = Minho.builder()
            .loader(() -> Stream.of(new ConfigService(), new OclLoader()))
            .build()
            .start();
        ServiceRegistry registry = minho.getServiceRegistry();
        Assertions.assertDoesNotThrow(() -> storage.onRegister(registry));
    }

    @Disabled
    @Test
    public void test() throws Exception {
        ObsOrchestratorStorage storage = new ObsOrchestratorStorage();

        ConfigService configService = new ConfigService();

        ServiceRegistry serviceRegistry = new ServiceRegistry();
        serviceRegistry.add(configService);

        storage.onRegister(serviceRegistry);

        Assertions.assertEquals(0, storage.services().size());

        String value = "{\"name\":\"test\",\"pwd\":123456}";

        storage.store("kafka-service", "osc-orchestrator-huaweicloud", "state", value);

        Assertions.assertEquals(1, storage.services().size());

        Assertions.assertTrue(
            storage.exists("kafka-service__osc-orchestrator-huaweicloud__state"));

        String state = storage.getKey("kafka-service", "osc-orchestrator-huaweicloud", "state");

        Assertions.assertEquals("{\"name\":\"test\",\"pwd\":123456}", state);

        storage.remove("kafka-service__osc-orchestrator-huaweicloud__state");

        Assertions.assertEquals(0, storage.services().size());

        Assertions.assertFalse(
            storage.exists("kafka-service__osc-orchestrator-huaweicloud__state"));
    }
}
