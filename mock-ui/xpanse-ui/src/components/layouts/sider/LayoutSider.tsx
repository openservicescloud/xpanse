import {Image, Layout, Menu, MenuProps} from 'antd';
import React, {useState} from 'react';
import { useNavigate } from 'react-router-dom';
import {Link } from 'react-router-dom';
import {homePageRoute} from '../../utils/constants';
function LayoutSider(): JSX.Element {
  const [collapsed, setCollapsed] = useState(false);
    const navigate = useNavigate();
  const onSelected = function (cfg: any) {
    navigate(cfg.key);
  }
    const item: MenuProps['items'] = [{
      key: "/services",
      label: "Services",
    }, {
      key: "/billing",
      label: "Billing",
    }, {
      key: "/integration-endpoints",
      label: "IntegrationEndpoints",
    },{
      key: "sub3",
      label: "subnav 3",
      children: [
        {key: 9, label: "option9"},
        {key: 10, label: "option10"},
        {key: 11, label: "option11"},
        {key: 12, label: "option12"}
      ]
    }];
    return (
        <Layout.Sider collapsible collapsed={collapsed} onCollapse={(newValue) => setCollapsed(newValue)}>
          <div className="logo">
            <Link to={homePageRoute}>
              <Image width={50} src="./huawei_logo.png" preview={false}/>
            </Link>
          </div>
          <Menu items={item} mode="inline" theme="dark" onSelect={onSelected}/>
        </Layout.Sider>
    );
}
export default LayoutSider;