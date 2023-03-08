# Notes:

recording key steps taken thus far:

1. following (this turtorial)[https://blog.rockthejvm.com/kafka-streams/]. we intialised with a hello-world template, change the build.sbt file and include docker compose for zoo-keeper and kafka images.



#### To run/stop zookeeper and kafka containers:

`docker-compose -f src/main/docker/docker-compose.yml up -d`

`docker-compose -f src/main/docker/docker-compose.yml down`


#### learning from wordcount example
[scala example](https://github.com/confluentinc/kafka-streams-examples/blob/7.1.1-post/src/main/scala/io/confluent/examples/streams/WordCountScalaExample.scala)

1. run docker containers
2. then docker exec
3. create topics
    kafka-topics --bootstrap-server localhost:9092 --topic streams-plaintext-input --create --partitions 1 --replication-factor 1
    kafka-topics --bootstrap-server localhost:9092 --topic streams-wordcount-output --create --partitions 1 --replication-factor 1
4. run stream app
5. run producer and consumer
   kafka-console-producer --broker-list localhost:9092 --topic streams-plaintext-input
   kafka-console-consumer --topic streams-wordcount-output --from-beginning \
        --bootstrap-server localhost:9092 \
        --from-beginning \
        --formatter kafka.tools.DefaultMessageFormatter \
        --property print.key=true \
        --property print.value=true \
        --property key.deserializer=org.apache.kafka.common.serialization.StringDeserializer \
        --property value.deserializer=org.apache.kafka.common.serialization.LongDeserializer


### setting up kafka connect
kafka connect requires topics to exist. currently, what you do after `docker-compose` up, is to recreate topic via 
` docker exec -it broker bash /usr/bin/kafka-topics --bootstrap-server localhost:9092 --topic my-source-topic \
 --create --partitions 1 --replication-factor 1 --config "cleanup.policy=compact" ` 
 then restart the kafka-connect container.

to verify the plugin, run:
` curl http://localhost:8083/connector-plugins `

then run:
` curl -d @"scripts/jdbc-source.json" \
-H "Content-Type: application/json" \
-X POST http://localhost:8083/connectors `


then check the topic:
` docker exec -it broker bash /usr/bin/kafka-console-consumer --bootstrap-server localhost:9092 --topic my-source-topic --from-beginning `

## Reproduce application
1. prepare sqlite table
2. run bash script `run.sh` (docker is launched)
3. run scala application: `StockDataApiStreaming`
4. check results