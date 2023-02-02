package org.eclipse.osc.modules.ocl.loader.oclv2;

import java.util.HashMap;
import java.util.Map;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Billing extends OclResource {

    private String model;
    private String period;
    private String currency;
    private Double fixedPrice;
    private Double variablePrice;
    private String variableItem;
    private String backend;
    private Map<String, Object> properties = new HashMap<>();

}
