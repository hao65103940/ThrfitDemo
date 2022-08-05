FROM fabric8/java-alpine-openjdk11-jre:latest

MAINTAINER FIT2CLOUD <support@fit2cloud.com>

RUN mkdir -p /opt/apps

ADD out/artifacts/ThriftDemo_jar /opt/apps

WORKDIR /opt/apps

EXPOSE 9099

CMD ["java","-jar","ThriftDemo.jar"]

