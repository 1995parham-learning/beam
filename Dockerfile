FROM gradle:latest as build

WORKDIR /app
COPY . .

RUN gradle shadowJar

FROM apache/spark

COPY --from=build /app/build/libs/beam-all.jar /opt/beam/beam-all.jar
