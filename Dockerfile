# Building phase, build with gradle
FROM gradle:jdk21-alpine AS build
WORKDIR /app
VOLUME /app
COPY build.gradle.kts .
COPY settings.gradle .
COPY src ./src
RUN gradle clean build -x test

# Running phase
FROM openjdk:21-jdk-slim
VOLUME /tmp
ARG JARFILE=build/libs/*.jar
COPY --from=build /app/${JARFILE} /app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]