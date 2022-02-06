package com.vreijsenj.escalation.temperature;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Data;

import static com.vreijsenj.escalation.sqs.listener.SqsMessageListener.CHANGE_MESSAGE;
import static com.vreijsenj.escalation.sqs.listener.SqsMessageListener.CHECK_MESSAGE;

@Data
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = TemperatureCheckMessage.class, name = CHECK_MESSAGE),
        @JsonSubTypes.Type(value = TemperatureChangeMessage.class, name = CHANGE_MESSAGE),
})
public class TemperatureMessage {

    String type;
}
