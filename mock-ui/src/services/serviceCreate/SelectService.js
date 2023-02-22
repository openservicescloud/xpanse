import React, {useState} from "react";
import './HuaWeiCloudServices.css';
import {ApacheKafka,Redis,Mysql,OpenSearch,Grafana,InfluxDB} from './SvgObjects'

function SelectService(props) {
  const list = [
    {
      picture: ApacheKafka,
      name: 'ApacheKafka',
      content: 'Kafka - High-Throughput Distributed Messaging System',
    }, {
      picture: Mysql,
      name: 'MySQL',
      content: 'MySQL - Relational Database Management System',
    }, {
      picture: Redis,
      name: 'M3DM®',
      content: 'M3DB - Distributed time series database',
    },{
      picture: OpenSearch,
      name:'OpenSearch®',
      content: 'OpenSearch - Search & Analyze Data in Real Time, derived from Elasticsearch v7.10.2',
    },{
      picture: Grafana,
      name:'Grafana',
      content: 'Grafana - Metrics Dashboard',
    },
    {
      picture: InfluxDB,
      name:'InfluxDB',
      content: 'InfluxDB - Distributed Time Series Database',
    }
  ];

  const [isSelected, setIsSelected] = useState();
  return (
      <div className='xpanse-Service-CloudBox'>
        {
          list.map((item,index)=>{
            return(
                <div key={index} className={isSelected === index ? 'xpanse-ServiceTypeOption-detail-selected' : 'xpanse-ServiceTypeOption-detail'}
                     onClick={() => setIsSelected(index)}>
                    <div className="xpanse-ServiceTypeOption-image">
                      <img src={item.picture} className="xpanse-ServiceTypeOption-service-icon" alt=""/>
                    </div>
                  <div className="xpanse-ServiceTypeOption-info">
                    <span className="xpanse-ServiceTypeOption">{item.name}</span>
                    <span className="xpanse-ServiceTypeOption-description">{item.content}</span>
                  </div>
                </div>
            )
          })
        }
      </div>
  )
}
export default SelectService;