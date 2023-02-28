import {Card, List, Pagination} from 'antd';
import React from 'react';
import {useState} from 'react';
import '../../../../styles/billing/credit.css'
import {
  AudioOutlined,
  ExclamationCircleOutlined,
  InfoCircleOutlined
} from '@ant-design/icons';
import {Input, Space, Table, Tag} from 'antd';

const {Search} = Input;

function Credits(props : any) {
  const suffix = (
      <AudioOutlined
          style={{
            fontSize: 16,
            color: '#1890ff',
          }}
      />
  );
  const onSearch = (value : any) => console.log(value);

  const [current, setCurrent] = useState(3);
  const onChange = (page : any) => {
    console.log(page);
    setCurrent(page);
  };

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
        <div className="xpanse-credits-head">
          <h2>Credits<InfoCircleOutlined/></h2>
          <div className="xpanse-available-credits-left">
            <h2>Available Credits</h2>
            <p className="xpanse-BillingGroupCreditsTab-available-credits-card-credits">$
              300.00 USD</p>
            <Input placeholder="Basic usage"/>
          </div>
          <div className="xpanse-search-table-credits-right">
            <div className="xpanse-search-table-right-credit-head">
              <div className="search-credit">
                <Space direction="vertical">
                  <Search
                      placeholder="Search by credit code..."
                      onSearch={onSearch}
                      style={{
                        width: 250,
                      }}
                  />
                </Space>
              </div>
              <div className="page-credit">
                <Pagination current={current} onChange={onChange} total={50}/>;
              </div>
            </div>
            <div className="search-code">
              <Table columns={columns} dataSource={data}/>
            </div>
          </div>
        </div>
      </>
  )
}

export default Credits;