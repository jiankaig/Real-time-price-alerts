# Real Time Price Alerts

![sa diagram](/images/SA-Diagram_v2.jpeg)

## Introduction
this is a streaming data engineering mini project that uses kafka streams api. The sqlite table acts as the source of truth for a hypothetical dashboard to show live stock data. Any Inserts into the table will trigger the JDBC Source connector to poll table values into a source kafka topic and the subsequent processes will run and update the table with new price data.

## Prerequisites

- sqlite3
- VSCode Metals Extension
- Docker
- [kcat](https://github.com/edenhill/kcat#install)

## To reproduce application

1. prepare sqlite table.
2. run bash script `run.sh` in `/scripts` dir.
3. run scala application via Metals extension.
4. Monitor result, open grafana dashboard at http://localhost:3000
