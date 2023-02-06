package org.eclipse.osc.modules.ocl.loader.oclV2;

import java.util.HashSet;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Slf4j
public class ResourceHclEnum {

    public enum HuaweiCloudResourceHclEnum {
        VPC("vpc", "huaweicloud_vpc"),
        SUBNET("subnet", "huaweicloud_vpc_subnet"),
        SECURITY("security", "huaweicloud_networking_secgroup"),
        IMAGE("image", "huaweicloud_images_image"),
        STORAGE("storage", "huaweicloud_evs_volume"),
        EIP("eip", "huaweicloud_vpc_eip"),
        VM("vm", "huaweicloud_compute_instance"),
        BILLING("billing", "Billing");

        String kind;
        String hclKind;

        HuaweiCloudResourceHclEnum(String kind, String hclKind) {
            this.kind = kind;
            this.hclKind = hclKind;
        }

        public static String getHclTypeByKind(String kind) {
            String hclKind = StringUtils.EMPTY;
            try {
                hclKind =
                    HuaweiCloudResourceHclEnum.valueOf(kind).hclKind;
            } catch (IllegalArgumentException e) {
                log.error("this kind:{} not declare hclKind!", kind);
            }
            return hclKind;
        }

        public static Set<String> getKindSet() {
            Set<String> kindSet = new HashSet<>();
            for (HuaweiCloudResourceHclEnum enumEnum : HuaweiCloudResourceHclEnum.values()) {
                kindSet.add(enumEnum.kind);
            }
            return kindSet;
        }
    }


    public enum OpenstackResourceHclEnum {
        VPC("vpc", "openstack_vpc"),
        SUBNET("vpcSubnet", "openstack_vpc_subnet"),
        SECURITY("security", "openstack_networking_secgroup"),
        IMAGE("image", "openstack_images_image"),
        STORAGE("storage", "openstack_evs_volume"),
        EIP("eip", "openstack_vpc_eip"),
        VM("compute", "openstack_compute_instance"),
        BILLING("billing", "Billing");

        String kind;
        String hclKind;

        OpenstackResourceHclEnum(String kind, String hclKind) {
            this.kind = kind;
            this.hclKind = hclKind;
        }

        public static String getHclTypeByKind(String kind) {
            String hclKind = StringUtils.EMPTY;
            try {
                hclKind =
                    OpenstackResourceHclEnum.valueOf(kind).hclKind;
            } catch (IllegalArgumentException e) {
                log.error("this kind:{} not declare hclKind!", kind);
            }
            return hclKind;
        }

        public static Set<String> getKindSet() {
            Set<String> kindSet = new HashSet<>();
            for (OpenstackResourceHclEnum enumEnum : OpenstackResourceHclEnum.values()) {
                kindSet.add(enumEnum.kind);
            }
            return kindSet;
        }
    }


}
