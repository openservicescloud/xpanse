import React, {useState} from 'react';
import {Breadcrumb, Space} from 'antd';
import TabChildServices from './TabChildServices';
import {Link} from "react-router-dom";
import {BankOutlined, HomeOutlined, InfoCircleTwoTone} from "@ant-design/icons";
import '../../index.css';
import './HuaWeiCloudServices.css';
import SelectService from "./SelectService";
import {Aws, Google, Upcloud} from "./SvgObjects";

function HuaWeiCloudServices(props) {
  const [term] = useState([{
    key: '1',
    label: `Term 1`,
    children: `Content of Tab Pane 1`,
  },
    {
      key: '2',
      label: `Tab 2`,
      children: `Content of Tab Pane 2`,
    },
    {
      key: '3',
      label: `Tab 3`,
      children: `Content of Tab Pane 3`,
    },
  ]);
  const [items, setItems] = useState([
    {
      key: '1',
      label: `Tab 1`,
      children: `Content of Tab Pane 1`,
    },
    {
      key: '2',
      label: `Tab 2`,
      children: `Content of Tab Pane 2`,
    },
    {
      key: '3',
      label: `Tab 3`,
      children: `Content of Tab Pane 3`,
    },
  ]);

  function onClick(ch) {
    if (ch === 1) {
      setItems([{
        key: '1',
        label: `Africa`,
        children: `aws-af-south-1`,
      },
        {
          key: '2',
          label: `Asia Pacific`,
          children: `aws-ap-east-1`,
        },
        {
          key: '3',
          label: `Europe`,
          children: `aws-eu-central-1`,
        }])
    }

    if (ch === 2) {
      setItems([
        {
          key: '1',
          label: `Asia Pacific`,
          children: `google-asia-east1`,
        },
        {
          key: '2',
          label: `Australia`,
          children: `google-australia-southeast1`,
        },
        {
          key: '3',
          label: `Europe`,
          children: `google-europe-central2`,
        },
        {
          key: '4',
          label: `Middle East`,
          children: `google-me-west1`,
        },
        {
          key: '5',
          label: `North America`,
          children: `google-northamerica-northeast1`,
        }
      ]);
    }

    if (ch === 3){
      setItems([{
          key: '1',
          label: `Asia Pacific`,
          children: `upcloud-sg-sin`,
        },
        {
          key: '2',
          label: `North America`,
          children: `upcloud-us-chi`,
        }])
    }
  }
  const providerList = [
    {
      picture: Aws
    }, {
      picture: Google
    }, {
      picture: Upcloud
    }
  ];
  const [ isSelected, setIsSelected ] = useState();
  const colorT='25px';
  return (
      <>
        <Breadcrumb>
          <Breadcrumb.Item><Link to={'/'}><HomeOutlined /><span>Home</span></Link></Breadcrumb.Item>
          <Breadcrumb.Item><Link to={'/huaweicloudservices'}><BankOutlined /><span>HuaWeiCloudServices</span></Link></Breadcrumb.Item>
        </Breadcrumb>
        <h1 className='xpanse-title-typography-heading'>Create services</h1>
        <h1>1.0 Select Your Service</h1>
        <SelectService/>
        <h1>2.0 Select Service Cloud Provider</h1>
        <Space>
          <div className='xpanse-Service-CloudBox'>
            {
              providerList.map( (item, index ) => {
                return (
                    <div key={index} className={isSelected===index ? 'xpanse-ServiceCreateScreen-CloudBox-selected' : 'xpanse-ServiceCreateScreen-CloudBox-unselected'}
                         onClick={() => {
                           setIsSelected(index);
                           onClick(index+1);
                         }}>
                      <div>
                        <img className="xpanse-CloudBox-Service-logo" src={item.picture} alt=""/>
                      </div>
                    </div>
                )
              })
            }
          </div>
        </Space>
        <h1>3.0 Select Service Cloud Region</h1>
        <TabChildServices items={items}/>
        <h1>4.0 Select Service Plan</h1>
        <TabChildServices items={term}/>
        <h1>5. Provide Service Name</h1>
        <div>
          <div className="xpanse-provide-service-name">
            <InfoCircleTwoTone style={{"fontSize":colorT,"color":"#999999"}}/>
            <span className="xpanse-provide-service-word-hint"> The service name cannot be changed afterwards.</span>
          </div>
          <div className="xpanse-relative-full">
            <span className="xpanse-relative-text">Name</span>
            <span className="xpanse-Special-characters">*</span>
            <span className="xpanse-relative-small">
                  <input defaultValue="mysql-30648b4d"/>
              </span>
          </div>
        </div>
      </>
  )
}

export default HuaWeiCloudServices;