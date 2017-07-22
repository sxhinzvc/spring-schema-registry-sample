package com.example.errorhandler;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.handler.LoggingHandler;
import org.springframework.messaging.MessageChannel;

@EnableBinding(Sink.class)
@Configuration
public class FlowConfig {

    @Bean
    public IntegrationFlow errorHandlerSinkFlow(MessageChannel input) {
        return IntegrationFlows
            .from(input)
            .handle(new LoggingHandler("info"))
            .get();
    }
}
