package org.eclipse.osc.modules.ocl.loader.oclv2;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SubnetV2 extends OclResource {

    private VPC vpc;
    private String cidr;

}
