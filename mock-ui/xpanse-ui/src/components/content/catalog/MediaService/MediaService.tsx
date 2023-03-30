import React, {useEffect, useState} from 'react';
import {Breadcrumb, Col, Row, TabsProps, Tree} from 'antd';
import type { DataNode, TreeProps } from 'antd/es/tree';
import {HomeOutlined} from "@ant-design/icons";
import ServicesTabs from "./ServicesTabs";
import ServicesTab from "./ServiceTab";
import axios from "axios";

function MediaService(): JSX.Element {

  interface DataType {
    key: React.Key;
    Property: string;
    Information: string;
  }

  const getService = () => {
    return axios.get(`/xpanse/service`);
  }
  const [cloudProvider, setCloudProvider] = useState<any[]>([]);
  const [serviceList, setServiceList] = useState<any[]>([]);
  const providerData: DataType[] = [
    {
      key: '1',
      Property: 'Category',
      Information: 'MiddleWare',
    },{
      key: '2',
      Property: 'Provider',
      Information: 'New York No. 1 Lake Park',
    },
    {
      key: '3',
      Property: 'Service Version',
      Information: 'V1.0',
    },
    {
      key: '4',
      Property: 'Billing Mode',
      Information: 'Monthly Per Service Instance',
    },
    {
      key: '5',
      Property: 'Regullar Pricing',
      Information: '￥140.00',
    },
    {
      key: '6',
      Property: 'Register Time',
      Information: '2022-08-26 T08:25:15:208Z',
    },
    {
      key: '7',
      Property: 'Status',
      Information: '2022-08-26 T08:25:15:208Z',
    },
    {
      key: '8',
      Property: 'Flavors',
      Information: 'London No. Park',
    },
  ]
  const[selectProduct, setSelectProduct] = useState(false);

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
      title: 'Kafka',
      key: '0-3',
    },
  ];

  useEffect(() => {
    getService().then(rsp => {
      const data = rsp.data.data;
      setServiceList(data);

      let tData: DataNode[] = []
      console.log('data'+ data)

      /*data.forEach(t => {
        let dn:DataNode = {
          title: t.name,
          key: t.name,
          children: [],
        };

        t.versions.forEach(v => {
          dn.children!.push({
            title: v.version,
            key: t.name + '@' + v.version,
          });
        });

        tData.push(dn);
      });*/
    });
  }, []);
  console.log('serviceList'+serviceList)
  const items = serviceList.map((item,index)=>{
    return (
        {
          key: {index},
          label: <h1 className='text-title'>{item.name}</h1>,
          children: <ServicesTab data={item.versionList}/>,
        }
    )
  });

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
        <div className="container">
          <div className="left-class">
            <Tree
                checkable
                defaultExpandedKeys={['0-0-0', '0-0-1']}
                defaultSelectedKeys={['0-0-0', '0-0-1']}
                defaultCheckedKeys={['0-0-0', '0-0-1']}
                onSelect={onSelect}
                onCheck={onCheck}
                treeData={treeData}
            />
          </div>
          <div className="middle-class"/>
          <div className="right-class">
            {
              selectProduct?<ServicesTabs items={items} />:<div/>
            }
          </div>
        </div>
      </>
  );
}
export default MediaService;
