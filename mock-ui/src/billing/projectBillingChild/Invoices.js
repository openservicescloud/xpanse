import {Table} from 'antd';
import React from 'react';
import {Input, Space} from 'antd';

const {Search} = Input;

function Invoices(props) {
  const onSearch = (value) => console.log(value);
  const columns = [
    {
      title: 'Credit Code',
      dataIndex: 'CreditCode',
      key: 'CreditCode'
    },
    {
      title: 'Credit Type',
      dataIndex: 'CreditType',
      key: 'CreditType',
    },
    {
      title: 'Credit',
      dataIndex: 'Credit',
      key: 'Credit',
    },
    {
      title: 'Remaining Credit',
      key: 'RemainingCredit',
      dataIndex: 'RemainingCredit'
    },
    {
      title: 'Credit Start Date',
      dataIndex: 'CreditStartDate',
      key: 'CreditStartDate'
    },
    {
      title: 'Credit Expiry Date',
      dataIndex: 'CreditExpiryDate',
      key: 'CreditExpiryDate',
    }
  ];
  const data = [
    {
      key: '1',
      CreditCode: 'welcome-3g9h9zm4puze',
      CreditType: 'Free trial credits',
      Credit: 'New York No. 1 Lake Park',
      RemainingCredit: 'developer',
      CreditStartDate: 'developer',
      CreditExpiryDate: 'developer'
    },
    {
      key: '2',
      CreditCode: 'welcome-3g9h9zm4puze',
      CreditType: 'Free trial credits',
      Credit: 'New York No. 1 Lake Park',
      RemainingCredit: 'developer',
      CreditStartDate: 'developer',
      CreditExpiryDate: 'developer'
    },
    {
      key: '3',
      CreditCode: 'welcome-3g9h9zm4puze',
      CreditType: 'Free trial credits',
      Credit: 'New York No. 1 Lake Park',
      RemainingCredit: 'developer',
      CreditStartDate: 'developer',
      CreditExpiryDate: 'developer'
    },
  ];

  return (
      <>
        <h2>Invoices</h2>
        <Space direction="vertical">
          <Search
              placeholder="Search by period..."
              onSearch={onSearch}
              style={{
                width: 250,
              }}
          />
        </Space>
        <Table columns={columns} dataSource={data}/>;
      </>
  )
}

export default Invoices;