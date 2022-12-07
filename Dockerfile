FROM gradle:7-jdk11 AS builder

WORKDIR /app
COPY . .

RUN ["gradle", "-x", "test" , "build"]

FROM openjdk:11-jre-slim AS runner

WORKDIR /app

ARG JAR_FILE=/app/build/libs/sammaru-0.0.1-SNAPSHOT.jar
ENV TZ=Asia/Seoul
COPY --from=builder ${JAR_FILE} /app/app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","-Dspring.profiles.active=dev","app.jar"]