FROM ubuntu:20.04

USER root
RUN apt-get update && DEBIAN_FRONTEND=noninteractive apt-get -y install vim net-tools openjdk-11-jdk
ENV LANG en_US.UTF-8
ENV HW_REGION_NAME=cn-southwest-2
ENV HW_CUSTOM_REGION_NAME=cn-southwest-2
ENV HW_DOMAIN_NAME=hwstaff_zhenguo
ENV HW_ACCESS_KEY=your_huaweicloud_ak
ENV HW_SECRET_KEY=your_huaweicloud_sk
ENV HW_ENTERPRISE_PROJECT_ID=bad95a97-9c31-4f29-ab4f-7bfe934ae7ab

COPY docker/install_online.sh  /
RUN chmod +x /install_online.sh
RUN /install_online.sh

RUN mkdir -p /usr/osc/runtime
COPY runtime/target/runtime/* /usr/osc/runtime/
WORKDIR /usr/osc/runtime
EXPOSE 8080
ENTRYPOINT ["java","-jar","minho-boot-1.0-SNAPSHOT.jar"]
