package com.vreijsenj.escalation.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("escalation")
public class EscalationConfigurationProperties {

    Integer delay;
    Threshold threshold;

    @Data
    public static class Threshold {
        double temperature;
    }
}
