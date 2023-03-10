---
sidebar_position: 3
---

# Configuration Language

The Open Services Cloud Configuration Language allows you to describe your service and interaction
with fundamentals services (computing, network, billing, ...).

The first part of the OCL service descriptor is basically service metadata:

* `name` is the name of the service, used to identify the service in the CSP catalog, user console,
  etc
* `category` is the overall category of the service. It's especially use to integrate the service in
  the right menu of the user console.
* `namespace` is the location of the service. It could be in a CSP subdomain, in a region, and any
  kind of CSP classification.

### Billing

The `billing` component defines the integration into cloud provider billing system.

You can configure the business model associated to the service:

* `model` defines the business model (`flat`, `exponential`, ...)
* `period` defines the rental period (`daily`, `weekly`, `monthly`, `yearly`)
* `currency` defines the billing currency (`euro`, `usd`, ...)
* `fixedPrice` is the fixed price during the period (the price applied one shot whatever is the
  service use)
* `variablePrice` is the price depending of item volume

### Compute

#### VMs

This is the list of VMs used by the service. Each VM has:

* `name` - (Required) The name of the VM.
* `type` - (Required) The VM type, like `t2.large` for example.
* `subnets` - (Required) A list of `JsonPath` of the `subnets`. The subnet for main Nic must be in
  the first place.
* `securityGroups` - (Required) A list of `JsonPath` of `securityGroups`.
* `storages` - (Required) A list of `JsonPath` of the `storages`.
* `publicly` - (Required) The flag to indicate if the VM should be exposed on Internet (`true`) or
  only local (`false`).

### Network

#### VPC

This is the list of VPC defined in the service network. Each VPC has:

* `name` - (Required) The name of VPC, used in other OCL elements
* `cidr` - (Required) The VPN IP address range.

#### Subnets

This is the list of subnet defined in the service network. Each subnet has:

* `name` - (Required) The name of the subnet, used in other OCL elements.
* `vpc` - (Required) The `JsonPath` of the VPC where the subnet is attached.
* `cidr` - (Required) The subnet IP address range.

#### SecurityGroups

This is the list of securityGroup defined in the service network. Each securityGroup has:

* `name` - (Required) The name of the securityGroup, used in other OCL elements.
* `rules` - (Required) The list of securityGroup rule.

##### SecurityRule

* `name` - (Required) The name of the securityGroup rule, define in `securityGroups`.
* `priority` - (Required) The priority of the securityGroup rule. The lower the priority number, the
  higher the priority of the rule.
* `protocol` - (Required) Network protocol this rule applies to. Possible values include: `tcp`
  ,`udp`,`icmp`,`*`(which matches all).
* `cidr` - (Required) The IP address range this rule applies to. The `*` matches any IP.
* `direction` - (Required) The direction of the network traffic. Possible values include: `in`,`out`
  .
* `ports` - (Required) (Optional) Specifies the port value range, which supports single port (80),
  continuous port (
  1-30) and discontinuous port (22, 3389, 80) The valid port values is range form 1 to 65,535.
* `action` - (Required) Specifies whether network traffic is allowed or denied. Possible values
  include: `allow`,`deny`.

### Storages

### Observability & Tracing

#### Logging

#### Tracing

#### Metrics (Gauge, ...)

### Identity Management

### Baseline

## Example

```json
{
  "name": "my-service",
  "category": "compute",
  "namespace": "my-namespace",
  "billing": {
    "model": "flat",
    "period": "monthly",
    "currency": "euro",
    "fixedPrice": 20,
    "variablePrice": 10
  },
  "compute": {
    "vm": [
      {
        "name": "my-vm",
        "type": "t2.large",
        "image": "c7d4ff3e-a851-11ed-b9df-f329738732c0",
        "subnets": [
          "$.network.subnets[0]"
        ],
        "securityGroups": [
          "$.network.securityGroups[0]"
        ],
        "storages": [
          "$.storages[0]"
        ],
        "publicly": true
      }
    ]
  },
  "network": {
    "vpc": [
      {
        "name": "my-vpc",
        "cidr": "172.31.0.0/16"
      },
      {
        "name": "my-another-vpc",
        "cidr": "172.32.0.0/16"
      }
    ],
    "subnets": [
      {
        "name": "my-subnet",
        "vpc": "$.network.vpc[0]",
        "cidr": "172.31.1.0/24"
      }
    ],
    "securityGroups": [
      {
        "name": "my-sg",
        "rules": [
          {
            "name": "my-app-msg",
            "priority": 1,
            "protocol": "TCP",
            "cidr": "172.31.2.0/24",
            "direction": "inbound",
            "ports": "3389",
            "action": "allow"
          }
        ]
      }
    ]
  },
  "storages": [
    {
      "name": "my-storage",
      "type": "ssd",
      "size": "80",
      "sizeUnit": "GB"
    }
  ]
}
```

## Terraform Provider

OCL is also available as Terraform Provider, supporting the same details as above.

```hcl
resource "osc_service" "myservice" {
  name      = "my-service"
  category  = "compute"
  namespace = "my-namespace"

  billing {
    model         = "flat"
    period        = "monthly"
    currency      = "euro"
    fixedPrice    = 20
    variablePrice = 10
  }

  compute {
    vm {
      name          = "my-vm"
      type          = "t2.large"
      platform      = "linux-x64"
      vpc           = "my-vpc"
      subnet        = "my-subnet"
      securityGroup = "my-sg"
      storage       = "my-storage"
      publicly      = true
    }
  }

  network {
    vpc {
      name = "my-vpc"
      cidr = "172.31.0.0/16"
    }

    subnet {
      name = "my-subnet"
      vpc  = "my-vpc"
    }
  }

  storage {
    name     = "my-storage"
    type     = "ssd"
    size     = "80"
    sizeUnit = "GB"
  }
}
```
