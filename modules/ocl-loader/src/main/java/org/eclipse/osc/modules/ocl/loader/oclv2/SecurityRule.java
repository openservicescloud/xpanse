package org.eclipse.osc.modules.ocl.loader.oclv2;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SecurityRule extends OclResource {

    private Integer priority;
    private String protocol;
    private String cidr;
    private String direction;
    private String ports;
    private String action;

}
