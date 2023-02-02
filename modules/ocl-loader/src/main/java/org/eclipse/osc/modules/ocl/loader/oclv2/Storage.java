package org.eclipse.osc.modules.ocl.loader.oclv2;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Storage extends OclResource {

    private String type;
    private String size;

}
