package org.eclipse.osc.modules.ocl.loader.oclV2;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Data
public class OclResourceV2 {

    private String state = "inactive";
    private String id;
    private String kind;
    private String name;

    Map<String, Object> properties = new HashMap<>();
}
