package org.eclipse.osc.modules.ocl.loader.oclv2;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ImageV2 extends OclResource {

    private String type;
}
