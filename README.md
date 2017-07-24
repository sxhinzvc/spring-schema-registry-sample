# Requirements

- Kafka: `brew install kafka`

# Apps

## Schema Registry

## Processor
This app reads messages from an input channel (`json-person` topic), transforms the messages to GenericRecord using Person schema and sends them to an output channel (`avro-person` topic).

## Sink
This app reads messages from an input channel (`json-person` topic), transforms the messages to GenericRecord using Person schema and sends them to an error channel (`error-person` topic).

# Reproducing issue

1. Start Schema Registry and register a new schema for Person:
   ```bash
   curl http://localhost:8990/ -H "Content-Type: application/json" -d @sample/schema-registry/src/main/resources/personschema.json
   ```

1. Start Processor and Sink apps.

1. Listen on the `error-person` and `avro-person` topics.
   ```bash
   kafka-console-consumer --zookeeper localhost:2181 --topic error-person
   ```
   
   ```bash
   kafka-console-consumer --zookeeper localhost:2181 --topic avro-person
   ```

1. Produce a json message onto `json-person` topic.
   ```bash
   echo "{\"firstName\":\"julia\",\"lastName\":\"gulia\",\"age\":\"55\"}" | kafka-console-producer --broker-list localhost:9092 --topic json-person
   ```
   
1. Notice that on the `avro-person` topic, the content type is `application/octet-stream` with the original content type `application/vnd.avroperson.v1+avro` while on the `error-person` topic, the content type is `application/x-java-object;type=org.apache.avro.generic.GenericData$Record`.