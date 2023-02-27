package org.eclipse.xpanse.modules.ocl.v2;

import lombok.Data;

@Data
public class Billing {
    private String model;
    private String period;
    private String currency;
}
