
FROM maven:3.8.6-eclipse-temurin-19-alpine AS build
COPY . .
RUN mvn clean package -Pproduction


FROM openjdk:17-jdk-slim
COPY --from=build target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
