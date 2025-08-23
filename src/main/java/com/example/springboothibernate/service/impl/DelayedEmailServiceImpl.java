package com.example.springboothibernate.service.impl;

import com.example.springboothibernate.service.DelayedEmailService;
import com.example.springboothibernate.util.EmailUtil;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;

@Service
public class DelayedEmailServiceImpl implements DelayedEmailService {
    private final TaskScheduler taskScheduler;
    private final EmailUtil emailUtil;

    public DelayedEmailServiceImpl(TaskScheduler taskScheduler, EmailUtil emailUtil) {
        this.taskScheduler = taskScheduler;
        this.emailUtil = emailUtil;
    }

    @Override
    public void sendEmailWithDelay(String to, String subject, String body, Duration delay) {
        taskScheduler.schedule(
                () -> emailUtil.sendEmail(to, subject, body),
                Instant.now().plus(delay)
        );
    }
}
