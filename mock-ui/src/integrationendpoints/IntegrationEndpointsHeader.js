import {useState} from "react";

function IntegrationEndpointsHeader(props) {
  return(
      <>
        <div className="xpanse-intergration-subright">
          <h2>Datadog</h2>
          <div>
            <a>aaa</a>
            is a monitoring service for cloud-scale applications, bringing together data from servers, <br/>databases, tools, and services to present a unified view of an entire stack.
            <i>(Note: Only for metrics, service logs can be sent via remote Syslog)</i>
          </div>
          <div>
            Configure endpoints for your external Datadog service to enable the integration with your<br/>
            Aiven services.
          </div>
          <div>
            <a>Help articles</a>
          </div>
          <div>
            <a>Create new</a>
          </div>
        </div>
      </>
  )
}

export default IntegrationEndpointsHeader;