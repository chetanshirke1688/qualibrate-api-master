FROM java:8u72-jre

EXPOSE 8080
ENV SPRING_PROFILES_ACTIVE=test

COPY run.sh /run.sh
RUN ["chmod", "+x", "/run.sh"]
COPY build/libs/qualibrate-api-challange*.jar /opt/app/server.jar

ENTRYPOINT ["/run.sh"]
