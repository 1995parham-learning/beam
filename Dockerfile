FROM gradle:jdk17 AS build

WORKDIR /app
COPY . .

RUN gradle shadowJar

# Pinned to match what the jars are compiled against: the shadow jars build
# against Spark 3.5.9 on Scala 2.12, and the floating `apache/spark` tag has
# moved on to Spark 4.x on Scala 2.13, which is binary-incompatible with them.
FROM apache/spark:3.5.9-scala2.12-java17-ubuntu

COPY --from=build /app/kafka-consumer-spark/build/libs/kafka-consumer-spark-all.jar /opt/beam/spark-all.jar
COPY --from=build /app/kafka-consumer-direct/build/libs/kafka-consumer-direct-all.jar /opt/beam/direct-all.jar
