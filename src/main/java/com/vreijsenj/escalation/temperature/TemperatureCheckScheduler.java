package com.vreijsenj.escalation.temperature;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vreijsenj.escalation.car.EngineState;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

import static com.vreijsenj.escalation.sqs.listener.SqsMessageListener.CHANGE_MESSAGE;

@Slf4j
@Component
@RequiredArgsConstructor
public class TemperatureCheckScheduler {

    private final SqsClient sqs;
    private final ObjectMapper mapper;

    public void schedule(String queue, Integer delay, EngineState.Severity severity) throws JsonProcessingException {
        String body = getMessagePayload(severity);

        SendMessageRequest request = SendMessageRequest.builder()
                .queueUrl(queue)
                .delaySeconds(delay)
                .messageBody(body)
                .build();

        sqs.sendMessage(request);
    }

    private String getMessagePayload(EngineState.Severity severity) throws JsonProcessingException {
        return mapper.writeValueAsString(
                new TemperatureCheckMessage(CHANGE_MESSAGE, severity)
        );
    }
}
