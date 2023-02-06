package org.eclipse.osc.orchestrator.plugin.huaweicloud.builders.terraform;


import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.osc.modules.ocl.loader.oclV2.OclResourceV2;
import org.eclipse.osc.modules.ocl.loader.oclV2.OclResourcesV2;
import org.eclipse.osc.modules.ocl.loader.oclV2.ResourceHclEnum;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public class OclResourcesLoader {

    private static final ObjectMapper mapper = new ObjectMapper();

    /**
     * Parse YAML to OclResourcesV2
     *
     * @param path
     * @return
     */
    private static OclResourcesV2 parseYamlToResources(String path) {
        OclResourcesV2 oclResources = null;
        try {
            YamlReader reader = new YamlReader(new FileReader(path));
            Object object = reader.read();
            oclResources = mapper.convertValue(object, OclResourcesV2.class);
            String provider = oclResources.getProvider();
            Set<String> kindSet = getKindSetByProvider(provider);
            if (CollectionUtils.isNotEmpty(oclResources.getResources())) {
                List<OclResourceV2> validResources =
                        oclResources.getResources().stream()
                                .filter(oclResource -> StringUtils.isNotBlank(oclResource.getKind())
                                        && kindSet.contains(oclResource.getKind())
                                        && (StringUtils.isNotBlank(oclResource.getId())
                                            || StringUtils.isNotBlank(oclResource.getName())))
                                .collect(Collectors.toList());
                oclResources.setResources(validResources);
            }
        } catch (FileNotFoundException ex) {
            log.error("parseYamlToResources error,file:{} not found", path);
        } catch (YamlException ex) {
            log.error("parseYamlToResources file:{} error:{}", path, ex.getMessage());
        }
        return oclResources;
    }

    private static Set<String> getKindSetByProvider(String provider) {
        if (StringUtils.equalsIgnoreCase(provider, "huaweicloud")) {
            return ResourceHclEnum.HuaweiCloudResourceHclEnum.getKindSet();
        }
        if (StringUtils.equalsIgnoreCase(provider, "openstack")) {
            return ResourceHclEnum.OpenstackResourceHclEnum.getKindSet();
        }
        return new HashSet<>();
    }

    public static void main(String[] args) {
        OclResourcesV2 oclResources = parseYamlToResources("D:\\project\\Osc\\osc\\modules\\ocl-loader\\src\\main\\resources\\resources.yaml");
        String hclByOclResources = OclResources2Hcl.getOclResourcesHcl(oclResources);
        String tfPath = "resources1.tf";
        try {
            try (FileWriter tfFile = new FileWriter(tfPath)) {
                tfFile.write(hclByOclResources);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
