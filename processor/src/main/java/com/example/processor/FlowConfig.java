package com.example.processor;

import org.apache.avro.Schema;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.cloud.stream.schema.client.EnableSchemaRegistryClient;
import org.springframework.cloud.stream.schema.client.SchemaRegistryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.messaging.MessageChannel;

@SuppressWarnings({"unused", "WeakerAccess"})
@EnableBinding(Processor.class)
@Configuration
public class FlowConfig {

    @Bean
    public IntegrationFlow processorFlow(MessageChannel input,
                                         MessageChannel output,
                                         ProcessorTransformer transformer) {
        return IntegrationFlows
                .from(input)
                .transform(transformer)
                .channel(output)
                .get();
    }

    @Bean
    public Schema schema(SchemaRegistryClient schemaRegistryClient, @Value("${schema.id}") int schemaId) {
        String schemaString = schemaRegistryClient.fetch(schemaId);
        return new Schema.Parser().parse(schemaString);
    }
}