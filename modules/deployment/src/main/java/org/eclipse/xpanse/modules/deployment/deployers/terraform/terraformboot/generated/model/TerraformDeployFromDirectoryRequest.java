/*
 * Terraform-Boot API
 * RESTful Services to interact with Terraform-Boot runtime
 *
 * The version of the OpenAPI document: 1.0.7-SNAPSHOT
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
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * TerraformDeployFromDirectoryRequest
 */
@JsonPropertyOrder({
  TerraformDeployFromDirectoryRequest.JSON_PROPERTY_REQUEST_ID,
  TerraformDeployFromDirectoryRequest.JSON_PROPERTY_IS_PLAN_ONLY,
  TerraformDeployFromDirectoryRequest.JSON_PROPERTY_VARIABLES,
  TerraformDeployFromDirectoryRequest.JSON_PROPERTY_ENV_VARIABLES
})
@jakarta.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", comments = "Generator version: 7.4.0")
public class TerraformDeployFromDirectoryRequest {
  public static final String JSON_PROPERTY_REQUEST_ID = "requestId";
  private UUID requestId;

  public static final String JSON_PROPERTY_IS_PLAN_ONLY = "isPlanOnly";
  private Boolean isPlanOnly;

  public static final String JSON_PROPERTY_VARIABLES = "variables";
  private Map<String, Object> variables = new HashMap<>();

  public static final String JSON_PROPERTY_ENV_VARIABLES = "envVariables";
  private Map<String, String> envVariables = new HashMap<>();

  public TerraformDeployFromDirectoryRequest() {
  }

  public TerraformDeployFromDirectoryRequest requestId(UUID requestId) {
    
    this.requestId = requestId;
    return this;
  }

   /**
   * Id of the request
   * @return requestId
  **/
  @jakarta.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_REQUEST_ID)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public UUID getRequestId() {
    return requestId;
  }


  @JsonProperty(JSON_PROPERTY_REQUEST_ID)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setRequestId(UUID requestId) {
    this.requestId = requestId;
  }


  public TerraformDeployFromDirectoryRequest isPlanOnly(Boolean isPlanOnly) {
    
    this.isPlanOnly = isPlanOnly;
    return this;
  }

   /**
   * Flag to control if the deployment must only generate the terraform or it must also apply the changes.
   * @return isPlanOnly
  **/
  @jakarta.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_IS_PLAN_ONLY)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public Boolean getIsPlanOnly() {
    return isPlanOnly;
  }


  @JsonProperty(JSON_PROPERTY_IS_PLAN_ONLY)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setIsPlanOnly(Boolean isPlanOnly) {
    this.isPlanOnly = isPlanOnly;
  }


  public TerraformDeployFromDirectoryRequest variables(Map<String, Object> variables) {
    
    this.variables = variables;
    return this;
  }

  public TerraformDeployFromDirectoryRequest putVariablesItem(String key, Object variablesItem) {
    this.variables.put(key, variablesItem);
    return this;
  }

   /**
   * Key-value pairs of variables that must be used to execute the Terraform request.
   * @return variables
  **/
  @jakarta.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_VARIABLES)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public Map<String, Object> getVariables() {
    return variables;
  }


  @JsonProperty(JSON_PROPERTY_VARIABLES)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setVariables(Map<String, Object> variables) {
    this.variables = variables;
  }


  public TerraformDeployFromDirectoryRequest envVariables(Map<String, String> envVariables) {
    
    this.envVariables = envVariables;
    return this;
  }

  public TerraformDeployFromDirectoryRequest putEnvVariablesItem(String key, String envVariablesItem) {
    if (this.envVariables == null) {
      this.envVariables = new HashMap<>();
    }
    this.envVariables.put(key, envVariablesItem);
    return this;
  }

   /**
   * Key-value pairs of variables that must be injected as environment variables to terraform process.
   * @return envVariables
  **/
  @jakarta.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_ENV_VARIABLES)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Map<String, String> getEnvVariables() {
    return envVariables;
  }


  @JsonProperty(JSON_PROPERTY_ENV_VARIABLES)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setEnvVariables(Map<String, String> envVariables) {
    this.envVariables = envVariables;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TerraformDeployFromDirectoryRequest terraformDeployFromDirectoryRequest = (TerraformDeployFromDirectoryRequest) o;
    return Objects.equals(this.requestId, terraformDeployFromDirectoryRequest.requestId) &&
        Objects.equals(this.isPlanOnly, terraformDeployFromDirectoryRequest.isPlanOnly) &&
        Objects.equals(this.variables, terraformDeployFromDirectoryRequest.variables) &&
        Objects.equals(this.envVariables, terraformDeployFromDirectoryRequest.envVariables);
  }

  @Override
  public int hashCode() {
    return Objects.hash(requestId, isPlanOnly, variables, envVariables);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TerraformDeployFromDirectoryRequest {\n");
    sb.append("    requestId: ").append(toIndentedString(requestId)).append("\n");
    sb.append("    isPlanOnly: ").append(toIndentedString(isPlanOnly)).append("\n");
    sb.append("    variables: ").append(toIndentedString(variables)).append("\n");
    sb.append("    envVariables: ").append(toIndentedString(envVariables)).append("\n");
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

