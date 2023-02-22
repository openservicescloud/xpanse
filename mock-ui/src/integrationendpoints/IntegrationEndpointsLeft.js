import {useState} from "react";
import {Datadog,Cloudwatch,Elasticsearch,Jolokia,Syslog} from './IntegrSvgObject'

function IntegrationEndpointsLeft(props) {
  const list = [
    {
      picture: Datadog,
      key: '1',
      content: 'Datadog',
    }, {
      picture: Cloudwatch,
      key:'2',
      content: 'AWS Cloudwatch Logs',
    }, {
      picture: Elasticsearch,
      key: '3',
      content: 'External Elasticsearch',
    },{
      picture: Jolokia,
      key:'4',
      content: 'Jolokia',
    },{
      picture: Syslog,
      key:'5',
      content: 'props.id',
    }
  ];

  return(
      <>
        <a><div>
          <img src={list[0].picture}/></div></a>
      </>
  )

}
export default IntegrationEndpointsLeft;