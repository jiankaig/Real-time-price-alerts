# Make sure docker is running, use `open -a docker` or launch manually.

echo "docker-compose -f src/main/docker/docker-compose.yml down"
docker-compose -f src/main/docker/docker-compose.yml down

echo "docker-compose -f src/main/docker/docker-compose.yml up -d"
docker-compose -f src/main/docker/docker-compose.yml up -d

echo "docker exec -it broker bash /usr/bin/kafka-topics --bootstrap-server localhost:9092 --list"
docker exec -it broker bash /usr/bin/kafka-topics --bootstrap-server localhost:9092 --list

echo "creating topics..."
docker exec -it broker bash /usr/bin/kafka-topics --bootstrap-server localhost:9092 --topic source-topic \
 --create --partitions 1 --replication-factor 1
docker exec -it broker bash /usr/bin/kafka-topics --bootstrap-server localhost:9092 --topic api-sink-topic \
 --create --partitions 1 --replication-factor 1
docker exec -it broker bash /usr/bin/kafka-topics --bootstrap-server localhost:9092 --topic connect-config-storage-topic \
 --create --partitions 1 --replication-factor 1 --config "cleanup.policy=compact"
docker exec -it broker bash /usr/bin/kafka-topics --bootstrap-server localhost:9092 --topic connect-offset-storage-topic \
 --create --partitions 1 --replication-factor 1 --config "cleanup.policy=compact"
docker exec -it broker bash /usr/bin/kafka-topics --bootstrap-server localhost:9092 --topic connect-status-storage-topic \
 --create --partitions 1 --replication-factor 1 --config "cleanup.policy=compact"

echo "docker exec -it broker bash /usr/bin/kafka-topics --bootstrap-server localhost:9092 --list"
docker exec -it broker bash /usr/bin/kafka-topics --bootstrap-server localhost:9092 --list

echo "until curl -s -f localhost:8083/connector-plugins"
until curl -s -f localhost:8083/connector-plugins
do
  echo "Waiting for kafka-connect.."
  sleep 5
done 
echo "create jdbc source connector"
curl -s -d @"scripts/jdbc-source-polling.json" \
    -H "Content-Type: application/json" \
    -X POST http://localhost:8083/connectors | jq .

echo "docker exec -it broker bash /usr/bin/kafka-console-consumer --bootstrap-server localhost:9092 --topic source-topic --from-beginning"
docker exec -it broker bash /usr/bin/kafka-console-consumer --bootstrap-server localhost:9092 \
 --topic source-topic --property print.key=true --from-beginning

# Ctrl+C to exit.
