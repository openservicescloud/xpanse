package org.eclipse.xpanse.modules.ocl.v2;

import java.util.Map;
import lombok.Data;

@Data
public class Flavor {

    private String name;
    private int fixedPrice;
    private Map<String, Object> property;

}
