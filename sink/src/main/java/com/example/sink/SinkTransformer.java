package com.example.sink;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.generic.GenericRecordBuilder;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.io.IOException;

@SuppressWarnings("WeakerAccess")
@Component
public class SinkTransformer {

    private Schema schema;
    private ObjectMapper objectMapper;

    public SinkTransformer(Schema schema, ObjectMapper objectMapper) {
        this.schema = schema;
        this.objectMapper = objectMapper;
    }

    public Message<GenericRecord> transform(Message<String> message) throws IOException {
        JsonNode personJsonNode = objectMapper.readTree(message.getPayload());

        GenericRecordBuilder builder = new GenericRecordBuilder(schema);

        GenericRecord record = builder
                .set("firstName", personJsonNode.get("firstName").asText())
                .set("lastName", personJsonNode.get("lastName").asText())
                .set("age", personJsonNode.get("age").asInt())
                .build();

        return MessageBuilder
                .withPayload(record)
                .build();
    }
}
