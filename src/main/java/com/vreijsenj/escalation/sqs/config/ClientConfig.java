package com.vreijsenj.escalation.sqs.config;

import com.amazon.sqs.javamessaging.ProviderConfiguration;
import com.amazon.sqs.javamessaging.SQSConnectionFactory;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import software.amazon.awssdk.services.sqs.SqsClient;

@Slf4j
@Configuration
@Profile("!localstack")
public class ClientConfig {

    @Bean
    public SqsClient sqsClient() {
        return SqsClient.create();
    }

    @Bean
    public AmazonSQS sqs() {
        return AmazonSQSClientBuilder.defaultClient();
    }

    @Bean
    public SQSConnectionFactory sqsConnectionFactory(AmazonSQS sqs) {
        ProviderConfiguration configuration = new ProviderConfiguration()
                .withNumberOfMessagesToPrefetch(10);

        return new SQSConnectionFactory(configuration, sqs);
    }
}
