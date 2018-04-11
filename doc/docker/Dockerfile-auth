FROM java:8
MAINTAINER fw-cloud-framework <liuweijw.github@foxmail.com>

ADD ./cloud-auth/fw-cloud-system-auth.jar /app/

CMD ["java", "-Xmx300m", "-jar", "/app/fw-cloud-system-auth.jar"]

EXPOSE 1004