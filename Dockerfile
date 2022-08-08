FROM gradle:jdk11 as build

WORKDIR /app
COPY . .

RUN gradle shadowJar

FROM apache/spark

COPY --from=build /app/build/libs/app-all.jar /opt/beam/beam-all.jar
