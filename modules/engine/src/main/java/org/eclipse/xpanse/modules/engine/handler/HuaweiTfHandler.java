/*
 * SPDX-License-Identifier: Apache-2.0
 * SPDX-FileCopyrightText: Huawei Inc.
 *
 */

package org.eclipse.xpanse.modules.engine.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.xpanse.modules.engine.XpanseDeployTask;
import org.eclipse.xpanse.modules.engine.XpanseHandler;
import org.eclipse.xpanse.modules.engine.terraform.exceptions.ExecutorException;
import org.eclipse.xpanse.modules.engine.terraform.resource.TfState;
import org.eclipse.xpanse.modules.engine.terraform.resource.TfStateResource;
import org.eclipse.xpanse.modules.engine.terraform.resource.TfStateResourceInstance;
import org.eclipse.xpanse.modules.engine.xpresource.XpResourceContainer;
import org.eclipse.xpanse.modules.engine.xpresource.XpResourceKind;
import org.eclipse.xpanse.modules.engine.xpresource.XpResourceProperty;
import org.eclipse.xpanse.modules.engine.xpresource.XpResourceVm;
import org.eclipse.xpanse.modules.engine.xpresource.XpResourceVpc;
import org.eclipse.xpanse.modules.engine.xpresource.XpanseResource;
import org.springframework.stereotype.Component;

/**
 * Process Huawei cloud resources and return XpanseResource format.
 */
@Component
@Slf4j
public class HuaweiTfHandler implements XpanseHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public List<XpanseResource> handler(XpanseDeployTask task) {
        List xpResourceList = new ArrayList();
        XpanseResource xpanseResource = new XpanseResource();
        TfState tfState;
        try {
            tfState = objectMapper.readValue(task.getResponse(), TfState.class);
        } catch (IOException ex) {
            log.error("Parse terraform state content failed.");
            throw new ExecutorException("Parse terraform state content failed.", ex);
        }
        for (TfStateResource tfStateResource : tfState.getResources()) {
            if (tfStateResource.getType().equals("huaweicloud_compute_instance")) {

                for (TfStateResourceInstance instance : tfStateResource.getInstances()) {
                    // XpResourceVm
                    XpResourceVm xpResourceVm = new XpResourceVm();
                    xpResourceVm.setId((String) instance.getAttributes().get("id"));
                    xpResourceVm.setName((String) instance.getAttributes().get("name"));
                    xpResourceVm.setIpv4((String) instance.getAttributes().get("access_ip_v4"));
                    // XpResourceContainer
                    XpResourceContainer xpResourceContainer = new XpResourceContainer();
                    xpResourceContainer.setContainerId(
                            (String) instance.getAttributes().get("image_id"));
                    xpResourceContainer.setContainerName(
                            (String) instance.getAttributes().get("image_name"));
                    // XpResourceKind
                    XpResourceKind xpResourceKind = new XpResourceKind();
                    xpResourceKind.setXpResourceVm(xpResourceVm);
                    xpResourceKind.setXpResourceContainer(xpResourceContainer);

                    xpanseResource.setXpResourceKind(xpResourceKind);
                    xpanseResource.setRegion((String) instance.getAttributes().get("region"));
                    xpanseResource.setId(UUID.randomUUID().toString());
                }
            }
            if (tfStateResource.getType().equals("huaweicloud_compute_eip_associate")) {
                for (TfStateResourceInstance instance : tfStateResource.getInstances()) {
                    xpanseResource.setEip((String) instance.getAttributes().get("public_ip"));
                }
            }
            if (tfStateResource.getType().equals("huaweicloud_vpc")) {
                for (TfStateResourceInstance instance : tfStateResource.getInstances()) {
                    XpResourceVpc xpResourceVpc = new XpResourceVpc();
                    xpResourceVpc.setVpcId((String) instance.getAttributes().get("id"));
                    xpResourceVpc.setVpcName((String) instance.getAttributes().get("name"));
                    XpResourceProperty xpResourceProperty = new XpResourceProperty();
                    xpResourceProperty.setXpResourceVpc(xpResourceVpc);
                    xpanseResource.setXpResourceProperty(xpResourceProperty);
                }
            }
            xpResourceList.add(xpanseResource);
        }
        return xpResourceList;
    }


}
