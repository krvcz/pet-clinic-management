
FROM maven:3.9.1-eclipse-temurin-20-alpine AS build
COPY . .
RUN mvn clean package -Pprod -DskipTests



FROM openjdk:19-jdk-alpine
COPY --from=build target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
