import {useEffect, useState} from "react";
import '../../../../styles/catalog.css';
import {DataNode} from "antd/es/tree";
import ServiceTree from "./components/ServiceTree";
import ServiceProvider from "./components/ServiceProvider";
import {getServiceVendorData} from "../../../../xpanse-api/service-vendor/api";

function MiddleWare(): JSX.Element {
  const [key, setKey] = useState<string>('');
  const [treeData, setTreeData] = useState<DataNode[]>([]);
  const [cloudProvider, setCloudProvider] = useState<any[]>([]);


  useEffect(() => {
    console.log('render index')
    getServiceVendorData()
    .then(rsp => {
      const data = rsp.data.data;
      setCloudProvider(data);

      let tData: DataNode[] = []
      data.forEach(service => {
        let dn: DataNode = {
          title: service.name,
          key: service.name,
          children: [],
        };

        service.versionList.forEach(v => {
          dn.children!.push({
            title: v.version,
            key: service.name + '@' + v.version,
          });
        });

        tData.push(dn);
      });
      setTreeData(tData);
    });
  }, []);

  return (
      <div className="container">
        <div className="left-class">
          <div className="left-title-class">Service Tree: {key}</div>
          <ServiceTree treeData={treeData} setKey={setKey}/>
        </div>
        <div className="middle-class"/>
        <div className="right-class">
          <div className="left-title-class">Cloud Provider</div>
          <ServiceProvider cloudProvider={cloudProvider} serviceName={key}/>
        </div>
      </div>
  );
}

export default MiddleWare;