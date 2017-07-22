package com.example.sink;

import org.apache.avro.generic.GenericRecord;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@SuppressWarnings("WeakerAccess")
@Component
public class PersonMessageHandler {

    MessageChannel errorChannel;

    public PersonMessageHandler(MessageChannel errorChannel) {
        this.errorChannel = errorChannel;
    }

    public void handleMessage(Message<GenericRecord> genericRecordMessage) {
        Message<GenericRecord> errorMessage = MessageBuilder
                .fromMessage(genericRecordMessage)
                .setHeader("exception", "some exception")
                .build();
        errorChannel.send(errorMessage);
    }

}
