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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.eclipse.xpanse.modules.deployment.deployers.terraform.terraformboot.generated.model.WebhookConfig;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * TerraformAsyncModifyFromScriptsRequest
 */
@JsonPropertyOrder({
  TerraformAsyncModifyFromScriptsRequest.JSON_PROPERTY_REQUEST_ID,
  TerraformAsyncModifyFromScriptsRequest.JSON_PROPERTY_IS_PLAN_ONLY,
  TerraformAsyncModifyFromScriptsRequest.JSON_PROPERTY_VARIABLES,
  TerraformAsyncModifyFromScriptsRequest.JSON_PROPERTY_ENV_VARIABLES,
  TerraformAsyncModifyFromScriptsRequest.JSON_PROPERTY_TASK_ID,
  TerraformAsyncModifyFromScriptsRequest.JSON_PROPERTY_SCRIPTS,
  TerraformAsyncModifyFromScriptsRequest.JSON_PROPERTY_TF_STATE,
  TerraformAsyncModifyFromScriptsRequest.JSON_PROPERTY_WEBHOOK_CONFIG
})
@jakarta.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", comments = "Generator version: 7.4.0")
public class TerraformAsyncModifyFromScriptsRequest {
  public static final String JSON_PROPERTY_REQUEST_ID = "requestId";
  private UUID requestId;

  public static final String JSON_PROPERTY_IS_PLAN_ONLY = "isPlanOnly";
  private Boolean isPlanOnly;

  public static final String JSON_PROPERTY_VARIABLES = "variables";
  private Map<String, Object> variables = new HashMap<>();

  public static final String JSON_PROPERTY_ENV_VARIABLES = "envVariables";
  private Map<String, String> envVariables = new HashMap<>();

  public static final String JSON_PROPERTY_TASK_ID = "taskId";
  private UUID taskId;

  public static final String JSON_PROPERTY_SCRIPTS = "scripts";
  private List<String> scripts = new ArrayList<>();

  public static final String JSON_PROPERTY_TF_STATE = "tfState";
  private String tfState;

  public static final String JSON_PROPERTY_WEBHOOK_CONFIG = "webhookConfig";
  private WebhookConfig webhookConfig;

  public TerraformAsyncModifyFromScriptsRequest() {
  }

  public TerraformAsyncModifyFromScriptsRequest requestId(UUID requestId) {
    
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


  public TerraformAsyncModifyFromScriptsRequest isPlanOnly(Boolean isPlanOnly) {
    
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


  public TerraformAsyncModifyFromScriptsRequest variables(Map<String, Object> variables) {
    
    this.variables = variables;
    return this;
  }

  public TerraformAsyncModifyFromScriptsRequest putVariablesItem(String key, Object variablesItem) {
    this.variables.put(key, variablesItem);
    return this;
  }

   /**
   * Key-value pairs of regular variables that must be used to execute the Terraform request.
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


  public TerraformAsyncModifyFromScriptsRequest envVariables(Map<String, String> envVariables) {
    
    this.envVariables = envVariables;
    return this;
  }

  public TerraformAsyncModifyFromScriptsRequest putEnvVariablesItem(String key, String envVariablesItem) {
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


  public TerraformAsyncModifyFromScriptsRequest taskId(UUID taskId) {
    
    this.taskId = taskId;
    return this;
  }

   /**
   * Id of the request.
   * @return taskId
  **/
  @jakarta.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_TASK_ID)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public UUID getTaskId() {
    return taskId;
  }


  @JsonProperty(JSON_PROPERTY_TASK_ID)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setTaskId(UUID taskId) {
    this.taskId = taskId;
  }


  public TerraformAsyncModifyFromScriptsRequest scripts(List<String> scripts) {
    
    this.scripts = scripts;
    return this;
  }

  public TerraformAsyncModifyFromScriptsRequest addScriptsItem(String scriptsItem) {
    if (this.scripts == null) {
      this.scripts = new ArrayList<>();
    }
    this.scripts.add(scriptsItem);
    return this;
  }

   /**
   * List of script files for modify requests deployed via scripts
   * @return scripts
  **/
  @jakarta.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_SCRIPTS)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public List<String> getScripts() {
    return scripts;
  }


  @JsonProperty(JSON_PROPERTY_SCRIPTS)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setScripts(List<String> scripts) {
    this.scripts = scripts;
  }


  public TerraformAsyncModifyFromScriptsRequest tfState(String tfState) {
    
    this.tfState = tfState;
    return this;
  }

   /**
   * The .tfState file content after deployment
   * @return tfState
  **/
  @jakarta.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_TF_STATE)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public String getTfState() {
    return tfState;
  }


  @JsonProperty(JSON_PROPERTY_TF_STATE)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setTfState(String tfState) {
    this.tfState = tfState;
  }


  public TerraformAsyncModifyFromScriptsRequest webhookConfig(WebhookConfig webhookConfig) {
    
    this.webhookConfig = webhookConfig;
    return this;
  }

   /**
   * Get webhookConfig
   * @return webhookConfig
  **/
  @jakarta.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_WEBHOOK_CONFIG)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public WebhookConfig getWebhookConfig() {
    return webhookConfig;
  }


  @JsonProperty(JSON_PROPERTY_WEBHOOK_CONFIG)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setWebhookConfig(WebhookConfig webhookConfig) {
    this.webhookConfig = webhookConfig;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TerraformAsyncModifyFromScriptsRequest terraformAsyncModifyFromScriptsRequest = (TerraformAsyncModifyFromScriptsRequest) o;
    return Objects.equals(this.requestId, terraformAsyncModifyFromScriptsRequest.requestId) &&
        Objects.equals(this.isPlanOnly, terraformAsyncModifyFromScriptsRequest.isPlanOnly) &&
        Objects.equals(this.variables, terraformAsyncModifyFromScriptsRequest.variables) &&
        Objects.equals(this.envVariables, terraformAsyncModifyFromScriptsRequest.envVariables) &&
        Objects.equals(this.taskId, terraformAsyncModifyFromScriptsRequest.taskId) &&
        Objects.equals(this.scripts, terraformAsyncModifyFromScriptsRequest.scripts) &&
        Objects.equals(this.tfState, terraformAsyncModifyFromScriptsRequest.tfState) &&
        Objects.equals(this.webhookConfig, terraformAsyncModifyFromScriptsRequest.webhookConfig);
  }

  @Override
  public int hashCode() {
    return Objects.hash(requestId, isPlanOnly, variables, envVariables, taskId, scripts, tfState, webhookConfig);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TerraformAsyncModifyFromScriptsRequest {\n");
    sb.append("    requestId: ").append(toIndentedString(requestId)).append("\n");
    sb.append("    isPlanOnly: ").append(toIndentedString(isPlanOnly)).append("\n");
    sb.append("    variables: ").append(toIndentedString(variables)).append("\n");
    sb.append("    envVariables: ").append(toIndentedString(envVariables)).append("\n");
    sb.append("    taskId: ").append(toIndentedString(taskId)).append("\n");
    sb.append("    scripts: ").append(toIndentedString(scripts)).append("\n");
    sb.append("    tfState: ").append(toIndentedString(tfState)).append("\n");
    sb.append("    webhookConfig: ").append(toIndentedString(webhookConfig)).append("\n");
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

