import React, {useState} from "react";
import './IntegrationEndpoints.css'

function IntegrationEndpointsHeader(props) {
  return (
      <>
        <div>
          <div className="xpanse-intergration-header">
            <h2>Integration endpoints</h2>
          </div>
          <div className="xpanse-intergration-header-content">
            {/*<a>Xpanse</a>*/}
            Xpanse supports integrating with a number of external systems. In
            order to connect your Xpanse service with an external system you will
            need to define the endpoint. <br/>Choose from the list which external
            endpoint you want to set up, before enabling the integration on the
            service itself.
          </div>
          {/*<div>*/}
          {/*  Configure endpoints for your external Datadog service to enable the*/}
          {/*  integration with your<br/>*/}
          {/*  Xpanse services.*/}
          {/*</div>*/}
          {/*<div>*/}
          {/*  <a>Help articles</a>*/}
          {/*</div>*/}
          {/*<div>*/}
          {/*  <a>Create new</a>*/}
          {/*</div>*/}
        </div>
      </>
  )
}

export default IntegrationEndpointsHeader;