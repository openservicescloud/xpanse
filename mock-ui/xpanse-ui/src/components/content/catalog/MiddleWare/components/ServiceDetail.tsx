import React from 'react';
import {Descriptions} from 'antd';


export type ServiceDetailProps = {
  product: string,
  billing: string,
  time: string,
  amount: string,
  discount: string,
  official: string
}

function ServiceDetail({serviceDetails}: { serviceDetails: ServiceVendor.CloudProviderDetails }): JSX.Element {
  return (
      <Descriptions bordered column={1}>
        <Descriptions.Item label="Product" labelStyle={{width: '230px'}}>{serviceDetails.product}</Descriptions.Item>
        <Descriptions.Item label="Billing">{serviceDetails.billing}</Descriptions.Item>
        <Descriptions.Item label="time">{serviceDetails.time}</Descriptions.Item>
        <Descriptions.Item label="Amount">{serviceDetails.amount}</Descriptions.Item>
        <Descriptions.Item label="Discount">{serviceDetails.discount}</Descriptions.Item>
        <Descriptions.Item label="Official">{serviceDetails.official}</Descriptions.Item>
        <Descriptions.Item label="Config Info">
          Data disk type: MongoDB
          <br/>
          Database version: 3.4
          <br/>
          Package: dds.mongo.mid
          <br/>
          Storage space: 10 GB
          <br/>
          Replication factor: 3
          <br/>
          Region: East China 1
        </Descriptions.Item>
      </Descriptions>
  );
}

export default ServiceDetail;