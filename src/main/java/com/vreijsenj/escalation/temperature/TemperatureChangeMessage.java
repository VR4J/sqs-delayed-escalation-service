package com.vreijsenj.escalation.temperature;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class TemperatureChangeMessage extends TemperatureMessage {

    Double temperature;
}
