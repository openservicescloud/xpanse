import React, {useState} from 'react';
import '../ProjectBilling.css'
import {Table} from 'antd';

function Overview(props) {
  const columns = [
    {
      title: 'Period',
      dataIndex: 'period',
      key: 'period',
      render: (text) => <a>{text}</a>,
    },
    {
      title: 'Billing Group',
      dataIndex: 'billing group',
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
      billinggroup: '32',
      state: 'New York No. 1 Lake Park',
      amount: ['nice', 'developer'],
      tax: '1',
      total: '2'
    },
  ];
  return (
      <>
        <Table columns={columns} dataSource={data}/>;
      </>
  )
}

export default Overview;