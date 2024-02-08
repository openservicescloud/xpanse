/*
 * Tofu-Maker API
 * RESTful Services to interact with Tofu-Maker runtime
 *
 * The version of the OpenAPI document: 1.0.0-SNAPSHOT
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


package org.eclipse.xpanse.modules.deployment.deployers.opentofu.opentofumaker.generated.model;

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
import org.eclipse.xpanse.modules.deployment.deployers.opentofu.opentofumaker.generated.model.WebhookConfig;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * OpenTofuAsyncDestroyFromScriptsRequest
 */
@JsonPropertyOrder({
  OpenTofuAsyncDestroyFromScriptsRequest.JSON_PROPERTY_DESTROY_SCENARIO,
  OpenTofuAsyncDestroyFromScriptsRequest.JSON_PROPERTY_VARIABLES,
  OpenTofuAsyncDestroyFromScriptsRequest.JSON_PROPERTY_ENV_VARIABLES,
  OpenTofuAsyncDestroyFromScriptsRequest.JSON_PROPERTY_SCRIPTS,
  OpenTofuAsyncDestroyFromScriptsRequest.JSON_PROPERTY_TF_STATE,
  OpenTofuAsyncDestroyFromScriptsRequest.JSON_PROPERTY_WEBHOOK_CONFIG
})
@jakarta.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen")
public class OpenTofuAsyncDestroyFromScriptsRequest {
  /**
   * The destroy scenario when the Xpanse client send the destroy request. Valid values: destroy,rollback,purge.
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

  public static final String JSON_PROPERTY_VARIABLES = "variables";
  private Map<String, Object> variables = new HashMap<>();

  public static final String JSON_PROPERTY_ENV_VARIABLES = "envVariables";
  private Map<String, String> envVariables = new HashMap<>();

  public static final String JSON_PROPERTY_SCRIPTS = "scripts";
  private List<String> scripts = new ArrayList<>();

  public static final String JSON_PROPERTY_TF_STATE = "tfState";
  private String tfState;

  public static final String JSON_PROPERTY_WEBHOOK_CONFIG = "webhookConfig";
  private WebhookConfig webhookConfig;

  public OpenTofuAsyncDestroyFromScriptsRequest() {
  }

  public OpenTofuAsyncDestroyFromScriptsRequest destroyScenario(DestroyScenarioEnum destroyScenario) {
    
    this.destroyScenario = destroyScenario;
    return this;
  }

   /**
   * The destroy scenario when the Xpanse client send the destroy request. Valid values: destroy,rollback,purge.
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


  public OpenTofuAsyncDestroyFromScriptsRequest variables(Map<String, Object> variables) {
    
    this.variables = variables;
    return this;
  }

  public OpenTofuAsyncDestroyFromScriptsRequest putVariablesItem(String key, Object variablesItem) {
    this.variables.put(key, variablesItem);
    return this;
  }

   /**
   * Key-value pairs of regular variables that must be used to execute the OpenTofu request.
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


  public OpenTofuAsyncDestroyFromScriptsRequest envVariables(Map<String, String> envVariables) {
    
    this.envVariables = envVariables;
    return this;
  }

  public OpenTofuAsyncDestroyFromScriptsRequest putEnvVariablesItem(String key, String envVariablesItem) {
    if (this.envVariables == null) {
      this.envVariables = new HashMap<>();
    }
    this.envVariables.put(key, envVariablesItem);
    return this;
  }

   /**
   * Key-value pairs of variables that must be injected as environment variables to OpenTofu process.
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


  public OpenTofuAsyncDestroyFromScriptsRequest scripts(List<String> scripts) {
    
    this.scripts = scripts;
    return this;
  }

  public OpenTofuAsyncDestroyFromScriptsRequest addScriptsItem(String scriptsItem) {
    if (this.scripts == null) {
      this.scripts = new ArrayList<>();
    }
    this.scripts.add(scriptsItem);
    return this;
  }

   /**
   * List of script files for destroy requests deployed via scripts
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


  public OpenTofuAsyncDestroyFromScriptsRequest tfState(String tfState) {
    
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


  public OpenTofuAsyncDestroyFromScriptsRequest webhookConfig(WebhookConfig webhookConfig) {
    
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
    OpenTofuAsyncDestroyFromScriptsRequest openTofuAsyncDestroyFromScriptsRequest = (OpenTofuAsyncDestroyFromScriptsRequest) o;
    return Objects.equals(this.destroyScenario, openTofuAsyncDestroyFromScriptsRequest.destroyScenario) &&
        Objects.equals(this.variables, openTofuAsyncDestroyFromScriptsRequest.variables) &&
        Objects.equals(this.envVariables, openTofuAsyncDestroyFromScriptsRequest.envVariables) &&
        Objects.equals(this.scripts, openTofuAsyncDestroyFromScriptsRequest.scripts) &&
        Objects.equals(this.tfState, openTofuAsyncDestroyFromScriptsRequest.tfState) &&
        Objects.equals(this.webhookConfig, openTofuAsyncDestroyFromScriptsRequest.webhookConfig);
  }

  @Override
  public int hashCode() {
    return Objects.hash(destroyScenario, variables, envVariables, scripts, tfState, webhookConfig);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OpenTofuAsyncDestroyFromScriptsRequest {\n");
    sb.append("    destroyScenario: ").append(toIndentedString(destroyScenario)).append("\n");
    sb.append("    variables: ").append(toIndentedString(variables)).append("\n");
    sb.append("    envVariables: ").append(toIndentedString(envVariables)).append("\n");
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

