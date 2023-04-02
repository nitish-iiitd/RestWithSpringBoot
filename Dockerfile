FROM openjdk:8-jdk-alpine
MAINTAINER Nitish Srivastava
COPY target/rest-with-springboot-0.0.1-SNAPSHOT.jar rest-with-springboot-0.0.1.jar
ENTRYPOINT ["java","-jar","/rest-with-springboot-0.0.1.jar"]