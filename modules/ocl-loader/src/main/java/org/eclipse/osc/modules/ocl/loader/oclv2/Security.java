package org.eclipse.osc.modules.ocl.loader.oclv2;

import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Security extends OclResource {

    private List<SecurityRule> rules;

}
