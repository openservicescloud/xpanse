package org.eclipse.osc.modules.ocl.loader.oclv2;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.InetAddressValidator;

@Data
class PortPair {

    private long from;
    private long to;

    PortPair(long from, long to) {
        this.from = from;
        this.to = to;
    }
}

@Slf4j
class OclV2ToHcl {

    private final OclV2 ocl;

    OclV2ToHcl(OclV2 ocl) {
        this.ocl = ocl;
    }

    private List<PortPair> getPortPairs(String ports) {
        List<PortPair> portPairs = new ArrayList<>();

        String[] portArray = ports.split(",");
        for (String port : portArray) {
            if (port.contains("-")) {
                String[] portPair = port.split("-");
                if (portPair.length == 2) {
                    portPairs.add(new PortPair(Integer.parseInt(portPair[0].strip()),
                        Integer.parseInt(portPair[1].strip())));
                }
            } else {
                portPairs.add(
                    new PortPair(Integer.parseInt(port.strip()), Integer.parseInt(port.strip())));
            }
        }

        return portPairs;
    }

    private String getHclVariables(String... hclVars) {
        StringBuilder hcl = new StringBuilder();
        for (String hclVar : hclVars) {
            hcl.append(String.format("variable \"%s\" {"
                + "\n  type = string\n"
                + "\n}\n", hclVar));
        }
        return hcl.toString();
    }

    public String getHclSecurityGroupRule() {
        if (ocl == null
            || ocl.getSecurities() == null
            || ocl.getSecurities().get(0).getRules() == null) {
            throw new IllegalArgumentException("Ocl for security group rule is invalid.");
        }

        StringBuilder hcl = new StringBuilder();

        // todo: parse ports to port_range

        for (var secGroup : ocl.getSecurities()) {
            int index = 0;
            String securityStr = secGroup.isExistedResource() ?
                "data.huaweicloud_networking_secgroup" : "huaweicloud_networking_secgroup";
            for (var securityRule : secGroup.getRules()) {
                var portPairs = getPortPairs(securityRule.getPorts());

                for (PortPair portPair : portPairs) {
                    hcl.append(String.format(
                        "\nresource \"huaweicloud_networking_secgroup_rule\"  \"%s_%d\" {"
                            + "\n  security_group_id = %s.%s.id"
                            + "\n  direction         = \"%s\""
                            + "\n  ethertype         = \"IPV4\""
                            + "\n  protocol          = \"%s\""
                            + "\n  port_range_min    = \"%s\""
                            + "\n  port_range_max    = \"%s\""
                            + "\n  remote_ip_prefix  = \"%s\""
                            + "\n}\n\n",
                        securityRule.getName(), index++, securityStr, secGroup.getName(),
                        securityRule.getDirection().equals("inbound") ? "ingress" : "egress",
                        securityRule.getProtocol(), portPair.getFrom(), portPair.getTo(),
                        securityRule.getCidr()));
                }
            }
        }
        return hcl.toString();
    }

    public String getHclSecurityGroup() {
        if (ocl == null
            || ocl.getSecurities() == null) {
            throw new IllegalArgumentException("Ocl for security group is invalid.");
        }

        StringBuilder hcl = new StringBuilder();
        for (var secGroup : ocl.getSecurities()) {
            if (secGroup.isExistedResource()) {
                hcl.append(String.format("\ndata \"huaweicloud_networking_secgroup\" \"%s\" {"
                        + "\n  secgroup_id = \"%s\""
                        + "\n}\n\n",
                    secGroup.getName(), secGroup.getId()));
            } else {
                hcl.append(String.format("\nresource \"huaweicloud_networking_secgroup\" \"%s\" {"
                        + "\n  name = \"%s\""
                        + "\n}\n\n",
                    secGroup.getName(), secGroup.getName()));
            }
        }
        return hcl.toString();
    }

    public String getHclVpc() {
        if (ocl == null
            || ocl.getVpcList() == null) {
            throw new IllegalArgumentException("Ocl for VPC is invalid.");
        }

        StringBuilder hcl = new StringBuilder();
        for (var vpc : ocl.getVpcList()) {
            if (vpc.isExistedResource()) {
                hcl.append(String.format("\ndata \"huaweicloud_vpc\" \"%s\" {"
                        + "\n  id = \"%s\""
                        + "\n}\n\n",
                    vpc.getName(), vpc.getId()));
            } else {
                hcl.append(String.format("\nresource \"huaweicloud_vpc\" \"%s\" {"
                        + "\n  name = \"%s\""
                        + "\n  cidr = \"%s\""
                        + "\n}\n\n",
                    vpc.getName(), vpc.getName(), vpc.getCidr()));
            }
        }
        return hcl.toString();
    }

    public String getHclVpcSubnet() {
        if (ocl == null
            || ocl.getSubnets() == null) {
            throw new IllegalArgumentException("Ocl for VPC subnet is invalid.");
        }

        StringBuilder hcl = new StringBuilder();
        InetAddressValidator validator = InetAddressValidator.getInstance();
        for (var subnet : ocl.getSubnets()) {
            if (subnet.isExistedResource()) {
                hcl.append(String.format("\ndata \"huaweicloud_vpc_subnet\" \"%s\" {"
                        + "\n  id = \"%s\""
                        + "\n}\n\n",
                    subnet.getName(),
                    subnet.getId()));
            } else {
                hcl.append(String.format("\nresource \"huaweicloud_vpc_subnet\" \"%s\" {"
                        + "\n  name = \"%s\""
                        + "\n  cidr = \"%s\"",
                    subnet.getName(), subnet.getName(), subnet.getCidr()));

                String gateway = subnet.getCidr().replaceAll("\\.\\d*/.*", ".1");
                gateway = gateway.replaceAll(":\\d*/.*", ":1");
                if (validator.isValid(gateway)) {
                    hcl.append("\n  gateway_ip = \"").append(gateway).append("\"");
                }
                if (Objects.nonNull(subnet.getVpc())) {
                    if (subnet.getVpc().isExistedResource()) {
                        hcl.append("\n  vpc_id = data.huaweicloud_vpc.")
                            .append(subnet.getVpc().getName())
                            .append(".id");
                    } else {
                        hcl.append("\n  vpc_id = huaweicloud_vpc.")
                            .append(subnet.getVpc().getName())
                            .append(".id");
                    }
                }
                hcl.append("\n}\n\n");
            }
        }

        return hcl.toString();
    }

    public String getHclAvailabilityZone() {
        if (ocl == null) {
            throw new IllegalArgumentException("Ocl for AvailabilityZone is invalid.");
        }
        return "\ndata \"huaweicloud_availability_zones\" \"osc-az\" {}";
    }

    public String getHclFlavor() {
        if (ocl == null) {
            throw new IllegalArgumentException("Ocl for flavor is invalid.");
        }
        return "";
    }

    public String getHclImage() {
        if (ocl == null) {
            throw new IllegalArgumentException("Ocl for image is invalid.");
        }
        return "";
    }

    public String getHclCompute() {
        if (ocl == null
            || ocl.getComputes() == null) {
            throw new IllegalArgumentException("Ocl for compute is invalid.");
        }

        StringBuilder hcl = new StringBuilder();
        for (var compute : ocl.getComputes()) {
            if (compute.isExistedResource()) {
                hcl.append(String.format("\ndata \"huaweicloud_compute_instance\" \"%s\" {"
                        + "\n  id = \"%s\"",
                    compute.getName(), compute.getId()));
            } else {
                hcl.append(String.format("\nresource \"huaweicloud_compute_instance\" \"%s\" {"
                        + "\n  name = \"%s\"",
                    compute.getName(), compute.getName()));
                hcl.append("\n  availability_zone = data.huaweicloud_availability_zones.osc-az"
                    + ".names[0]");
                if (Objects.nonNull(compute.getImage())) {
                    if (StringUtils.isNotBlank(compute.getImage().getId())) {
                        hcl.append("\n  image_id = \"").append(compute.getImage().getId())
                            .append("\"");
                    } else if (StringUtils.isNotBlank(compute.getImage().getName())) {
                        String imageName = compute.getImage().getName();
                        if (StringUtils.isNotBlank(
                            compute.getImage().getProperties().get("name").toString())) {
                            imageName = compute.getImage().getProperties().get("name").toString();
                        }
                        hcl.append("\n  image_name = \"").append(imageName)
                            .append("\"");

                    } else {
                        hcl.append("\n  image_id = image not found\"");
                    }
                }
                hcl.append("\n  flavor_id = \"").append(compute.getType()).append("\"");

                for (var subnet : compute.getSubnets()) {
                    if (Objects.nonNull(subnet)) {
                        if (subnet.isExistedResource()) {
                            hcl.append("\n  network {\n    uuid = data.huaweicloud_vpc_subnet.")
                                .append(subnet.getName())
                                .append(".id\n  }");
                        } else {
                            hcl.append("\n  network {\n    uuid = huaweicloud_vpc_subnet.")
                                .append(subnet.getName())
                                .append(".id\n  }");
                        }
                    }
                }

                hcl.append("\n  admin_pass = \"Cloud#1234\"");

                List<String> existedSecGroupList = new ArrayList<>();
                List<String> securityGroupList = new ArrayList<>();
                for (var secGroup : compute.getSecurities()) {
                    if (Objects.nonNull(secGroup)) {
                        if (secGroup.isExistedResource()) {
                            existedSecGroupList.add(secGroup.getName());
                        } else {
                            securityGroupList.add(secGroup.getName());
                        }
                    }
                }
                String existedSecGroupIds =
                    existedSecGroupList.stream()
                        .map(group -> "data.huaweicloud_networking_secgroup." + group + ".id")
                        .collect(Collectors.joining(","));
                hcl.append("\n  security_group_ids = [ ").append(existedSecGroupIds);
                String securityGroupIds =
                    securityGroupList.stream()
                        .map(group -> "huaweicloud_networking_secgroup." + group + ".id")
                        .collect(Collectors.joining(","));
                hcl.append(securityGroupIds).append(" ]");
                hcl.append("\n}\n\n");

                if (compute.isPublicly()) {
                    hcl.append(String.format(""
                            + "resource \"huaweicloud_vpc_eip\" \"osc-eip-%s\" {\n"
                            + "  publicip {\n"
                            + "    type = \"5_sbgp\"\n"
                            + "  }\n"
                            + "  bandwidth {\n"
                            + "    name        = \"osc-eip-%s\"\n"
                            + "    size        = 5\n"
                            + "    share_type  = \"PER\"\n"
                            + "    charge_mode = \"traffic\"\n"
                            + "  }\n"
                            + "}\n"
                            + "\n"
                            + "resource \"huaweicloud_compute_eip_associate\" "
                            + "\"osc-eip-associated-%s\" {\n"
                            + "  public_ip   = huaweicloud_vpc_eip.osc-eip-%s.address\n"
                            + "  instance_id = huaweicloud_compute_instance.%s.id\n"
                            + "}", compute.getName(), compute.getName(), compute.getName(),
                        compute.getName(), compute.getName()));
                }
            }
        }

        return hcl.toString();
    }

    public String getHclStorage() {
        if (ocl == null || ocl.getStorages() == null) {
            throw new IllegalArgumentException("Ocl for storage is invalid.");
        }

        StringBuilder hcl = new StringBuilder();
        for (var storage : ocl.getStorages()) {
            if (storage.isExistedResource()) {
                log.info(
                    "The provider huaweicloud/huaweicloud does not support data source "
                        + "\"huaweicloud_evs_volume\"");
            } else {
                hcl.append(String.format("\nresource \"huaweicloud_evs_volume\" \"%s\" {"
                        + "\n  name = \"%s\""
                        + "\n  volume_type = \"%s\""
                        + "\n  size = \"%s\"",
                    storage.getName(), storage.getName(), storage.getType(),
                    storage.getSize().replaceAll("[^0-9]*GiB", "").strip()));
                // TODO: Add variable [var.availability_zone] for availability_zone
                hcl.append("\n  availability_zone = data.huaweicloud_availability_zones.osc-az"
                    + ".names[0]");
                hcl.append("\n}\n\n");
            }
            if (storage.isExistedResource()) {
                hcl.append(String.format(""
                        + "\nresource \"huaweicloud_compute_volume_attach\" \"osc-attached-%s\" {\n"
                        + "  volume_id   = \"%s\"",
                    storage.getName(), storage.getId()));
            } else {
                hcl.append(String.format(""
                        + "\nresource \"huaweicloud_compute_volume_attach\" \"osc-attached-%s\" {\n"
                        + "  volume_id   = huaweicloud_evs_volume.%s.id",
                    storage.getName(), storage.getName()));
            }
            for (var compute : ocl.getComputes()) {
                hcl.append(String.format("\n  instance_id = huaweicloud_compute_instance.%s.id\n"
                    + "}", compute.getName()));
            }
        }
        return hcl.toString();
    }

    public String getHcl() {
        return getHclVariables()
            + getHclSecurityGroup()
            + getHclSecurityGroupRule()
            + getHclVpc()
            + getHclCompute()
            + getHclVpcSubnet()
            + getHclFlavor()
            + getHclImage()
            + getHclAvailabilityZone()
            + getHclStorage();
    }

}
