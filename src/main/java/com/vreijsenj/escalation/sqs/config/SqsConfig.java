package com.vreijsenj.escalation.sqs.config;

import com.amazon.sqs.javamessaging.SQSConnection;
import com.amazon.sqs.javamessaging.SQSConnectionFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;

@Slf4j
@Component
@RequiredArgsConstructor
public class SqsConfig {

    private final SQSConnectionFactory sqsConnectionFactory;
    private final SQSConnectionProperties properties;
    private final MessageListener sqsMessageListener;

    @PostConstruct
    public void registerQueueListener() throws JMSException {
        SQSConnection connection = sqsConnectionFactory.createConnection();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        MessageConsumer consumer = session.createConsumer(session.createQueue(properties.getName()));

        consumer.setMessageListener(sqsMessageListener);
        connection.start();
    }
}
