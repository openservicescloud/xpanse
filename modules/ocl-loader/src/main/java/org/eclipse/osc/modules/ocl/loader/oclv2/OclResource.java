package org.eclipse.osc.modules.ocl.loader.oclv2;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Data
public class OclResource {

    private static final String NAME_REGEX = "/^[a-zA-Z_]([a-zA-Z0-9_]+)?$/";
    String kind;
    String id;
    String name;
    String state = "inactive";

    Map<String, Object> properties = new HashMap<>();

    public boolean isExistedResource() {
        return StringUtils.isNotBlank(id);
    }




    public static void main(String[] args) {
        OclResource ocl = new OclResource();
        ocl.setKind("compute");
        ocl.setName("Ubutun 22.04");
        System.out.println(ocl.getName());

    }

}
