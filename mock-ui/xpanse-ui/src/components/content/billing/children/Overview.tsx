import React, {useState} from 'react';
import '../../../../styles/billing/billing.css'
import {Table} from 'antd';

function Overview(props : any) {
  const columns = [
    {
      title: 'Period',
      dataIndex: 'period',
      key: 'period',
      // render: (text : any) => <a>{text}</a>,
    },
    {
      title: 'Billing Group',
      dataIndex: 'billinggroup',
      key: 'billing group',
    },
    {
      title: 'State',
      dataIndex: 'state',
      key: 'state',
    },
    {
      title: 'Amount',
      key: 'amount',
      dataIndex: 'amount',
    },
    {
      title: 'Tax',
      dataIndex: 'tax',
      key: 'tax',
    },
    {
      title: 'Total',
      dataIndex: 'total',
      key: 'total',
    },
    {
      title: 'Actions',
      key: 'actions',
    },
  ];
  const data = [
    {
      key: '1',
      period: 'Feb 1 -Feb 28,2023',
      billinggroup: 'Default billing group for alice-1030',
      state: 'New York No. 1 Lake Park',
      amount: ['nice', 'developer'],
      tax: '1',
      total: '2'
    },
  ];
  return (
      <>
        <div className="xpanse-BillingOverview-TableHeader">
          <h2>Invoices</h2>

          <div className="xpanse-BillingOverview-TableHeaderContent">
            <a href="">All invoices</a>
          </div>
        </div>
        <Table columns={columns} dataSource={data}/>
        <div className="xpanse-BillingOverview-TableHeader">
          <h2>Credits</h2>
          <div className="xpanse-BillingOverview-TableHeaderContent">
            <a href="">All credits</a>
          </div>
        </div>
        <Table columns={columns} dataSource={data}/>
      </>
  )
}

export default Overview;