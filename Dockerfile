FROM gradle:jdk11 as build

WORKDIR /app
COPY . .

RUN gradle shadowJar

FROM apache/spark

COPY --from=build /app/kafka-consumer-spark/build/libs/kafka-consumer-spark-all.jar /opt/beam/spark-all.jar
COPY --from=build /app/kafka-consumer-direct/build/libs/kafka-consumer-direct-all.jar /opt/beam/direct-all.jar
