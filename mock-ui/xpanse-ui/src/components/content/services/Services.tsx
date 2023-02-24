import React, {useState} from 'react';
import {Aws,Google,Upcloud} from './SvgObject';
import TabServicesChild from "./TabServicesChild";
import { HomeOutlined, UserOutlined } from '@ant-design/icons';
import { Breadcrumb } from 'antd';
import {Link} from "react-router-dom";

const providerList = [
  {
    picture: Aws
  }, {
    picture: Google
  }, {
    picture: Upcloud
  }
]
function Services() {
    return(
        <>
          <Breadcrumb>
            <Breadcrumb.Item><Link to={'/'}><HomeOutlined /><span>Home</span></Link></Breadcrumb.Item>
            <Breadcrumb.Item>
              <UserOutlined />
              <span>Application List</span>
            </Breadcrumb.Item>
            <Breadcrumb.Item>Application</Breadcrumb.Item>
          </Breadcrumb>
          <h1>services</h1>
          <TabServicesChild/>
        </>
    )
}
export default Services;