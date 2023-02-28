import React, {useState} from "react";
import {Datadog,Cloudwatch,Elasticsearch,Jolokia,Syslog} from './IntegrSvgObject'
import IntegrationEndpointsHeader from "./IntegrationEndpointsHeader";
import IntegrationEndpointsLeft from "./IntegrationEndpointsLeft";
import IntegrationEndpointsRight from './IntegrationEndpointsRight';
import type { RadioChangeEvent } from 'antd';
import { Tabs } from 'antd';

type TabPosition = 'left' | 'right' | 'top' | 'bottom';
function IntergrationEndPoints(props: any) {
  const [tabPosition, setTabPosition] = useState<TabPosition>('left');
  const changeTabPosition = (e: RadioChangeEvent) => {
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
              return {
                label: <IntegrationEndpointsLeft index = {id}/>,
                key: id,
                children: <IntegrationEndpointsRight index = {id}/>,
              };
            })}
        />
      </>
  );
}
export default IntergrationEndPoints;