FROM java:8u72-jre
COPY build/libs/qualibrate-api-challange-1.0.0.jar /dockerapi/qualibrate-api-challange-1.0.0.jar
ENTRYPOINT ["java"]
CMD ["-jar", "/dockerapi/qualibrate-api-challange-1.0.0.jar"]
EXPOSE 8080
