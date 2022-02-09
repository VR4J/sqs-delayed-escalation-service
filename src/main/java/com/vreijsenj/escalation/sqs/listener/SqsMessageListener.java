package com.vreijsenj.escalation.sqs.listener;

import com.amazon.sqs.javamessaging.message.SQSTextMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vreijsenj.escalation.temperature.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

@Slf4j
@Component
@RequiredArgsConstructor
public class SqsMessageListener implements MessageListener {

    public static final String CHECK_MESSAGE = "CHECK";
    public static final String CHANGE_MESSAGE = "CHANGE";

    private final ObjectMapper mapper;
    private final TemperatureCheckListener temperatureCheckListener;
    private final TemperatureChangeListener temperatureChangeListener;

    @Override
    public void onMessage(Message message) {
        if (message instanceof SQSTextMessage sqsTextMessage) {
            try {
                onMessage(sqsTextMessage);
            } catch (JMSException | JsonProcessingException e) {
                // A better description is probably needed before we start running this in production...
                log.error("Something went wrong retrieving the payload of the SQSTextMessage.", e);
            }
        }
    }

    private void onMessage(SQSTextMessage sqsTextMessage) throws JMSException, JsonProcessingException {
        TemperatureMessage message = mapper.readValue(sqsTextMessage.getText(), TemperatureMessage.class);

        switch (message) {
            case TemperatureCheckMessage check -> {
                temperatureCheckListener.onTemperatureCheck(check);
            }
            case TemperatureChangeMessage change -> {
                temperatureChangeListener.onTemperatureChange(change);
            }
            default -> { }
        }
    }
}