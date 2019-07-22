FROM java:8u72-jre

EXPOSE 8080

COPY run.sh /run.sh
RUN ["chmod", "+x", "/run.sh"]
COPY build/libs/qualibrate-api-challange*.jar /opt/app/server.jar

ENTRYPOINT ["/run.sh"]
