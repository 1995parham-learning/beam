FROM gradle:jdk11 as build

WORKDIR /app
COPY ./kafka-consumer-spark .

RUN gradle shadowJar

FROM apache/spark

COPY --from=build /app/build/libs/app-all.jar /opt/beam/beam-all.jar
