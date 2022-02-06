package com.vreijsenj.escalation.sqs.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "sqs.connection")
public class SQSConnectionProperties {

    String name;
    String url;
}
