import React, {useState} from 'react';
import '../../../styles/billing/billing.css';
import  '../../../styles/billing/credit.css';
import  '../../../styles/billing/billingInformation.css';
import Credits from "./children/Credits";
import Invoices from "./children/Invoices";
import Overview from "./children/Overview";
import BillingInformation from "./children/BillingInformation";
import {Tabs} from 'antd';
import {Breadcrumb, Layout, Menu, Switch, theme} from 'antd';
import {Link} from "react-router-dom";
import {HomeOutlined, UserOutlined, WarningOutlined} from "@ant-design/icons";


function Billing() {
  const onChange = (key : any) => {
    console.log(key);
  };

  const items = [
    {
      key: '1',
      label: `Overview`,
      children: <Overview/>,
    },
    {
      key: '2',
      label: `Credits`,
      children: <Credits/>,
    },
    {
      key: '3',
      label: `Invoices`,
      children: <Invoices/>,
    },
    {
      key: '4',
      label: `BillingInformation`,
      children: <BillingInformation/>,
    },
  ];
  return (
      <>
        <Breadcrumb>
          <Breadcrumb.Item><Link to={'/'}><HomeOutlined/><span>Home</span></Link></Breadcrumb.Item>
          <Breadcrumb.Item>Billing</Breadcrumb.Item>
        </Breadcrumb>

        {/*<div>*/}
        {/*  <h1 className="typography-heading">Project Billing</h1>*/}
        {/*</div>*/}
        <div className="xpanse-BillingGroupHeader">
          <div className="typography-heading">
            <h1>User-1030</h1>
          </div>
          <div className="xpanse-BillingGroupHeader-detail-container">
            <div className="xpanse-BillingGroupHeader-detail-title">Current
              accumulated monthly bill
            </div>
            <div className="xpanse-BillingGroupHeader-detail-balance">$ 11.37
              USD
            </div>
            <div className="xpanse-BillingGroupHeader-detail-payment">No credit
              card assigned
            </div>
          </div>
        </div>
        <div className='xpanse-BillingGroupHeader-Waring'>
          <div><WarningOutlined/>Waring</div>
          <div>There is no payment method set up for this project.</div>
        </div>
        <div>
          <Tabs defaultActiveKey="1" items={items} onChange={onChange}/>
        </div>
      </>
  )
}

export default Billing;