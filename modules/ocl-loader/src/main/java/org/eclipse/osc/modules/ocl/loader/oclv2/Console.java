package org.eclipse.osc.modules.ocl.loader.oclv2;

import java.util.HashMap;
import java.util.Map;
import lombok.Data;

@Data
public class Console {

    private String backend;
    private Map<String, Object> properties = new HashMap<>();

}
