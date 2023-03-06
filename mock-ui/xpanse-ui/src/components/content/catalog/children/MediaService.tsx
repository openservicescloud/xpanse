import React, {useState} from 'react';
import {Breadcrumb, Col, Row, Space, TabsProps, Tree} from 'antd';
import type { DataNode, TreeProps } from 'antd/es/tree';
import {HomeOutlined} from "@ant-design/icons";
import ServicesTabs from "./ServicesTabs";
import ServicesTab from "./ServiceTab";

function MediaService(): JSX.Element {

  const[selectProduct, setSelectProduct] = useState(false);
  const items: TabsProps['items'] = [
    {
      key: '1',
      label: <h1 className='text-title'>Huawei</h1>,
      children: <ServicesTab/>,
    },
    {
      key: '2',
      label: <h1 className='text-title'>AWS</h1>,
      children: <ServicesTab />,
    },
    {
      key: '3',
      label: <h1 className='text-title'>AZure</h1>,
      children: <ServicesTab/>,
    },
  ];
  const treeData: DataNode[] = [
    {
      title: 'Compute',
      key: '0-1',
      children:[
        {
          title:'Version-1',
          key:'0-1-1',
        },{
          title:'Version-2',
          key:'0-1-2',
        },
      ]
    },
    {
      title: 'Rancher',
      key: '0-2',
      children:[
        {
          title:'Rancher-1',
          key:'0-2-1',
        },{
          title:'Rancher-2',
          key:'0-2-2',
        },
      ]
    },
    {
      title: <Space>Kafka</Space> ,
      key: '0-3',
    },
  ];
  const onSelect: TreeProps['onSelect'] = (selectedKeys, info) => {
    console.log('selected', selectedKeys, info);
    setSelectProduct(true);
  };

  const onCheck: TreeProps['onCheck'] = (checkedKeys, info) => {
    console.log('onCheckerdewew3', checkedKeys, info);
  };

  return (
      <>
        <Breadcrumb>
          <Breadcrumb.Item><HomeOutlined/><span> / Service Product</span></Breadcrumb.Item>
        </Breadcrumb>
        <Row gutter={24}>
          <Col span={8}>
            <Tree
                checkable
                defaultExpandedKeys={['0-0-0', '0-0-1']}
                defaultSelectedKeys={['0-0-0', '0-0-1']}
                defaultCheckedKeys={['0-0-0', '0-0-1']}
                onSelect={onSelect}
                onCheck={onCheck}
                treeData={treeData}
            />
          </Col>
          <Col span={16}>
            {
              selectProduct?<ServicesTabs items={items} />:<div/>
            }
          </Col>
        </Row>
      </>
  );
}
export default MediaService;
