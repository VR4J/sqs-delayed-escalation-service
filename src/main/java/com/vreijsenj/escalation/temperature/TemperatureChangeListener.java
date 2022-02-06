package com.vreijsenj.escalation.temperature;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vreijsenj.escalation.car.EngineState;
import com.vreijsenj.escalation.config.EscalationConfigurationProperties;
import com.vreijsenj.escalation.sqs.config.SQSConnectionProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
@Slf4j
@Component
@RequiredArgsConstructor
public class TemperatureChangeListener {

    private final SQSConnectionProperties sqsProperties;
    private final EscalationConfigurationProperties escalation;
    private final TemperatureCheckScheduler scheduler;
    private final EngineState engine;

    public void onTemperatureChange(TemperatureChangeMessage message) throws JsonProcessingException {
        log.info("Temperature Change: {}", message.getTemperature());

        Double temperature = message.getTemperature();

        engine.setTemperature(temperature);

        if(temperature > escalation.getThreshold().getTemperature()) {
            scheduler.schedule(sqsProperties.getUrl(), escalation.getDelay(), engine.getSeverity());
        }
    }
}
