import React, {useState} from "react";
import {Datadog,Cloudwatch,Elasticsearch,Jolokia,Syslog} from './IntegrSvgObject'
import IntegrationEndpointsHeader from "./IntegrationEndpointsHeader";
import IntegrationEndpointsLeft from "./IntegrationEndpointsLeft";
import IntegrationEndpointsRight from './IntegrationEndpointsRight';
import {Col, Row} from "antd";
import { Radio, Space, Tabs } from 'antd';

function IntergrationEndPoints(props) {
  const [tabPosition, setTabPosition] = useState('left');
  const changeTabPosition = (e) => {
    setTabPosition(e.target.value);
  };
  const [items, setItems] = useState([
    {
      key: '1',
      label: `Tab 1`,
      children: Datadog,
    }]);

  // const list = [
  //   {
  //     picture: Datadog,
  //     key: '1',
  //     content: 'Datadog',
  //   }, {
  //     picture: Cloudwatch,
  //     key:'2',
  //     content: 'AWS Cloudwatch Logs',
  //   }, {
  //     picture: Elasticsearch,
  //     key: '3',
  //     content: 'External Elasticsearch',
  //   },{
  //     picture: Jolokia,
  //     key:'4',
  //     content: 'Jolokia',
  //   },{
  //     picture: Syslog,
  //     key:'5',
  //     content: 'props.id',
  //   }
  // ];
  return (
      <>
        <div>
          <IntegrationEndpointsHeader/>
        </div>
        <Tabs
            tabPosition={tabPosition}
            items={new Array(5).fill(null).map((_, i) => {
              const id = String(i + 1);
              return {
                label: <IntegrationEndpointsLeft/>,
                key: id,
                children: <IntegrationEndpointsRight/>,
              };
            })}
        />
      </>
  );
}
export default IntergrationEndPoints;