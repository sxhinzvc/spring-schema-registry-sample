package com.example.sink;

import org.apache.avro.Schema;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.cloud.stream.schema.avro.AvroSchemaMessageConverter;
import org.springframework.cloud.stream.schema.avro.AvroSchemaRegistryClientMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.converter.MessageConverter;

import java.io.IOException;

@SuppressWarnings({"unused", "WeakerAccess"})
@EnableBinding(Sink.class)
@EnableIntegration
@Configuration
public class FlowConfig {

    @Bean
    public IntegrationFlow sinkFlow(MessageChannel input,
                                    MessageChannel errorChannel,
                                    SinkTransformer transformer) {
        return IntegrationFlows
                .from(input)
                .transform(transformer)
                .channel(errorChannel)
                .get();
    }

    @Bean
    public Schema schema(@Value("classpath:person.avsc") Resource resource) throws IOException {
        return new Schema.Parser().parse(resource.getInputStream());
    }
}
