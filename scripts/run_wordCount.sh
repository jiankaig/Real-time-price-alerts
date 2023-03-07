docker exec -it broker bash /usr/bin/kafka-topics --bootstrap-server localhost:9092 --topic streams-plaintext-input \
 --create  --partitions 1 --replication-factor 1
docker exec -it broker bash /usr/bin/kafka-topics --bootstrap-server localhost:9092 --topic streams-wordcount-output \
 --create  --partitions 1 --replication-factor 1
 
echo "word count output sink.."
docker exec -it broker bash /usr/bin/kafka-console-consumer --topic streams-wordcount-output --from-beginning \
 --bootstrap-server localhost:9092 \
 --property print.key=true \
 --property value.deserializer=org.apache.kafka.common.serialization.LongDeserializer