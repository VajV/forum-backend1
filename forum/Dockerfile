FROM gradle:8.7.0-jdk17 AS build
WORKDIR /app
COPY . .
RUN gradle clean build -x test --no-daemon

FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar

ENV DB_HOST=host.docker.internal
ENV DB_PORT=5432
ENV DB_NAME=forumdb
ENV DB_USER=postgres
ENV DB_PASS=rootroot

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]