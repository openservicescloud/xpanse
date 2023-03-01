import {Tabs} from 'antd';

function TabChildServices(props) {
  // if (props.select === 1) {
  //   console.log("items");
  // }
  // if (props.select === 2) {
  //   console.log("items2")
  // }
  return (
      <>
        <div>
          <Tabs defaultActiveKey="1" items={props.items}/>
          <Tabs defaultActiveKey="1" items={props.term}/>
        </div>
      </>
  )
}
export default TabChildServices;