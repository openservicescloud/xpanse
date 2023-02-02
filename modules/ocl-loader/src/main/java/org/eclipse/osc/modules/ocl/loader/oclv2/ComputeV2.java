package org.eclipse.osc.modules.ocl.loader.oclv2;

import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ComputeV2 extends OclResource {

    private String name;
    private String type;
    private ImageV2 image;
    private List<SubnetV2> subnets;
    private List<Security> securities;
    private List<Storage> storages;
    private boolean publicly;

}
