#!/bin/bash
#os_arch(linux_amd64/linux_arm/linux_386/darwin_amd64/darwin_arm64/)
echo `java -version`
os_arch=linux_amd64
bin_path=/usr/bin
# install terraform
tf_version=1.3.7
tf_file=terraform_${tf_version}_${os_arch}.zip
ft_file_url=https://releases.hashicorp.com/terraform/$tf_version/$tf_file
echo "Terraform $tf_file start downloading and installing......"
wget $ft_file_url  && unzip -o -d $bin_path $tf_file && rm -rf $tf_file
echo `terraform -v`
echo "Terraform $tf_file download and install succeeded!"

#install terraform huaweicloud provider
echo "Terraform HuaweiCloud Provider plugin cache start installing......"

cat>$HOME/.terraformrc<<EOF
plugin_cache_dir   = "$HOME/.terraform.d/plugin-cache"
disable_checkpoint = true
EOF

mkdir -p $HOME/.terraform.d/plugin-cache
cat>$HOME/.terraform.d/plugin-cache/version.tf<<EOF
terraform {
  required_version = ">= 0.13"
  required_providers {
    huaweicloud = {
      source = "huaweicloud/huaweicloud"
      version = ">= 1.20.0"
    }
  }
}
EOF

cd $HOME/.terraform.d/plugin-cache/ && terraform init
if [ -d "registry.terraform.io/" ]; then
    echo `terraform -v`
    cp -r registry.terraform.io local-registry
    echo  "Terraform HuaweiCloud Provider plugin cache install succeeded!"
fi
