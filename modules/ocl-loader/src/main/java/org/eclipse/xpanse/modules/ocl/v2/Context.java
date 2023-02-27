package org.eclipse.xpanse.modules.ocl.v2;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.Map;
import lombok.Data;

@Data
public class Context {

    @NotEmpty
    @Schema(description = "Name of the context")
    private String name;
    @NotEmpty
    @Schema(description = "Description of the context")
    private String description;
    @NotEmpty
    @Schema(description = "Kind of the context. fix or variable")
    private String kind;
    @NotNull
    @Schema(description = "Datatype of the context value. String,Boolean,Number,Array,Object...")
    private String type;
    @NotNull
    @Schema(description = "Value of the context")
    private String value;
    @NotNull
    @Schema(description = "Default value of the context")
    private String defaultValue;
    @NotNull
    @Schema(description = "Whether the value can be null. true or false")
    private Boolean required;
    @NotNull
    @Schema(description = "Rules for determining whether the value is valid")
    private Map<String, String> validators;
}
