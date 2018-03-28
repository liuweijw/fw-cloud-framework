FROM java:8
MAINTAINER fw-cloud-framework <liuweijw.github@foxmail.com>

ADD ./cloud-config/fw-cloud-system-config.jar /app/

CMD ["java", "-Xmx200m", "-jar", "/app/fw-cloud-system-config.jar"]

EXPOSE 1002