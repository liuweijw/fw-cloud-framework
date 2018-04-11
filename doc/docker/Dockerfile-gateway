FROM java:8
MAINTAINER fw-cloud-framework <liuweijw.github@foxmail.com>

ADD ./cloud-gateway/fw-cloud-system-gateway.jar /app/

CMD ["java", "-Xmx300m", "-jar", "/app/fw-cloud-system-gateway.jar"]

EXPOSE 1003