# syntax=docker/dockerfile:1
FROM gradle:8.1.1-jdk17-alpine AS build
WORKDIR /app
COPY . /app
RUN gradle build --no-daemon

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]