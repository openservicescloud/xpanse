package org.eclipse.xpanse.modules.engine.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.xpanse.modules.engine.exceptions.ExecutorException;
import org.eclipse.xpanse.modules.engine.terraform.resource.TfState;
import org.eclipse.xpanse.modules.engine.terraform.resource.TfStateResource;
import org.eclipse.xpanse.modules.engine.terraform.resource.TfStateResourceInstance;
import org.eclipse.xpanse.modules.engine.xpresource.XpResource;
import org.eclipse.xpanse.modules.engine.xpresource.XpResourceContainer;
import org.eclipse.xpanse.modules.engine.xpresource.XpResourceVm;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @Description:
 * @ClassName: HuaweiTFHandler
 * @Author: yy
 * @Date: 2023/2/16 17:03
 * @Version: 1.0
 */
@Component
@Slf4j
public class HuaweiTFHandler implements XpanseHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public List<XpResource> handler(String terraformState) {
        List xpResourceList = new ArrayList();
        XpResource xpResource = new XpResource();
        TfState tfState;
        try {
            tfState = objectMapper.readValue(terraformState, TfState.class);
        } catch (IOException ex) {
            log.error("Parse terraform state content failed.");
            throw new ExecutorException("Parse terraform state content failed.", ex);
        }
        for (TfStateResource tfStateResource : tfState.getResources()) {
            if (tfStateResource.getType().equals("huaweicloud_compute_instance")) {
                for (TfStateResourceInstance instance : tfStateResource.getInstances()) {
                    String id = (String) instance.getAttributes().get("id");
                    String name = (String) instance.getAttributes().get("name");
                    String ipv4 = (String) instance.getAttributes().get("access_ip_v4");
                    String image_id = (String) instance.getAttributes().get("image_id");
                    String image_name = (String) instance.getAttributes().get("image_name");
                    String region = (String) instance.getAttributes().get("region");

                    XpResourceVm xpResourceVm = new XpResourceVm();
                    xpResourceVm.setId(id);
                    xpResourceVm.setName(name);
                    xpResourceVm.setIpv4(ipv4);

                    XpResourceContainer xpResourceContainer = new XpResourceContainer();
                    xpResourceContainer.setContainerId(image_id);
                    xpResourceContainer.setContainerName(image_name);

                    xpResource.getXpResourceKind().getXpResourceVm().add(xpResourceVm);
                    xpResource.getXpResourceKind().getXpResourceContainer()
                        .add(xpResourceContainer);
                    xpResource.setRegion(region);
                    xpResource.setId(UUID.randomUUID().toString());
                }
            }
            if (tfStateResource.getType().equals("huaweicloud_compute_eip_associate")) {
                for (TfStateResourceInstance instance : tfStateResource.getInstances()) {
                    String public_ip = (String) instance.getAttributes().get("public_ip");
                    xpResource.setEip(public_ip);
                }
            }
            xpResourceList.add(xpResource);
        }
        return xpResourceList;
    }


}
