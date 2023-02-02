---
sidebar_position: 3
---

# Configuration Language

The Open Services Cloud Configuration Language allows you to describe your service and interaction
with fundamentals services (computing, network, billing, ...).

The first part of OCL describes the metadata of the basic configuration of the service, the metadata
of the resources required by the service

## resources

`provider` used to describe the provider of the service(huaweicloud, openstack, k8s, etc. more
providers will be coming)
`serviceName` used to describe the name of the service
`resources` used to describe the infrastructure of the service and the metadata of the resources
required by the service(storage, computing, network, mirroring, etc.),`resources` is a list
of `OclResource`, each `OclResource` contains attributes such as `kind`,`id`, `name`,`properties`
etc.

### resource

* `kind` - (Required) It is the cloud resource type provided by the cloud vendor. `vpc`、`subnet`
  、`security`、`storage`、`computeV2`、`image`、、`billing` (more types will be coming).
* `id` - The instance id of the existed Cloud Resource. When reusing existed Cloud resource to fill
  it.
* `name` - (Required when id is empty) The name of the Cloud Resource Possible values include:
  `vpc`、`subnet`
  、`security`、`storage`、`computeV2`、`image`、`provisioner`、`billing` (more type will be coming). Fill it
  when not reusing the existed Cloud Resource but creating new Cloud Resource.
* `properties` -(Required when id is empty) Preset detailed attributes of various cloud resources
  and their dependent cloud resources, The dependency relationship of each resource is defined in
  the properties of the relying party's cloud resource, that is, the `type` property value of the
  dependent party's cloud resource is used as the key, and the `name` attribute value of the
  dependent party's cloud resource is used as the value to define in the relying party's cloud
  resource. In the properties of the resource

```YAML
provider: huaweicloud
serviceName: computeV2-ubutun-demo
resources:
  - kind: vpc
    id:
    name: vpc1
    properties:
      cidr: 192.168.0.0/16
  - kind: subnet
    id:
    name: subnet-web
    properties:
      cidr: 192.168.10.0/24
      vpc: vpc1 # subnet depends on vpc
      dnsList:
        - 100.125.1.250
        - 100.125.129.250
```

### Examples of various types of OclResource

#### vpc

This is the list of VPC defined in the service network. Each VPC has:

* `kind` - (Required) The type of OclResource, used in other OCL elements
* `id` - The name of existed VPC, used in other OCL elements
* `name` - (Required when id is empty)The name of VPC, used in other OCL elements
* `properties`-(Required when id is empty):
    * `cidr` - The VPN IP address range.

```YAML
    kind: vpc
    id:
    name: vpc1
    properties:
      cidr: 192.168.0.0/16
```

#### subnet

This is the list of subnet defined in the service network. Each subnet has:

* `kind` - (Required) The type of OclResource, used in other OCL elements
* `id` - The name of existed VPC, used in other OCL elements
* `name` - (Required when id is empty) The name of the subnet, used in other OCL elements.
* `properties` -(Required when id is empty):
    * `cidr` - (Required when id is empty) The subnet IP address range.
    * `vpc` - (Required when id is empty) A reference to a resource of type vpc

```YAML
    kind: subnet
    id:
    name: subnet-web
    properties:
      cidr: 192.168.10.0/24
      gatewayIp: 192.168.10.1
      vpc: vpc1
```

#### security

This is the list of security (groups) defined in the service network. Each security has:

* `kind` - (Required) The type of OclResource, used in other OCL elements
* `id` - The id of the security, used in other OCL elements.
* `name` - (Required when id is empty) The name of the security, used in other OCL elements.
* `properties` -(Required when id is empty):
    * `rules` - (Required) The list of security rule.

##### rules

* `name` - (Required) The name of the security rule, define in `security` (groups).
* `priority` - (Required) The priority of the security rule. The lower the priority number, the
  higher the priority of the rule.
* `protocol` - (Required) Network protocol this rule applies to. Possible values include: `Tcp`
  ,`Udp`,`Icmp`,`*`(which matches all).
* `cidr` - (Required) The IP address range this rule applies to. The `*` matches any IP.
* `direction` - (Required) The direction of the network traffic. Possible values include: `inbound`
  ,`outbound`.
* `ports` - (Required) (Optional) Specifies the port value range, which supports single port (80),
  continuous port (1-30) and discontinuous port (22, 3389, 80) The valid port values is range form 1
  to 65,535.
* `action` - (Required) Specifies whether network traffic is allowed or denied. Possible values
  include: `allow`,`deny`.

```YAML
    kind: security
    id:
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

* `kind` - (Required) The type of OclResource, used in other OCL elements
* `id` - The id of the storage, used in other OCL elements.
* `name` - (Required when id is empty) The name of the storage, used in other OCL elements.
* `properties` -(Required when id is empty):
    * `type` - (Required when id is empty) The type of the storage, used in other OCL elements.
    * `size` - (Required when id is empty) The size of the storage, used in other OCL elements.

```YAML
    kind: storage
    id:
    name: osc-res-storage
    properties:
      type: SAS
      size: 20GiB
```

#### image

This is the list of base image. Each `image` has:

* `kind` - (Required) The type of OclResource, used in other OCL elements
* `id` - The id of the `image`,used in other OCL elements
* `name` - (Required when id is empty) The name of the `image`,used in other OCL elements
* `properties` -(Required when id is empty):
    * `type` - (Required) The type of the `image`,used in other OCL elements

```YAML
    kind: image
    id:
    name: Ubuntu 20.04 server 64bit
    properties:
      type: t2.large

```

#### computeV2

This is the list of VMs used by the service. Each VM has:

* `kind` - (Required) The type of OclResource, used in other OCL elements
* `id` - The id of the VM, used in other OCL elements
* `name` - (Required when id is empty) The name of the VM, used in other OCL elements
* `properties` -(Required when id is empty):
    * `type` - (Required) The type of the VM, used in other OCL elements
    * `subnet` - (Required) A reference to a resource of type vpcSubnet, used in other OCL elements
    * `security` - (Required) A reference to a resource of type security, used in other OCL elements
    * `storage` - (Required) A reference to a resource of type storage, used in other OCL elements
    * `publicly` - (Required) The flag to indicate if the VM should be exposed on Internet
      (`true`) or only local (`false`).

```YAML
    kind: computeV2
    id:
    name: kafka-computeV2
    properties:
      type: c7.large.4
      submit: subnet-web
      image: kafka
      security: osc-res-sg
      storage: osc-res-storage
      publicly: true
```

##### billing

You can configure the business model associated to the service:

* `kind` defines the integration into cloud provider billing system.
* `name`
* `id`
* `properties` -(Required when id is empty):
    * `model` defines the business model (`flat`, `exponential`, ...)
    * `period` defines the rental period (`daily`, `weekly`, `monthly`, `yearly`)
    * `currency` defines the billing currency (`euro`, `usd`, ...)
    * `fixedPrice` is the fixed price during the period (the price applied one shot whatever is the
      service use)
    * `variablePrice` is the price depending of item volume
    * `variableItem` is the item used to calculate the variable price on the period (for instance,
      the number of instances, the number of transactions, ...)

```YAML
    kind: billing
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

OCL Resource configuration for deploying ECS

```yaml
resources:
  - kind: vpc
    id:
    name: vpc1
    properties:
      cidr: 192.168.0.0/16
  - kind: subnet
    id:
    name: subnet-web
    properties:
      cidr: 192.168.10.0/24
      gatewayIp: 192.168.10.1
      vpc: vpc1
  - kind: security
    id:
    name: osc-res-sg
    properties:
      rules:
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
  - kind: storage
    id:
    name: osc-res-storage
    properties:
      type: SAS
      size: 20GiB
  - kind: computeV2
    id:
    name: kafka-computeV2
    properties:
      type: c7.large.4
      subnet: subnet-web
      provisioner: kafka
      security: osc-res-sg
      storage: osc-res-storage
      publicly: true
  - kind: image
    id:
    name: Ubuntu 20.04 server 64bit
    properties:
      type: t2.large
  - kind: image
    id:
    name: centos-x64
      properties:
      type: t2.large
  - kind: billing
    id:
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
  kind  = "computeV2"
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

  computeV2 {
    computeV2 {
      name     = "my-computeV2"
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
