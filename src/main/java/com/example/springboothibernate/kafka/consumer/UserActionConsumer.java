package com.example.springboothibernate.kafka.consumer;

import com.example.springboothibernate.kafka.event.UserActionEvent;
import com.example.springboothibernate.kafka.producer.UserActionProducer;
import com.example.springboothibernate.model.AuditLog;
import com.example.springboothibernate.repository.AuditLogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class UserActionConsumer {
    private static final Logger logger = LoggerFactory.getLogger(UserActionProducer.class);
    private final AuditLogRepository auditLogRepository;

    public UserActionConsumer(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    @KafkaListener(topics = "user-actions", groupId = "audit-group")
    private void listen(UserActionEvent event) {
        logger.info("Received from Kafka: " + event);

        AuditLog log = new AuditLog();
        log.setEmail(event.getEmail());
        log.setAction(event.getAction());
        log.setTimestamp(event.getTimestamp());
        auditLogRepository.save(log);
    }
}
