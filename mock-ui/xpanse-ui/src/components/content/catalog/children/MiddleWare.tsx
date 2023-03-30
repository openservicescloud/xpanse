import React, {useState} from 'react';
import {Col, Row, Space, Tree} from 'antd';
import {DataNode, TreeProps} from "antd/es/tree";
import MiddlerWareTabs from "./MiddlerWareTabs";
import {
  SystemStatus
} from "../../../../xpanse-api/generated";
import {serviceVendorApi} from "../../../../xpanse-api/xpanseRestApiClient";
import { ServiceStatusServiceStateEnum } from '../../../../xpanse-api/generated/models/ServiceStatus';


function MiddleWare(): JSX.Element {
  const [serviceState, setServiceState] = useState<ServiceStatusServiceStateEnum>('starting')
  const [serviceName, setServiceName] = useState<string>()
  const [serviceVersion, setServiceVersion] = useState<string>()
  const [serviceData, setServiceData] = useState([]);

  serviceVendorApi
  .listRegisteredServices()
  .then((response)=>{
  })
  .catch((error: any) =>{
    console.error(error);
  })

  const treeData: DataNode[] = [
    {
      title: serviceName,
      key: '0-0',
      children: [
        {
          title: '3.4.0',
          key: '0-0-0',
          disabled: false,
        },
        {
          title: '3.4.3',
          key: '0-0-1',
        },
      ],
    },
    {
      title: 'ActiveMQ',
      key: '0-1',
      children: [
        {
          title: '5.6.1',
          key: '0-1-0',
          disabled: false,
        },
        {
          title: '5.6.5',
          key: '0-1-1',
        },
      ],
    }
  ];

  const onSelect: TreeProps['onSelect'] = (selectedKeys, info) => {
    console.log('selected', selectedKeys, info);
  };

  const onCheck: TreeProps['onCheck'] = (checkedKeys, info) => {
    console.log('onCheck', checkedKeys, info);
  };


  return (
      <>
        <Row  gutter={24}>
          <Col span={8}>
              <Tree
                  checkable
                  defaultExpandedKeys={['0-0', '0-1']}
                  defaultSelectedKeys={['0-0', '0-1']}
                  defaultCheckedKeys={['0-0', '0-1']}
                  onSelect={onSelect}
                  onCheck={onCheck}
                  treeData={treeData}
              />
          </Col>
          <Col span={16}>
            <MiddlerWareTabs />
          </Col>
        </Row>
      </>
  );
}

export default MiddleWare;