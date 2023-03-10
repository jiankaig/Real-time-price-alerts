# Make sure docker is running, use `open -a docker` or launch manually.

echo "docker-compose -f src/main/docker/docker-compose.yml down"
docker-compose -f src/main/docker/docker-compose.yml down

echo "docker-compose -f src/main/docker/docker-compose.yml up -d"
docker-compose -f src/main/docker/docker-compose.yml up -d

echo "docker exec -it broker bash /usr/bin/kafka-topics --bootstrap-server localhost:9092 --list"
docker exec -it broker bash /usr/bin/kafka-topics --bootstrap-server localhost:9092 --list

echo "creating topics..."
# docker exec -it broker bash /usr/bin/kafka-topics --bootstrap-server localhost:9092 --topic testTopic \
#  --create --partitions 1 --replication-factor 1
docker exec -it broker bash /usr/bin/kafka-topics --bootstrap-server localhost:9092 --topic source-topic \
 --create --partitions 1 --replication-factor 1
docker exec -it broker bash /usr/bin/kafka-topics --bootstrap-server localhost:9092 --topic api-sink-topic \
 --create --partitions 1 --replication-factor 1
docker exec -it broker bash /usr/bin/kafka-topics --bootstrap-server localhost:9092 --topic price-update-topic \
 --create --partitions 1 --replication-factor 1
docker exec -it broker bash /usr/bin/kafka-topics --bootstrap-server localhost:9092 --topic price-update-topic-schema \
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

echo "create jdbc source/sink connections"
curl -s -d @"scripts/jdbc-source-polling.json" \
    -H "Content-Type: application/json" \
    -X POST http://localhost:8083/connectors | jq .

curl -s -d @"scripts/jdbc-sink-updating.json" \
    -H "Content-Type: application/json" \
    -X POST http://localhost:8083/connectors | jq .

### Dirty way to parse json with schema and payload signatures
### bridges between two topics
### https://rmoff.net/2020/01/22/kafka-connect-and-schemas/
# echo "Running slient kcat"
echo "Running slient kcat"
docker exec -it `docker ps -q --filter name=kafkacat` \
  /bin/sh -c "kcat -b kafka:29092 -q -u -X auto.offset.reset=earliest -t price-update-topic" | \
jq --compact-output --unbuffered \
    '. |
    {   schema: { type: "struct", optional: false, fields: [
                { type: "string", optional: true, field:"SYM"},
                { type :"float", optional: true, field:"Price"},
                { type :"int64", optional: true, field:"LastUpdateTimeStamp_UNIX"}]},
        payload: {
            SYM: .SYM,
            Price: .Price,
            LastUpdateTimeStamp_UNIX: .LastUpdateTimeStamp_UNIX
            }
    }' | \
docker exec -i `docker ps -q --filter name=kafkacat` \
  /bin/sh -c "kcat -b kafka:29092 -t price-update-topic-schema -P -T -u" | \
jq --unbuffered '.'

# Ctrl+C to exit.
