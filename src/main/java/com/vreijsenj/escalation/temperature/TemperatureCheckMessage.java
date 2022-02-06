package com.vreijsenj.escalation.temperature;

import com.vreijsenj.escalation.car.EngineState;
import lombok.*;

@Getter
public class TemperatureCheckMessage extends TemperatureMessage {

    EngineState.Severity severity;

    public TemperatureCheckMessage(String type, EngineState.Severity severity) {
        this.type = type;
        this.severity = severity;
    }
}
