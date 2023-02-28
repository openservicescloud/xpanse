import {Datadog,Cloudwatch,Elasticsearch,Jolokia,Syslog} from './IntegrSvgObject'

function IntegrationEndpointsLeft(props: any) {
  const list = [
    {
      picture: Datadog,
      key: '1',
      content: 'Datadog',
    }, {
      picture: Cloudwatch,
      key:'2',
      content: 'AWS Cloudwatch Logs',
    }, {
      picture: Elasticsearch,
      key: '3',
      content: 'External Elasticsearch',
    },{
      picture: Jolokia,
      key:'4',
      content: 'Jolokia',
    },{
      picture: Syslog,
      key:'5',
      content: 'Syslog',
    }
  ];
  const id = Number(props.index) - 1;
  return(
      <>
        <a><div>
          <img src={list[id].picture}/>
          {list[id].content}
        </div></a>
      </>
  )

}
export default IntegrationEndpointsLeft;