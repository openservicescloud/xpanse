package org.eclipse.xpanse.modules.engine.ocl;

import lombok.Data;

import java.util.Properties;

@Data
public class Flavor {

    private String name;
    private int fixedPrice;
    Properties properties =new Properties();

}
