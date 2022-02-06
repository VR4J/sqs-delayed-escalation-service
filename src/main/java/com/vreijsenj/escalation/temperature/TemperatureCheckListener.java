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
            EngineState.Severity onChangeSeverity = message.getSeverity();

            if(onChangeSeverity == EngineState.Severity.CLEAR) {
                if(engine.getSeverity() == EngineState.Severity.WARNING) return;

                log.info("Sending notification: Engine temperature has been above configured threshold of '{}' degrees celsius.", escalation.getThreshold().getTemperature());
                log.info("Engine severity raised to {}", EngineState.Severity.WARNING);
                engine.setSeverity(EngineState.Severity.WARNING);
            } else if(onChangeSeverity == EngineState.Severity.WARNING) {
                if(engine.getSeverity() == EngineState.Severity.CRITICAL) return;

                log.info("Sending notification: Engine temperature has been above configured threshold of '{}' degrees celsius.", escalation.getThreshold().getTemperature());
                log.info("Engine severity raised to {}", EngineState.Severity.CRITICAL);
                engine.setSeverity(EngineState.Severity.CRITICAL);
            }
        }
    }
}
