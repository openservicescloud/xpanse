package org.eclipse.xpanse.modules.ocl.v2;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class Deployment {

    @NotNull
    @Schema(description = "Context of the deployment")
    private List<Context> context;
    @NotNull
    @Schema(description = "Deployer of the deployment")
    private Deployer deployer;

}
