package org.eclipse.osc.orchestrator.plugin.huaweicloud;

import java.util.HashMap;
import java.util.Map;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.karaf.minho.boot.service.ConfigService;
import org.eclipse.osc.modules.ocl.loader.OclResources;
import org.eclipse.osc.orchestrator.OrchestratorStorage;

@Data
@EqualsAndHashCode(callSuper = true)
public class BuilderContext extends HashMap<String, Map<String, String>> {

    private ConfigService config;

    private String serviceName;

    private String pluginName;

    private OclResources oclResources = new OclResources();

    private Map<String, AtomBuilder> builderMap = new HashMap<>();

    private OrchestratorStorage storage;
}
