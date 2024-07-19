FROM openjdk:21-jdk-slim
LABEL authors="HP"

WORKDIR /app

ARG JAR_FILE=target/*.jar
VOLUME /tmp
COPY target/serverapp-0.0.1-SNAPSHOT.jar /app/serverapp.jar

ENTRYPOINT ["java", "-jar", "/app/serverapp.jar"]