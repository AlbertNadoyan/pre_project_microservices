package com.example.springboothibernate.service;

import java.time.Duration;

public interface DelayedEmailService {
    void sendEmailWithDelay(String to, String subject, String body, Duration delay);
}
