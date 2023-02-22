import React, {useState} from 'react';
import './Credits.css'
import {Button, Input, Space, Modal, Select} from 'antd';
import CreditCard from "./CreditCard";

const {Search} = Input;

function BillingInformation(props) {

  function onClick(number) {

    if (number === 1) {
      <CreditCard/>
    }
  }

  const [isModalOpen, setIsModalOpen] = useState(false);
  const showModal = () => {
    setIsModalOpen(true);
  };
  const handleOk = () => {
    setIsModalOpen(false);
  };
  const handleCancel = () => {
    setIsModalOpen(false);
  };
  const onChange = (value) => {
    console.log(`selected ${value}`);
  };
  const onSearch = (value) => {
    console.log('search:', value);
  };
  const {TextArea} = Input;

  return (
      <>
        <div>
          <h2>Current accumulated monthly bill</h2>
          <h2>$ 11.37</h2>
          <span className="xpanse-BillingMethod-currency">USD</span>
        </div>
        <Button type="primary" onClick={showModal}>
          Open Modal
        </Button>
        <Modal title="Basic Modal" open={isModalOpen} onOk={handleOk}
               onCancel={handleCancel}>
          <p>Some contents...</p>
          <p>Some contents...</p>
          <p>Some contents...</p>
        </Modal>
        <h2>Billing Profile </h2>
        <h2>Company name:</h2>
        <Input placeholder="Basic usage"/>
        <h2>Address line1</h2>
        <Input placeholder="Basic usage"/>
        <h2>Address line2</h2>
        <Input placeholder="Basic usage"/>
        <h2>Country</h2>
        <Select
            showSearch
            placeholder="Select a person"
            optionFilterProp="children"
            onChange={onChange}
            onSearch={onSearch}
            filterOption={(input, option) =>
                (option?.label ?? '').toLowerCase().includes(
                    input.toLowerCase())
            }
            options={[
              {
                value: 'jack',
                label: 'Jack',
              },
              {
                value: 'lucy',
                label: 'Lucy',
              },
              {
                value: 'tom',
                label: 'Tom',
              },
            ]}
        />
        <h2>City</h2>
        <Input placeholder="Basic usage"/>
        <div>
          <div> StateProvince
            <Select
                showSearch
                placeholder="Select a person"
                optionFilterProp="children"
                onChange={onChange}
                onSearch={onSearch}
                filterOption={(input, option) =>
                    (option?.label ?? '').toLowerCase().includes(
                        input.toLowerCase())
                }
                options={[
                  {
                    value: 'jack',
                    label: 'Jack',
                  },
                  {
                    value: 'lucy',
                    label: 'Lucy',
                  },
                  {
                    value: 'tom',
                    label: 'Tom',
                  },
                ]}
            />
          </div>
          <div>ZIP/Postal Code
            <Input placeholder="Basic usage"/>
          </div>
          <h2>Currency</h2>
          <Select
              showSearch
              placeholder="Select a person"
              optionFilterProp="children"
              onChange={onChange}
              onSearch={onSearch}
              filterOption={(input, option) =>
                  (option?.label ?? '').toLowerCase().includes(
                      input.toLowerCase())
              }
              options={[
                {
                  value: 'jack',
                  label: 'Jack',
                },
                {
                  value: 'lucy',
                  label: 'Lucy',
                },
                {
                  value: 'tom',
                  label: 'Tom',
                },
              ]}
          />
          <h2>Billing email</h2>
          <Input placeholder="Basic usage"/>
          <h2>Additional Info</h2>
          <TextArea rows={4}/>
        </div>
      </>
  )

}

export default BillingInformation;