package com.vreijsenj.escalation.sqs.config.localstack;

import com.amazon.sqs.javamessaging.ProviderConfiguration;
import com.amazon.sqs.javamessaging.SQSConnectionFactory;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;

import java.net.URI;

@Slf4j
@Configuration
@Profile("localstack")
public class ClientConfig {

    private static final Region LOCALSTACK_REGION = Region.US_EAST_1;
    private static final URI LOCALSTACK_URI = URI.create("http://localhost:4566");

    @Bean
    public SqsClient sqsClient() {
        return SqsClient.builder()
                .endpointOverride(LOCALSTACK_URI)
                .region(LOCALSTACK_REGION)
                .build();
    }

    @Bean
    public AmazonSQS sqs() {
        return AmazonSQSClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(LOCALSTACK_URI.toString(), LOCALSTACK_REGION.id()))
                .build();
    }

    @Bean
    public SQSConnectionFactory sqsConnectionFactory(AmazonSQS sqs) {
        ProviderConfiguration configuration = new ProviderConfiguration()
                .withNumberOfMessagesToPrefetch(10);

        return new SQSConnectionFactory(configuration, sqs);
    }
}
