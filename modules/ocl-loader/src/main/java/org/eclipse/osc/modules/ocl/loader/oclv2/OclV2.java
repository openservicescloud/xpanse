package org.eclipse.osc.modules.ocl.loader.oclv2;

import java.util.List;
import java.util.Map;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Data
public class OclV2 {

    private String serviceName;
    private String provider;
    private String namespace;
    private List<OclResource> oclResources;
    private Map<String, Object> properties;
    private List<ImageV2> images;
    private List<ComputeV2> computes;
    private List<VPC> vpcList;
    private List<SubnetV2> subnets;
    private List<Security> securities;
    private List<Storage> storages;
    private List<Billing> billings;
    private Console console;

}
