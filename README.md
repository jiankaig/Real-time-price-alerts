# Real Time Price Alerts

![sa diagram](/images/SA-Diagram_v2.jpeg)

## Introduction
this is a streaming data engineering mini project that uses kafka streams api. The sqlite table acts as the source of truth for a hypothetical dashboard to show live stock data. Any Inserts into the table will trigger the JDBC Source connector to poll table values into a source kafka topic and the subsequent processes will run and update the table with new price data.

## Prerequisites

- sqlite3
- [Java JDK 19](https://www.oracle.com/sg/java/technologies/downloads/)
- [Scala](https://www.scala-lang.org/download/)
- [VSCode Metals Extension](https://scalameta.org/metals/docs/editors/vscode#installation)
- Docker
- [jq](https://stedolan.github.io/jq/download/)

## To reproduce application

1. prepare sqlite table.
2. run bash script `run.sh` in `/scripts` dir.
3. run scala application via Metals extension. or run application via [java](#build-scala-and-run-as-java)
4. Monitor result, open grafana dashboard at http://localhost:3000
5. add more stock symbols to watch list via sqlite3, run `sqlite3 data/data/watch_list.db '.read data/scripts/insert.sql'` or see `/data/scripts/insert.sql` for an example.


## Build with scala and run as java

with [`sbt-assembly`](https://github.com/sbt/sbt-assembly), [build the scala application into a single jar file](https://www.baeldung.com/scala/sbt-fat-jar).

run this jar file by following example cli command:
`java -jar target/scala-2.13/app-assembly-XXX.jar`
