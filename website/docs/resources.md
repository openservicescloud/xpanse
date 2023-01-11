---
sidebar_position: 3
---

# Configuration Language

The Open Services Cloud Configuration Language allows you to describe your service and interaction with fundamentals services (computing, network, billing, ...).


The first part of OCL describes the metadata of the basic configuration of the service, the metadata of the resources required by the service, and the development status of the overall resource #OCL第一部分描述服务的基础设施的元数据和服务所需资源的元数据及整体资源的状态

* `state`  is the status of service, Used to describe the delivery status of the overall resource of the service 
           # 服务的状态，用来描述服务整体资源的交付状态
* `resources` is resources needed by the service, Used to describe the infrastructure of the service and the metadata of the resources required by the service
             (storage, computing, network, mirroring, etc.) # 是服务需要的资源，用来描述服务的基础设施和服务所需资源的元数据 (存储、计算、网络、镜像等)

## state
`state`  is the status of service, Used to describe the delivery status of the overall resource of the service

## resources
Resources is a list of `OclResource`,  each `OclResource` contains `attributes` such as `name`, `type`, `properties` 89etc. #Resources是OclResource
列表，每个OclResource 包含 name、type、properties等属性

* `name` - (Required) The name of the Cloud Resource # OclResource 的名称

* `type` - (Required) It is the cloud resource type provided by the cloud vendor.
           Possible values include: `vpc`、`sub_vpc`、`security`、`storage`、`vm`、`baseImage`、`provisioner`、`billing` (more type will be coming).
            # 云厂商提供的云资源类型,可能的值包括：`vpc`、`vpc_subnet`、`security`、`storage`、`vm`、`baseImage`、`provisioner`、`billing`(更多类型即将到来)

* `properties` - (Required) Preset detailed attributes of various cloud resources and their dependent cloud resources, 
                  The dependency relationship of each resource is defined in the properties of the relying party's cloud resource, that is, the `type` property value of the dependent party's cloud resource is used as the key, and the `name` attribute value of the dependent party's cloud resource is used as the value to define in the relying party's cloud resource. In the properties of the resource
            # 预置各类云资源的详细属性及其依赖的云资源资源,各资源的依赖关系在依赖方云资源的properties中定义,即以被依赖方云资源的`type`属性值作为key,以被依赖方云资源的`name`属性值作为value定义在依赖方云资源的properties中

```YAML
state: inactive #编排云资源的属性
resources:
  - type: vpc   #云资源的类型为vpc
    name: vpc1  #云资源的名称(vpc的名称 vpc1)
    properties:
      cidr: 192.168.0.0/16 #VPC IPV4网段
  - type: vpc_subnet #云资源的类型为vpc
    name: subnet-web #云资源的名称
    properties:
        cidr: 192.168.10.0/24   #vpc_subnet IPV4网段
        gatewayIp: 192.168.10.1 #vpc_subnet 网关
        vpc: vpc1 #vpc_sub依赖于vpc,通过key和value值依赖云资源类型为key,名称为value的云资源
        dnsList:  #DNS
          - 100.125.1.250
          - 100.125.129.250
```
### OclResource各类型云资源定义示例

#### vpc

This is the list of VPC defined in the service network. Each VPC has:
* `type` - (Required) The type of OclResource, used in other OCL elements
* `name` - (Required) The name of VPC, used in other OCL elements
* `cidr` - (Required) The VPN IP address range.

```YAML
    type: vpc
    name: vpc1
    properties:
      cidr: 192.168.0.0/16
```

#### vpc_subnet

This is the list of subnet defined in the service network. Each subnet has:
* `type` - (Required) The type of OclResource, used in other OCL elements
* `name` - (Required) The name of the subnet, used in other OCL elements.
* `cidr` - (Required) The subnet IP address range.
* `gatewayIp` -(Required) The subnet IP Gateway.
* `vpc` - (Required) A reference to a resource of type vpc

```YAML
    type: vpcSubnet
    name: subnet-web
    properties:
      cidr: 192.168.10.0/24 
      gatewayIp: 192.168.10.1
      vpc: vpc1
```

#### security

This is the list of security (groups) defined in the service network. Each security has:
* `type` - (Required) The type of OclResource, used in other OCL elements
* `name` - (Required) The name of the security, used in other OCL elements.
* `rules` - (Required) The list of security rule.

##### rules
* `type` - (Required) The type of OclResource, used in other OCL elements
* `name` - (Required) The name of the security rule, define in `security` (groups).
* `priority` - (Required) The priority of the security rule. The lower the priority number, the higher the priority of the rule.
* `protocol` - (Required) Network protocol this rule applies to. Possible values include: `Tcp`,`Udp`,`Icmp`,`*`(which matches all).
* `cidr` - (Required) The IP address range this rule applies to. The `*` matches any IP.
* `direction` - (Required) The direction of the network traffic. Possible values include: `inbound`,`outbound`.
* `ports` - (Required) (Optional) Specifies the port value range, which supports single port (80), continuous port (1-30) and discontinuous port (22, 3389, 80) The valid port values is range form 1 to 65,535.
* `action` - (Required) Specifies whether network traffic is allowed or denied. Possible values include: `allow`,`deny`.

```YAML
    type: security
    name: osc-res-sg
    properties:
      roles:
        - name: osc-res-sg-kafka
          priority: 1
          protocol: tcp
          cidr: 10.10.2.6/32,
          direction: inbound,
          ports: 8080, 9092-9093, 2181
          action: allow
        - name: osc-res-sg-ssh-public
          priority: 1
          protocol: tcp
          cidr: 121.36.59.153/32
          direction: inbound
          ports: 22, 8080, 9092-9093,2181
          action: allow
        - name: osc-res-sg-ssh-private
          priority: 1,
          protocol: tcp,
          cidr: 198.19.0.0/16
          direction: inbound
          ports: 22
          action: allow
```
#### storage
This is the list of storage used by the service. Each Storage has:
* `type` - (Required) The type of OclResource, used in other OCL elements
* `name` - (Required) The name of the storage, used in other OCL elements.
* `strageType` - (Required) The type of the storage, used in other OCL elements.
* `size` - (Required) The size of the storage, used in other OCL elements.

```YAML
    type: storage
    name: osc-res-storage
    properties:
      storageType: SAS
      size: 20GiB
```
#### vm

This is the list of VMs used by the service. Each VM has:
* `type` - (Required) The type of OclResource, used in other OCL elements
* `name` - (Required) The name of the VM, used in other OCL elements
* `vmType` - (Required) The type of the VM, used in other OCL elements
* `vpcSubnet` - (Required) A reference to a resource of type vpcSubnet, used in other OCL elements
* `provisioner` - (Required) A reference to a resource of type provisioner, used in other OCL elements
* `security` - (Required) A reference to a resource of type security, used in other OCL elements
* `storage` - (Required) A reference to a resource of type storage, used in other OCL elements
* `publicly` - (Required) The flag to indicate if the VM should be exposed on Internet (`true`) or only local (`false`).

```YAML
    type: vm
    name: kafka-vm
    properties:
      vmType: c7.large.4
      vpcSubnet: subnet-web
      provisioner: kafka
      security: osc-res-sg
      storage: osc-res-storage
      publicly: true
```
#### baseImage
This is the list of base image. Each `baseImage` has:
* `type` - (Required) The type of OclResource, used in other OCL elements
* `name` - (Required) The name of the `baseImage`,used in other OCL elements
* `baseType` - (Required) The type of the `baseImage`,used in other OCL elements
* `filters` - (Required) The filters to lock the image,used in other OCL elements

```YAML
    type: baseImage
    name: Ubuntu 20.04 server 64bit
    properties:
      baseType: t2.large
      filters:
        name: ubuntu-for-osc-*
```
#### provisioner
This is the list of provisioners. Each provisioner has:
* `type` - (Required) The type of OclResource, used in other OCL elements
* `name` - (Required) The name of the provisioner,used in other OCL elements
* `baseImage` - (Required) A reference to a resource of type baseImage,used in other OCL elements

```YAML
    type: provisioner
    name: my-kafka-release
    properties:
      baseImage: Ubuntu 20.04 server 64bit
```
##### billing
You can configure the business model associated to the service:

* `type` defines the integration into cloud provider billing system.
* `name`
* `model` defines the business model (`flat`, `exponential`, ...)
* `period` defines the rental period (`daily`, `weekly`, `monthly`, `yearly`)
* `currency` defines the billing currency (`euro`, `usd`, ...)
* `fixedPrice` is the fixed price during the period (the price applied one shot whatever is the service use)
* `variablePrice` is the price depending of item volume
* `variableItem` is the item used to calculate the variable price on the period (for instance, the number of instances, the number of transactions, ...)

```YAML
    type: billing
    name:
    properties:
      model: flat
      period: monthly
      currency: euro
      fixedPrice: 20
      variablePrice: 10
      variableItem: instance
```

### Administration Console

`console` element described the integration with CSP admin console, with:

* `backend` is the admin console backend API URL
* `properties` is key/value pairs used by OSC for the console integration

### Observability & Tracing

#### Logging

#### Tracing

#### Metrics (Gauge, ...)

### Identity Management

### Baseline

## Example
OCL Resource configuration for deploying kafka service

```yaml
state: inactive
resources:
  - type: vpc
    name: vpc1
    properties:
      cidr: 192.168.0.0/16
  - type: vpcSubnet
    name: subnet-web
    properties:
      cidr: 192.168.10.0/24
      gatewayIp: 192.168.10.1
      vpc: vpc1
  - type: security
    name: osc-res-sg
    properties:
  - type: storage
    name: osc-res-storage
    properties:
      storageType: SAS
      size: 20GiB
  - type: vm
    name: kafka-vm
    properties:
      vmType: c7.large.4
      vpcSubnet: subnet-web
      provisioner: kafka
      security: osc-res-sg
      storage: osc-res-storage
      publicly: true
  - type: baseImage
    name: Ubuntu 20.04 server 64bit
    properties:
      baseType: t2.large
      filters:
        name: ubuntu-for-osc-*
  - type: baseImage
    name: centos-x64
    properties:
      baseType: t2.large
  - type: provisioner
    name: my-kafka-release
    properties:
      baseImage: Ubuntu 20.04 server 64bit
  - type: billing
    name:
    properties:
      model: flat
      period: monthly
      currency: euro
      fixedPrice: 20
      variablePrice: 10
      variableItem: instance

```

## Terraform Provider

OCL is also available as Terraform Provider, supporting the same details as above.

```hcl
resource "osc_service" "myservice" {
  name      = "my-service"
  category  = "compute"
  namespace = "my-namespace"
  artifacts = ["mvn:groupId/artifactId/version", "https://host/path/to/artifact"]

  billing {
    model         = "flat"
    period        = "monthly"
    currency      = "euro"
    fixedPrice    = 20
    variablePrice = 10
    variableItem  = "instance"
  }

  compute {
    vm {
      name     = "my-vm"
      type     = "t2.large"
      platform = "linux-x64"
      vpc      = "my-vpc"
      subnet   = "my-subnet"
      security = "my-sg"
      storage  = "my-storage"
      publicly = true
    }
  }

  network {
    vpc {
      name   = "my-vpc"
      cidrs  = "172.31.0.0/16"
      routes = ""
      acl    = ""
    }

    subnet {
      name   = "my-subnet"
      vpc    = "my-vpc"
      table  = ""
      routes = ""
    }

    security {
      name     = "my-sg"
      inbound  = ["22->22", "443->443", "80->80"]
      outbound = []
    }
  }

  storage {
    name = "my-storage"
    type = "ssd"
    size = "8GiB"
  }
}
```
