package com.example.springboothibernate.kafka.producer;

import com.example.springboothibernate.kafka.event.UserActionEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserActionProducer {
    private static final Logger logger = LoggerFactory.getLogger(UserActionProducer.class);
    private final KafkaTemplate<String, UserActionEvent> kafkaTemplate;

    public UserActionProducer(KafkaTemplate<String, UserActionEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendUserAction(UserActionEvent event) {
        kafkaTemplate.send("user-actions", event);
        logger.info("Sent to Kafka: " + event);
    }
}
