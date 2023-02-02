package org.eclipse.osc.modules.ocl.loader.oclv2;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class VPC extends OclResource {

    private String cidr;

}
