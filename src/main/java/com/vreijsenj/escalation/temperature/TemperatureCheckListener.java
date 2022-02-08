package com.vreijsenj.escalation.temperature;

import com.vreijsenj.escalation.car.EngineState;
import com.vreijsenj.escalation.config.EscalationConfigurationProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TemperatureCheckListener {

    private final EngineState engine;
    private final EscalationConfigurationProperties escalation;

    public void onTemperatureCheck(TemperatureCheckMessage message) {
        if(engine.getTemperature() > escalation.getThreshold().getTemperature()) {
            EngineState.Severity previousSeverity = message.getSeverity();
            EngineState.Severity nextSeverity = previousSeverity == EngineState.Severity.CLEAR
                ? EngineState.Severity.WARNING
                : EngineState.Severity.CRITICAL;

            if(engine.getSeverity() == nextSeverity) return;

            log.info("Sending notification: Engine temperature has been above configured threshold of '{}' degrees celsius.", escalation.getThreshold().getTemperature());
            log.info("Engine severity raised to {}", nextSeverity);
            engine.setSeverity(nextSeverity);
        }
    }
}
