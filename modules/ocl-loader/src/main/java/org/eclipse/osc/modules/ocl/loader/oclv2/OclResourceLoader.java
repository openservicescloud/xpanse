package org.eclipse.osc.modules.ocl.loader.oclv2;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.osc.modules.ocl.loader.oclv2.ResourceHclEnum.HuaweiCloudResourceHclEnum;
import org.eclipse.osc.modules.ocl.loader.oclv2.ResourceHclEnum.OpenstackResourceHclEnum;

@Slf4j
public class OclResourceLoader {

    private ObjectMapper mapper;
    private static final String NAME_REGEX = "/^[a-zA-Z_]([a-zA-Z0-9_]+)?$/";
    public static void main(String[] args) {
        OclResourceLoader oclResourceLoader = new OclResourceLoader();
        OclResources resources = oclResourceLoader.parseYamlToResources(
            "D:\\IdeaProjects\\bxs"
                + "\\osc\\modules\\ocl"
                + "-loader\\src"
                + "\\main"
                + "\\resources"
                + "\\resources.yaml");
        System.out.println(resources.toString());
        OclV2 oclV2 = oclResourceLoader.transOclResourcesToOcl(resources);
        System.out.println(oclV2.toString());

        OclV2ToHcl oclV2ToHcl = new OclV2ToHcl(oclV2);
        String tfScript = oclV2ToHcl.getHcl();
        String tfPath = "resources.tf";
        try {
            try (FileWriter tfFile = new FileWriter(tfPath)) {
                tfFile.write(tfScript);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public OclResources parseYamlToResources(String path) {
        OclResources oclResources = null;
        try {
            YamlReader reader = new YamlReader(new FileReader(path));
            Object object = reader.read();
            mapper = new ObjectMapper();
            oclResources = mapper.convertValue(object, OclResources.class);
            String provider = oclResources.getProvider();
            Set<String> kindSet = getKindSetByProvider(provider);
            if (CollectionUtils.isNotEmpty(oclResources.getResources())) {
                List<OclResource> validResources =
                    oclResources.getResources().stream()
                        .filter(oclResource -> StringUtils.isNotBlank(oclResource.kind)
                            && kindSet.contains(oclResource.getKind()))
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

    private Set<String> getKindSetByProvider(String provider) {
        if (StringUtils.equalsIgnoreCase(provider, "huaweicloud")) {
            return HuaweiCloudResourceHclEnum.getKindSet();
        }
        if (StringUtils.equalsIgnoreCase(provider, "openstack")) {
            return OpenstackResourceHclEnum.getKindSet();
        }
        return new HashSet<>();
    }


    public OclV2 transOclResourcesToOcl(OclResources oclResources) {
        OclV2 oclV2 = new OclV2();
        oclV2.setServiceName(oclResources.getServiceName());
        oclV2.setNamespace(oclResources.getServiceName());
        oclV2.setProvider(oclResources.getProvider());
        if (CollectionUtils.isNotEmpty(oclResources.getResources())) {
            oclV2.setOclResources(oclResources.getResources());
            fillVPCList(oclV2);
            fillSubnets(oclV2);
            fillSecurities(oclV2);
            fillBillings(oclV2);
            fillStorages(oclV2);
            fillImages(oclV2);
            fillVms(oclV2);
        }
        return oclV2;
    }


    private void fillVPCList(OclV2 oclV2) {
        List<VPC> vpcList =
            oclV2.getOclResources().stream()
                .filter(oclResource -> "vpc".equalsIgnoreCase(oclResource.getKind()))
                .map(oclResource -> {
                    VPC vpc = new VPC();
                    vpc.setId(oclResource.getId());
                    vpc.setName(oclResource.getName());
                    if (oclResource.getProperties().containsKey("cidr")) {
                        vpc.setCidr((String) oclResource.getProperties().get("cidr"));
                    }
                    return vpc;
                }).collect(Collectors.toList());
        oclV2.setVpcList(vpcList);

    }

    private void fillSubnets(OclV2 oclV2) {
        List<SubnetV2> subnets =
            oclV2.getOclResources().stream()
                .filter(oclResource -> "subnet".equalsIgnoreCase(oclResource.getKind()))
                .map(oclResource -> {
                    SubnetV2 subnet = new SubnetV2();
                    subnet.setId(oclResource.getId());
                    subnet.setName(oclResource.getName());
                    if (oclResource.getProperties().containsKey("cidr")) {
                        subnet.setCidr((String) oclResource.getProperties().get("cidr"));
                    }
                    if (oclResource.getProperties().containsKey("vpc")) {
                        String vpcName = (String) oclResource.getProperties().get("vpc");
                        Optional<VPC> optional =
                            oclV2.getVpcList().stream().filter(vpc -> vpcName.equals(vpc.getName()))
                                .findAny();
                        if (optional.isPresent()) {
                            subnet.setVpc(optional.get());
                        }
                    }
                    return subnet;
                }).collect(Collectors.toList());
        oclV2.setSubnets(subnets);

    }

    private void fillSecurities(OclV2 oclV2) {
        List<Security> securities =
            oclV2.getOclResources().stream()
                .filter(oclResource -> "security".equalsIgnoreCase(oclResource.getKind()))
                .map(oclResource -> {
                    Security security = new Security();
                    security.setName(oclResource.getName());
                    security.setId(oclResource.getId());
                    if (oclResource.getProperties().containsKey("rules")) {
                        List<Object> rules = (List<Object>) oclResource.getProperties()
                            .get("rules");
                        List<SecurityRule> securityRules = rules.stream().map(rule -> {
                            SecurityRule securityRule = null;
                            try {
                                String ruleStr = mapper.writeValueAsString(rule);
                                securityRule = mapper.readValue(ruleStr, SecurityRule.class);
                            } catch (JsonProcessingException e) {
                                log.error("Json to SecurityRule failed. error:{}", e.getMessage());
                            }
                            return securityRule;
                        }).collect(Collectors.toList());
                        security.setRules(securityRules);
                    }
                    return security;
                }).collect(Collectors.toList());
        oclV2.setSecurities(securities);
    }

    private void fillStorages(OclV2 oclV2) {
        List<Storage> storages =
            oclV2.getOclResources().stream()
                .filter(oclResource -> "storage".equalsIgnoreCase(oclResource.getKind()))
                .map(oclResource -> {
                    Storage storage = new Storage();
                    storage.setId(oclResource.getId());
                    storage.setName(oclResource.getName());
                    if (oclResource.getProperties().containsKey("type")) {
                        storage.setType((String) oclResource.getProperties().get("type"));
                    }
                    if (oclResource.getProperties().containsKey("size")) {
                        storage.setSize((String) oclResource.getProperties().get("size"));
                    }
                    return storage;
                }).collect(Collectors.toList());
        oclV2.setStorages(storages);
    }

    private void fillImages(OclV2 oclV2) {
        List<ImageV2> images =
            oclV2.getOclResources().stream()
                .filter(oclResource -> "image".equalsIgnoreCase(oclResource.getKind()))
                .map(oclResource -> {
                    ImageV2 image = new ImageV2();
                    image.setId(oclResource.getId());
                    image.setName(oclResource.getName());
                    if (oclResource.getProperties().containsKey("type")) {
                        image.setType((String) oclResource.getProperties().get("type"));
                    }
                    return image;
                }).collect(Collectors.toList());
        oclV2.setImages(images);
    }


    private void fillBillings(OclV2 oclV2) {
        List<Billing> billings =
            oclV2.getOclResources().stream()
                .filter(oclResource -> "billing".equalsIgnoreCase(oclResource.getKind())
                    && !oclResource.getProperties().isEmpty())
                .map(oclResource -> {
                    Billing billing = null;
                    try {
                        String billingStr =
                            mapper.writeValueAsString(oclResource.getProperties());
                        billing = mapper.readValue(billingStr, Billing.class);
                    } catch (JsonProcessingException e) {
                        log.error("Json to Billing failed. error:{}", e.getMessage());
                    }
                    return billing;
                }).collect(Collectors.toList());
        oclV2.setBillings(billings);

    }

    private void fillVms(OclV2 oclV2) {
        List<ComputeV2> computes =
            oclV2.getOclResources().stream()
                .filter(oclResource -> "compute".equalsIgnoreCase(oclResource.getKind()))
                .map(oclResource -> {
                    ComputeV2 compute = new ComputeV2();
                    compute.setId(oclResource.getId());
                    compute.setName(oclResource.getName());
                    if (oclResource.getProperties().containsKey("type")) {
                        compute.setType((String) oclResource.getProperties().get("type"));
                    }
                    if (oclResource.getProperties().containsKey("publicly")) {
                        compute.setPublicly("true".equalsIgnoreCase(
                            (String) oclResource.getProperties().get("publicly")));
                    }
                    if (oclResource.getProperties().containsKey("subnet")) {
                        String subnetName = (String) oclResource.getProperties().get("subnet");
                        List<SubnetV2> subnets =
                            oclV2.getSubnets().stream()
                                .filter(subnet -> subnetName.equals(subnet.getName()))
                                .collect(Collectors.toList());
                        compute.setSubnets(subnets);
                    }
                    if (oclResource.getProperties().containsKey("image")) {
                        String imageName = (String) oclResource.getProperties().get("image");
                        Optional<ImageV2> optional =
                            oclV2.getImages().stream()
                                .filter(image -> imageName.equals(image.getName()))
                                .findAny();
                        if (optional.isPresent()) {
                            compute.setImage(optional.get());
                        }
                    }
                    if (oclResource.getProperties().containsKey("security")) {
                        String securityName = (String) oclResource.getProperties().get("security");
                        List<Security> securities =
                            oclV2.getSecurities().stream()
                                .filter(security -> securityName.equals(security.getName()))
                                .collect(Collectors.toList());
                        compute.setSecurities(securities);
                    }
                    if (oclResource.getProperties().containsKey("storage")) {
                        String storageName = (String) oclResource.getProperties().get("storage");
                        List<Storage> storages =
                            oclV2.getStorages().stream()
                                .filter(storage -> storageName.equals(storage.getName()))
                                .collect(Collectors.toList());
                        compute.setStorages(storages);
                    }
                    return compute;
                }).collect(Collectors.toList());
        oclV2.setComputes(computes);
    }

}
