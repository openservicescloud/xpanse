import {useState} from "react";
import {Button, Modal} from "antd";

function IntegrationEndpointsRight(props: any) {
  const list = [
    {
      name: 'Datadog',
      key: '1',
      content: <div>
        <a>Datadog</a>
        is a monitoring service for cloud-scale applications, bringing together
        data from servers, <br/>databases, tools, and services to present a
        unified view of an entire stack.
        <i>(Note: Only for metrics, service logs can be sent via remote
          Syslog)</i>
        <div>
          Configure endpoints for your external Datadog service to enable the
          integration with your<br/>
          Xpanse services.
        </div>
      </div>

    }, {
      name: 'AWS Cloudwatch Logs',
      key: '2',
      content: <div>
        <a>AWS CloudWatch®</a>
        Logs is a log storage service provided by Amazon Web Services.
        <div>
          Configure endpoints for your AWS Cloudwatch Logs service to enable the
          integration with your Xpanse services.
        </div>
      </div>
    }, {
      name: 'External Elasticsearch',
      key: '3',
      content: <div>
        <a>Elasticsearch®</a>
        is a commonly used document storage service.
        <div>
          Configure endpoints for your external Elasticsearch service to enable
          the integration with your Xpanse services.
        </div>
      </div>
    }, {
      name: 'Jolokia',
      key: '4',
      content: <div>
        <a>Jolokia</a>
        is an HTTP/REST bridge for accessing monitoring metrics via Java
        Management Extensions (JMX).
        <div>
          The Jolokia integration allows HTTP POST requests to read values from
          the service-specific metrics. Configure endpoints for your Jolokia to
          enable the integration with your Xpanse services.
        </div>
      </div>
    }, {
      name: 'Syslog',
      key: '5',
      content: <div>
        <a>Syslog</a>
        is a commonly used message logging service for auditing, informational,
        analytical, and debugging messages.
        <div>
          Use our Rsyslog integration to send service logs to a different
          monitoring system that supports the Rsyslog protocol. Configure
          endpoints for your remote Syslog to enable the integration with your
          Xpanse services.
        </div>
      </div>
    }
  ];
  const [items, setItems] = useState(list)
  const id = Number(props.index) - 1;
  const [open, setOpen] = useState(false);
  const [confirmLoading, setConfirmLoading] = useState(false);
  const [modalText, setModalText] = useState('Content of the modal');

  const showModal = () => {
    setOpen(true);
  };

  const handleOk = () => {
    setModalText('The modal will be closed after two seconds');
    setConfirmLoading(true);
    setTimeout(() => {
      setOpen(false);
      setConfirmLoading(false);
    }, 2000);
  };

  const handleCancel = () => {
    console.log('Clicked cancel button');
    setOpen(false);
  };
  return (
      <>
        <div className="xpanse-intergration-subright">
          <h2>{items[id].name}</h2>
          {items[id].content}
          <div>
            <a>Help articles</a>
          </div>
          <div>
            <Button type="primary" onClick={showModal}>
              Create New
            </Button>
            <Modal
                title="Title"
                open={open}
                onOk={handleOk}
                confirmLoading={confirmLoading}
                onCancel={handleCancel}
            >
              <p>{modalText}</p>
            </Modal>
          </div>
        </div>
      </>
  )
}

export default IntegrationEndpointsRight;