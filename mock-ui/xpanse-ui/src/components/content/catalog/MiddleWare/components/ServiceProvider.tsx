import React, {useEffect, useState} from 'react';
import {Tabs} from 'antd';
import ServiceDetail, {ServiceDetailProps} from "./ServiceDetail";


let lastServiceName: string = '';

function ServiceProvider(
    {
      cloudProvider,
      serviceName
    }: {
      cloudProvider: ServiceVendor.Service[],
      serviceName: string
    }): JSX.Element {
  const [activeKey, setActiveKey] = useState<string>('');
  const [serviceDetails, setServiceDetails] = useState<ServiceVendor.CloudProviderDetails>({});

  const mapper: Map<string, ServiceVendor.CloudProviderDetails> = new Map<string, ServiceVendor.CloudProviderDetails>();
  const [name, version] = serviceName.split('@');

  const items = cloudProvider.filter(service => service.name === name)
  .flatMap(service => service.versionList)
  .filter(v => v.version === version)
  .flatMap(v => {
    return v.cloudProviderList.map((cloudProvider:any) => {
      const key = serviceName + '@' + cloudProvider.name;
      mapper.set(key, cloudProvider.details);
      return {
        label: cloudProvider.name,
        key: cloudProvider.name,
        children: [],
      }
    });
  });


  let defaultServiceDetail: ServiceVendor.CloudProviderDetails = {};
  if (items.length > 0 && lastServiceName !== serviceName) {
    setTimeout(() => {
      const key = serviceName + '@' + items[0].key;
      const details = mapper.get(key);
      if (details) {
        defaultServiceDetail = details;
      }

      setServiceDetails(defaultServiceDetail);
      setActiveKey(items[0].key);
      console.log("active key: " + items[0].key);
    }, 10);
  }
  lastServiceName = serviceName;

  const onChange = (key: string) => {
    setActiveKey(key);
    const mk = serviceName + '@' + key;
    const details = mapper.get(mk);
    if (details) {
      setServiceDetails(details);
    }
  }
  console.log("service Name", serviceName)
  return (
      <>
        {serviceName.length > 0 ?
            <Tabs items={items} onChange={onChange} activeKey={activeKey}/> : <></>}
        <ServiceDetail serviceDetails={serviceDetails}/>
      </>);
}

export default ServiceProvider;