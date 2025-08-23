package com.example.springboothibernate.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailUtil {
    private static final Logger logger = LoggerFactory.getLogger(EmailUtil.class);
    private final JavaMailSender emailSender;

    public EmailUtil(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("albertnadoyan98@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        logger.info("Sending email to: {} with subject: {}", to, subject);
        emailSender.send(message);
        logger.info("Email successfully sent to {}", to);
    }
}
