import React, {useState} from 'react';
import './ProjectBilling.css'
import {Tabs} from 'antd';
import Overview from "./projectBillingChild/Overview";
import Credits from "./projectBillingChild/Credits";
import Invoices from "./projectBillingChild/Invoices";
import BillingInformation from "./projectBillingChild/BillingInformation";
import {WarningOutlined} from '@ant-design/icons';

function ProjectBilling(props) {
  const onChange = (key) => {
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
        <div>
          <h1 class="typography-heading text-grey-80">Project Billing</h1>
        </div>
        <div className="xpanse-BillingGroupHeader">
          <div className="typography-heading">
            <h1>User-1030</h1>
          </div>
          <div className="xpanse-BillingGroupHeader-detail-container">
            <div className="xpanse-BillingGroupHeader-detail-title">Current
              accumulated monthly bill
            </div>
            <div className="aiven-BillingGroupHeader-detail-balance">$ 11.37
              USD
            </div>
            <div className="xpanse-BillingGroupHeader-detail-payment">No credit
              card assigned
            </div>
          </div>
        </div>
        <div>
          <div><WarningOutlined/>Waring</div>
          <div>There is no payment method set up for this project.</div>
        </div>
        <div>
          <Tabs defaultActiveKey="1" items={items} onChange={onChange}/>;
        </div>
      </>
  )
}

export default ProjectBilling;