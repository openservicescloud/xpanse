/*
 * Terraform-Boot API
 * RESTful Services to interact with Terraform-Boot runtime
 *
 * The version of the OpenAPI document: 1.0.5-SNAPSHOT
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


package org.eclipse.xpanse.modules.deployment.deployers.terraform.terraformboot.generated.model;

import java.util.Objects;
import java.util.Arrays;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * TerraformResult
 */
@JsonPropertyOrder({
  TerraformResult.JSON_PROPERTY_DESTROY_SCENARIO,
  TerraformResult.JSON_PROPERTY_COMMAND_STD_OUTPUT,
  TerraformResult.JSON_PROPERTY_COMMAND_STD_ERROR,
  TerraformResult.JSON_PROPERTY_TERRAFORM_STATE,
  TerraformResult.JSON_PROPERTY_IMPORTANT_FILE_CONTENT_MAP,
  TerraformResult.JSON_PROPERTY_COMMAND_SUCCESSFUL
})
@jakarta.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen")
public class TerraformResult {
  /**
   * This value is set only if the same is set in the request as well. This is useful only for the caller to differentiate what type of destroy it is. No difference in the way destroy will be executed.based on this flag. User may use this flag in case callback are used. So the calling application can know the result is for which specific destroy use case within the calling system.
   */
  public enum DestroyScenarioEnum {
    DESTROY("destroy"),
    
    ROLLBACK("rollback"),
    
    PURGE("purge");

    private String value;

    DestroyScenarioEnum(String value) {
      this.value = value;
    }

    @JsonValue
    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static DestroyScenarioEnum fromValue(String value) {
      for (DestroyScenarioEnum b : DestroyScenarioEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  public static final String JSON_PROPERTY_DESTROY_SCENARIO = "destroyScenario";
  private DestroyScenarioEnum destroyScenario;

  public static final String JSON_PROPERTY_COMMAND_STD_OUTPUT = "commandStdOutput";
  private String commandStdOutput;

  public static final String JSON_PROPERTY_COMMAND_STD_ERROR = "commandStdError";
  private String commandStdError;

  public static final String JSON_PROPERTY_TERRAFORM_STATE = "terraformState";
  private String terraformState;

  public static final String JSON_PROPERTY_IMPORTANT_FILE_CONTENT_MAP = "importantFileContentMap";
  private Map<String, String> importantFileContentMap = new HashMap<>();

  public static final String JSON_PROPERTY_COMMAND_SUCCESSFUL = "commandSuccessful";
  private Boolean commandSuccessful;

  public TerraformResult() {
  }

  public TerraformResult destroyScenario(DestroyScenarioEnum destroyScenario) {
    
    this.destroyScenario = destroyScenario;
    return this;
  }

   /**
   * This value is set only if the same is set in the request as well. This is useful only for the caller to differentiate what type of destroy it is. No difference in the way destroy will be executed.based on this flag. User may use this flag in case callback are used. So the calling application can know the result is for which specific destroy use case within the calling system.
   * @return destroyScenario
  **/
  @jakarta.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_DESTROY_SCENARIO)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public DestroyScenarioEnum getDestroyScenario() {
    return destroyScenario;
  }


  @JsonProperty(JSON_PROPERTY_DESTROY_SCENARIO)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setDestroyScenario(DestroyScenarioEnum destroyScenario) {
    this.destroyScenario = destroyScenario;
  }


  public TerraformResult commandStdOutput(String commandStdOutput) {
    
    this.commandStdOutput = commandStdOutput;
    return this;
  }

   /**
   * stdout of the command returned as string.
   * @return commandStdOutput
  **/
  @jakarta.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_COMMAND_STD_OUTPUT)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getCommandStdOutput() {
    return commandStdOutput;
  }


  @JsonProperty(JSON_PROPERTY_COMMAND_STD_OUTPUT)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setCommandStdOutput(String commandStdOutput) {
    this.commandStdOutput = commandStdOutput;
  }


  public TerraformResult commandStdError(String commandStdError) {
    
    this.commandStdError = commandStdError;
    return this;
  }

   /**
   * stderr of the command returned as string.
   * @return commandStdError
  **/
  @jakarta.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_COMMAND_STD_ERROR)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getCommandStdError() {
    return commandStdError;
  }


  @JsonProperty(JSON_PROPERTY_COMMAND_STD_ERROR)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setCommandStdError(String commandStdError) {
    this.commandStdError = commandStdError;
  }


  public TerraformResult terraformState(String terraformState) {
    
    this.terraformState = terraformState;
    return this;
  }

   /**
   * .tfstate file contents returned as string.
   * @return terraformState
  **/
  @jakarta.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_TERRAFORM_STATE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getTerraformState() {
    return terraformState;
  }


  @JsonProperty(JSON_PROPERTY_TERRAFORM_STATE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setTerraformState(String terraformState) {
    this.terraformState = terraformState;
  }


  public TerraformResult importantFileContentMap(Map<String, String> importantFileContentMap) {
    
    this.importantFileContentMap = importantFileContentMap;
    return this;
  }

  public TerraformResult putImportantFileContentMapItem(String key, String importantFileContentMapItem) {
    if (this.importantFileContentMap == null) {
      this.importantFileContentMap = new HashMap<>();
    }
    this.importantFileContentMap.put(key, importantFileContentMapItem);
    return this;
  }

   /**
   * Data of all other files generated by the terraform execution.The map key contains the file name and value is the file contents as string.
   * @return importantFileContentMap
  **/
  @jakarta.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_IMPORTANT_FILE_CONTENT_MAP)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Map<String, String> getImportantFileContentMap() {
    return importantFileContentMap;
  }


  @JsonProperty(JSON_PROPERTY_IMPORTANT_FILE_CONTENT_MAP)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setImportantFileContentMap(Map<String, String> importantFileContentMap) {
    this.importantFileContentMap = importantFileContentMap;
  }


  public TerraformResult commandSuccessful(Boolean commandSuccessful) {
    
    this.commandSuccessful = commandSuccessful;
    return this;
  }

   /**
   * Get commandSuccessful
   * @return commandSuccessful
  **/
  @jakarta.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_COMMAND_SUCCESSFUL)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Boolean getCommandSuccessful() {
    return commandSuccessful;
  }


  @JsonProperty(JSON_PROPERTY_COMMAND_SUCCESSFUL)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setCommandSuccessful(Boolean commandSuccessful) {
    this.commandSuccessful = commandSuccessful;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TerraformResult terraformResult = (TerraformResult) o;
    return Objects.equals(this.destroyScenario, terraformResult.destroyScenario) &&
        Objects.equals(this.commandStdOutput, terraformResult.commandStdOutput) &&
        Objects.equals(this.commandStdError, terraformResult.commandStdError) &&
        Objects.equals(this.terraformState, terraformResult.terraformState) &&
        Objects.equals(this.importantFileContentMap, terraformResult.importantFileContentMap) &&
        Objects.equals(this.commandSuccessful, terraformResult.commandSuccessful);
  }

  @Override
  public int hashCode() {
    return Objects.hash(destroyScenario, commandStdOutput, commandStdError, terraformState, importantFileContentMap, commandSuccessful);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TerraformResult {\n");
    sb.append("    destroyScenario: ").append(toIndentedString(destroyScenario)).append("\n");
    sb.append("    commandStdOutput: ").append(toIndentedString(commandStdOutput)).append("\n");
    sb.append("    commandStdError: ").append(toIndentedString(commandStdError)).append("\n");
    sb.append("    terraformState: ").append(toIndentedString(terraformState)).append("\n");
    sb.append("    importantFileContentMap: ").append(toIndentedString(importantFileContentMap)).append("\n");
    sb.append("    commandSuccessful: ").append(toIndentedString(commandSuccessful)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}

