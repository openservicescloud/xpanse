import React, {useState} from "react";
import {Datadog,Cloudwatch,Elasticsearch,Jolokia,Syslog} from './IntegrSvgObject'
import IntegrationEndpointsHeader from "./IntegrationEndpointsHeader";
import IntegrationEndpointsLeft from "./IntegrationEndpointsLeft";
import IntegrationEndpointsRight from './IntegrationEndpointsRight';
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

  return (
      <>
        <div>
          <IntegrationEndpointsHeader/>
        </div>
        <Tabs
            tabPosition={tabPosition}
            items={new Array(5).fill(null).map((_, i) => {
              const id = String(i + 1);
              const [index, setIndex] = new useState(id);
              return {
                label: <IntegrationEndpointsLeft index = {index}/>,
                key: id,
                children: <IntegrationEndpointsRight index = {index}/>,
              };
            })}
        />
      </>
  );
}
export default IntergrationEndPoints;