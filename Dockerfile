FROM maven:3.8.6-eclipse-temurin-19-alpine AS build
COPY . .
RUN mvn clean package -Pproduction


FROM openjdk:17-jdk-slim
# Instalacja Node.js
RUN apt-get update && apt-get install -y curl \
    && curl -fsSLO --compressed https://nodejs.org/dist/v18.14.1/node-v18.14.1-linux-x64.tar.xz \
    && tar -xf node-v18.14.1-linux-x64.tar.xz --strip-components=1 -C /usr/local

COPY --from=build target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
