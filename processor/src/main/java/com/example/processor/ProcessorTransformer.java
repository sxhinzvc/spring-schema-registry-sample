package com.example.processor;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.generic.GenericRecordBuilder;
import org.springframework.core.io.ClassPathResource;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.io.IOException;

@SuppressWarnings("WeakerAccess")
@Component
public class ProcessorTransformer {

    private final ObjectMapper objectMapper;

    public ProcessorTransformer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public Message<GenericRecord> transform(Message<String> message) throws IOException {
        JsonNode personJsonNode = objectMapper.readTree(message.getPayload());

        Schema schema = new Schema.Parser().parse(new ClassPathResource("person.avsc").getInputStream());
        GenericRecordBuilder genericRecordBuilder = new GenericRecordBuilder(schema);

        GenericRecord record = genericRecordBuilder
                .set("firstName", personJsonNode.get("firstName").asText())
                .set("lastName", personJsonNode.get("lastName").asText())
                .set("age", personJsonNode.get("age").asInt())
                .build();

        return MessageBuilder
                .withPayload(record)
                .build();
    }
}
