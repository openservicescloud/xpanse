package org.eclipse.osc.orchestrator.plugin.huaweicloud.builders.terraform;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.InetAddressValidator;
import org.eclipse.osc.modules.ocl.loader.oclV2.OclResourceV2;
import org.eclipse.osc.modules.ocl.loader.oclV2.OclResourcesV2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OclResources2Hcl {

    public static String getOclResourcesHcl(OclResourcesV2 oclResources) {
        List<OclResourceV2> resources = oclResources.getResources();
        if (resources.isEmpty()) {
            throw new IllegalArgumentException("Resources is invalid.");
        }
        return getHclVariables()
                + getVpcHcl(resources)
                + getSubnetHcl(resources)
                + getSecurityHcl(resources)
                + getSecurityRuleHcl(resources)
                + getStorageHcl(resources)
                + getHclAvailabilityZone(resources)
                + getVmHcl(resources)
                + getImage(resources);
    }

    private static String getVpcHcl(List<OclResourceV2> resources) {
        StringBuilder sb = new StringBuilder();
        List<OclResourceV2> vpcResources = searchOclResource(resources, "vpc");
        for (OclResourceV2 vpc : vpcResources) {
            if (StringUtils.isNotBlank(vpc.getId())) {
                sb.append(String.format("data \"huaweicloud_vpc\" \"%s\" { \n id = \"%s\" \n}\n\n", vpc.getName(), vpc.getId()));
            } else {
                sb.append(String.format("resource \"huaweicloud_vpc\" \"%s\" {"
                        + "\n  name = \"%s\"", vpc.getName(), vpc.getName()));
                if (vpc.getProperties().containsKey("cidr")) {
                    sb.append(String.format("\n  cidr = \"%s\"", vpc.getProperties().get("cidr")));
                } else {
                    sb.append(String.format("\n  cidr = \"%s\"", "vpc_cidr_not_found"));
                }
                sb.append("\n}\n\n");
            }
        }
        return sb.toString();
    }

    private static String getSubnetHcl(List<OclResourceV2> resources) {
        StringBuilder sb = new StringBuilder();
        List<OclResourceV2> vpcResources = searchOclResource(resources, "subnet");
        InetAddressValidator validator = InetAddressValidator.getInstance();
        for (OclResourceV2 subnet : vpcResources) {
            if (StringUtils.isNotBlank(subnet.getId())) {
                sb.append(String.format("data \"huaweicloud_vpc_subnet\" \"%s\" { \n id = \"%s\" \n}\n\n",
                        subnet.getName(), subnet.getId()));
            } else {
                sb.append(String.format("resource \"huaweicloud_vpc_subnet\" \"%s\" {"
                        + "\n  name = \"%s\"", subnet.getName(), subnet.getName()));
                String cidr = (String) subnet.getProperties().get("cidr");
                if (subnet.getProperties().containsKey("cidr")) {
                    sb.append(String.format("\n  cidr = \"%s\"", cidr));
                } else {
                    sb.append(String.format("\n  cidr = \"%s\"", "subnet_cidr_not_found"));
                }

                String gateway = cidr.replaceAll("\\.\\d*/.*", ".1");
                gateway = gateway.replaceAll(":\\d*/.*", ":1");
                if (validator.isValid(gateway)) {
                    sb.append("\n  gateway_ip = \"").append(gateway).append("\"");
                }
                if (subnet.getProperties().containsKey("vpc")) {
                    String subnetVpcName = (String) subnet.getProperties().get("vpc");
                    List<OclResourceV2> subnetVpcList = searchOclResource(resources, "vpc", subnetVpcName);
                    for (OclResourceV2 subnetVpc : subnetVpcList) {
                        sb.append("\n  vpc_id = huaweicloud_vpc.")
                                .append(subnetVpc.getName())
                                .append(".id");
                    }
                }
                sb.append("\n}\n\n");
            }
        }

        return sb.toString();
    }

    private static String getSecurityHcl(List<OclResourceV2> resources) {
        StringBuilder sb = new StringBuilder();
        List<OclResourceV2> securityList = searchOclResource(resources, "security");
        for (OclResourceV2 security : securityList) {
            if (StringUtils.isNotBlank(security.getId())) {
                sb.append(String.format("data \"huaweicloud_networking_secgroup\" \"%s\" { \n secgroup_id = \"%s\" \n}\n\n",
                        security.getName(), security.getId()));
            } else {
                sb.append(String.format("resource \"huaweicloud_networking_secgroup\" \"%s\" {"
                                + "\n  name = \"%s\""
                                + "\n}\n\n",
                        security.getName(), security.getName()));
            }
        }
        return sb.toString();
    }

    private static String getSecurityRuleHcl(List<OclResourceV2> resources) {
        StringBuilder sb = new StringBuilder();
        List<OclResourceV2> securityList = searchOclResource(resources, "security");
        for (OclResourceV2 security : securityList) {
            String securityName = security.getName();
            if (security.getProperties().containsKey("rules")) {
                List<Map<String, String>> secRuleList = (List<Map<String, String>>) (security.getProperties().get("rules"));
                int index = 0;
                for (int i = 0; i < secRuleList.size(); i++) {
                    var portPairs = getPortPairs(secRuleList.get(i).get("ports"));
                    for (PortPair portPair : portPairs) {
                        sb.append(String.format(
                                "resource \"huaweicloud_networking_secgroup_rule\"  \"%s_%d\" {", secRuleList.get(i).get("name"), index++));
                        if (StringUtils.isNotBlank(security.getId())) {
                            sb.append(String.format("\n  security_group_id = data.huaweicloud_networking_secgroup.%s.id", securityName));
                        } else {
                            sb.append(String.format("\n  security_group_id = huaweicloud_networking_secgroup.%s.id", securityName));
                        }
                        sb.append(String.format("\n  direction  = \"%s\""
                                        + "\n  ethertype         = \"IPV4\""
                                        + "\n  protocol          = \"%s\""
                                        + "\n  port_range_min    = \"%s\""
                                        + "\n  port_range_max    = \"%s\""
                                        + "\n  remote_ip_prefix  = \"%s\""
                                        + "\n}\n\n",
                                secRuleList.get(i).get("direction").equals("inbound") ? "ingress" : "egress",
                                secRuleList.get(i).get("protocol"), portPair.getFrom(), portPair.getTo(),
                                secRuleList.get(i).get("cidr")));
                    }
                }
            }
        }
        return sb.toString();
    }

    private static String getStorageHcl(List<OclResourceV2> resources) {
        StringBuilder sb = new StringBuilder();
        List<OclResourceV2> storageList = searchOclResource(resources, "storage");
        for (OclResourceV2 storage : storageList) {
            if (StringUtils.isNotBlank(storage.getId())) {
                sb.append(String.format(""
                        + "resource \"huaweicloud_compute_volume_attach\" \"osc-attached-%s\" {\n"
                        + "  volume_id   = \"%s\"  \n", storage.getName(), storage.getId()));
            } else {
                sb.append(String.format("\nresource \"huaweicloud_evs_volume\" \"%s\" {"
                        + "\n  name = \"%s\"", storage.getName(), storage.getName()));
                if (storage.getProperties().containsKey("type")) {
                    String type = (String) storage.getProperties().get("type");
                    sb.append(String.format("\n  volume_type = \"%s\"", type));
                }

                if (storage.getProperties().containsKey("size")) {
                    String storageSize = (String) storage.getProperties().get("size");
                    sb.append(String.format("\n  size = \"%s\"", storageSize.replaceAll("[^0-9]*GiB", "").strip()));
                }
                // TODO: Add variable [var.availability_zone] for availability_zone
                sb.append("\n  availability_zone = data.huaweicloud_availability_zones.osc-az"
                        + ".names[0] \n}\n\n");
                sb.append(String.format(""
                        + "resource \"huaweicloud_compute_volume_attach\" \"osc-attached-%s\" {\n"
                        + "  volume_id   = huaweicloud_evs_volume.%s.id\n", storage.getName(), storage.getName()));
            }
            // vm
            List<OclResourceV2> vmOclResourceList = searchOclResource(resources, "vm");
            for (OclResourceV2 vm : vmOclResourceList) {
                sb.append(String.format("  instance_id = huaweicloud_compute_instance.%s.id \n}\n\n", vm.getName()));
            }
        }
        return sb.toString();
    }

    private static String getVmHcl(List<OclResourceV2> resources) {
        StringBuilder sb = new StringBuilder();
        List<OclResourceV2> vmOclResourceList = searchOclResource(resources, "vm");
        for (OclResourceV2 vm : vmOclResourceList) {

            sb.append(String.format("\nresource \"huaweicloud_compute_instance\" \"%s\" {"
                    + "\n  name = \"%s\"", vm.getName(), vm.getName()));

            sb.append("\n  availability_zone = data.huaweicloud_availability_zones.osc-az"
                    + ".names[0]");
            // image
            if (vm.getProperties().containsKey("image")) {
                String vmImageName = (String) vm.getProperties().get("image");
                List<OclResourceV2> imageList = searchOclResource(resources, "image", vmImageName);
                for (OclResourceV2 image : imageList) {
                    String imageId = image.getId();
                    String imageName = image.getName();
                    if (StringUtils.isNotBlank(imageId)) {
                        sb.append("\n  image_id = \"").append(imageId).append("\"");
                    } else if (StringUtils.isNotBlank(imageName)) {
                        sb.append("\n  image_name = \"").append(imageName).append("\"");
                    } else {
                        sb.append("\n  image_id = \"").append("image_not_found").append("\"");
                    }
                }
            }
            sb.append("\n  flavor_id = \"").append(vm.getProperties().get("type")).append("\"");
            // subnet
            if (vm.getProperties().containsKey("subnet")) {
                String vmSubnetName = (String) vm.getProperties().get("subnet");
                List<OclResourceV2> subnetList = searchOclResource(resources, "subnet", vmSubnetName);
                for (OclResourceV2 subnet : subnetList) {

                    if (StringUtils.isNotBlank(subnet.getId())) {
                        sb.append("\n  network {\n    uuid = data.huaweicloud_vpc_subnet.")
                                .append(subnet.getName())
                                .append(".id\n  }");
                    } else {
                        sb.append("\n  network {\n    uuid = huaweicloud_vpc_subnet.")
                                .append(subnet.getName())
                                .append(".id\n  }");
                    }
                }
            }
            // security
            sb.append("\n  admin_pass = \"Cloud#1234\"");
            Map<String, String> securityGroupMap = new HashMap<>();
            List<String> securityGroupList = new ArrayList<>();
            if (vm.getProperties().containsKey("security")) {
                String vmSecurityName = (String) vm.getProperties().get("security");
                List<OclResourceV2> securityList = searchOclResource(resources, "security", vmSecurityName);
                for (OclResourceV2 security : securityList) {
                    securityGroupMap.put(security.getId(), security.getName());
                }
            }
            for (Map.Entry<String, String> entry : securityGroupMap.entrySet()) {
                String securityGroup = StringUtils.isNotBlank(entry.getKey()) ? "data.huaweicloud_networking_secgroup." + entry.getValue() + ".id"
                        : "huaweicloud_networking_secgroup." + entry.getValue() + ".id";
                securityGroupList.add(securityGroup);
            }
            sb.append("\n  security_group_ids = [ ").append(String.join(",", securityGroupList)).append(" ]");
            sb.append("\n}\n\n");

            // publicly
            if (vm.getProperties().containsKey("publicly") && "true".equals(vm.getProperties().get("publicly"))) {
                sb.append(String.format(""
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
                        + "}\n\n"
                        + "resource \"huaweicloud_compute_eip_associate\" \"osc-eip-associated-%s\" {\n"
                        + "  public_ip   = huaweicloud_vpc_eip.osc-eip-%s.address\n"
                        + "  instance_id = huaweicloud_compute_instance.%s.id\n"
                        + "}\n\n", vm.getName(), vm.getName(), vm.getName(), vm.getName(), vm.getName()));
            }
        }
        return sb.toString();
    }

    private static String getImage(List<OclResourceV2> resources) {
        if (resources.size() == 0 || resources == null) {
            throw new IllegalArgumentException("Ocl for image is invalid.");
        }
        return "";
    }

    private static String getHclVariables(String... hclVars) {
        StringBuilder hcl = new StringBuilder();
        for (String hclVar : hclVars) {
            hcl.append(String.format("variable \"%s\" {"
                    + "\n  type = string\n"
                    + "\n}\n\n", hclVar));
        }
        return hcl.toString();
    }

    public static String getHclAvailabilityZone(List<OclResourceV2> resources) {
        if (resources.size() == 0 || resources == null) {
            throw new IllegalArgumentException("Ocl for AvailabilityZone is invalid.");
        }
        return "\ndata \"huaweicloud_availability_zones\" \"osc-az\" {}\n\n";
    }

    private static List<OclResourceV2> searchOclResource(List<OclResourceV2> oclResources, String kind) {
        if (StringUtils.isBlank(kind)) {
            return new ArrayList<>();
        }
        return oclResources.stream().filter(oclResource ->
                kind.equals(oclResource.getKind())).collect(Collectors.toList());
    }

    private static List<OclResourceV2> searchOclResource(List<OclResourceV2> oclResources, String kind, String name) {
        if (StringUtils.isAnyBlank(kind, name)) {
            return new ArrayList<>();
        }
        return oclResources.stream().filter(oclResource ->
                kind.equals(oclResource.getKind()) && name.equals(oclResource.getName())).collect(Collectors.toList());
    }

    private static List<PortPair> getPortPairs(String ports) {
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


}
