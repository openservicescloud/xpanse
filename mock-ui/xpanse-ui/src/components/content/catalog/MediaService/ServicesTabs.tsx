import React from 'react';
import { Tabs } from 'antd';
import './MediaService.css';
import { tab } from '@testing-library/user-event/dist/tab';

const ServicesTabs: React.FC<{// @ts-ignore
  items:tab[]}> = (props) =>{
  return (
      <Tabs defaultActiveKey="1" items={props.items}/>
  );
}
export default ServicesTabs;