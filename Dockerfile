FROM ubuntu:20.04

USER root
RUN apt-get update && DEBIAN_FRONTEND=noninteractive apt-get -y install vim wget net-tools openjdk-17-jdk
ENV LANG en_US.UTF-8
ENV HW_REGION_NAME=your_region_name
ENV HW_CUSTOM_REGION_NAME=your_custom_region_name
ENV HW_DOMAIN_NAME=your_domain_name
ENV HW_ACCESS_KEY=your_huaweicloud_ak
ENV HW_SECRET_KEY=your_huaweicloud_sk
ENV HW_ENTERPRISE_PROJECT_ID=your_enterprise_project_id

COPY docker/install_online.sh  /
RUN chmod +x /install_online.sh
RUN /install_online.sh

RUN mkdir -p /usr/osc/runtime
COPY runtime/target/xpanse-runtime-1.0.0-SNAPSHOT.jar /usr/osc/runtime/
WORKDIR /usr/osc/runtime
EXPOSE 8080
ENTRYPOINT ["java","-jar","xpanse-runtime-1.0.0-SNAPSHOT.jar"]