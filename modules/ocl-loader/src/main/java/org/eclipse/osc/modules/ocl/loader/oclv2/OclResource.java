package org.eclipse.osc.modules.ocl.loader.oclv2;

import java.util.HashMap;
import java.util.Map;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Data
public class OclResource {

    String kind;
    String id;
    String name;
    String state = "inactive";

    Map<String, Object> properties = new HashMap<>();

    public boolean isExistedResource() {
        return StringUtils.isNotBlank(id);
    }

}
