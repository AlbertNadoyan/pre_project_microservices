package com.example.springboothibernate.kafka.event;

import java.time.LocalDateTime;

public class UserActionEvent {
    private String email;
    private String action;
    private LocalDateTime timestamp;

    public UserActionEvent() {
    }

    public UserActionEvent(String email, String action, LocalDateTime timestamp) {
        this.email = email;
        this.action = action;
        this.timestamp = timestamp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
