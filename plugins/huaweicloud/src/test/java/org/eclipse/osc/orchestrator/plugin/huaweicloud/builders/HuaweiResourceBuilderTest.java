package org.eclipse.osc.orchestrator.plugin.huaweicloud.builders;

import static org.mockito.Mockito.spy;

import java.util.Map;
import org.apache.karaf.minho.boot.service.ConfigService;
import org.eclipse.osc.modules.ocl.loader.Ocl;
import org.eclipse.osc.orchestrator.plugin.huaweicloud.BuilderContext;
import org.eclipse.osc.orchestrator.plugin.huaweicloud.FileOrchestratorStorage;
import org.eclipse.osc.orchestrator.plugin.huaweicloud.exceptions.BuilderException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class HuaweiResourceBuilderTest {

    private HuaweiEnvBuilder envBuilder;
    private HuaweiImageBuilder imageBuilder;
    private HuaweiResourceBuilder resourceBuilder;
    private BuilderContext ctx;
    private Ocl ocl;

    @Test
    public void builderTest() {
        HuaweiResourceBuilder builder = new HuaweiResourceBuilder(null);
        Assertions.assertThrows(BuilderException.class, () -> builder.create(null));
    }

    @Test
    public void hhuaweiResourceBuilderTest() {
        HuaweiResourceBuilder builder = new HuaweiResourceBuilder(null);
        Assertions.assertThrows(BuilderException.class, () -> builder.create(null));
    }

    @BeforeEach
    public void mockBuilder() {
        ocl = new Ocl();

        ConfigService conf = new ConfigService();
        conf.setProperties(
            Map.of(HuaweiEnvBuilder.ACCESS_KEY, "test_access_key", HuaweiEnvBuilder.SECRET_KEY,
                "test_secret_key", HuaweiEnvBuilder.REGION_NAME, "test_region_name"));
        ctx = new BuilderContext();
        ctx.setConfig(conf);
        ctx.setStorage(new FileOrchestratorStorage());

        envBuilder = spy(new HuaweiEnvBuilder(ocl));
        imageBuilder = spy(new HuaweiImageBuilder(ocl));
        resourceBuilder = spy(new HuaweiResourceBuilder(ocl));
    }

    @Test
    public void restoreTFStateFileTest() {
        Assertions.assertThrows(BuilderException.class, () -> resourceBuilder.create(ctx));
    }


}
