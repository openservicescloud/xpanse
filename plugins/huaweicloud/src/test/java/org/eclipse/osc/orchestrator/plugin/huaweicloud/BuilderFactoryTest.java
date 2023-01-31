package org.eclipse.osc.orchestrator.plugin.huaweicloud;

import java.util.Objects;
import org.eclipse.osc.modules.ocl.loader.Ocl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BuilderFactoryTest {

    @Test
    public void basicBuilderTest() {
        BuilderFactory builderFactory = new BuilderFactory();
        AtomBuilder builder = builderFactory.createBuilder(BuilderFactory.BASIC_BUILDER, new Ocl());
        Assertions.assertTrue(Objects.nonNull(builder));
    }

    @Test
    public void envBuilderTest() {
        BuilderFactory builderFactory = new BuilderFactory();
        AtomBuilder builder = builderFactory.createBuilder(BuilderFactory.ENV_BUILDER, new Ocl());
        Assertions.assertEquals("Huawei-Cloud-env-Builder", builder.name());
    }


    @Test
    public void basicBuilderDeprecatedTest() {
        BuilderFactory builderFactory = new BuilderFactory();
        AtomBuilder builder = builderFactory.createBuilder(BuilderFactory.BASIC_BUILDER_DEPRECATED, new Ocl());
        Assertions.assertTrue(Objects.nonNull(builder));
    }

    @Test
    public void unsupportedBuilderTest() {
        BuilderFactory builderFactory = new BuilderFactory();
        Assertions.assertThrows(IllegalArgumentException.class, () ->
            builderFactory.createBuilder("invalid", new Ocl())
        );
    }
}
