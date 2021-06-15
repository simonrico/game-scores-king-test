package com.king.juan.simon.scores.model;

import java.time.Duration;

public class Session {

    private static final long EXPIRATION_TIME = Duration.ofMinutes(10).toMillis();

    private String sessionId;
    private Integer userId;
    private long timestamp;

    public Session(String sessionId, Integer userId, long timestamp) {
        this.sessionId = sessionId;
        this.userId = userId;
        this.timestamp = timestamp;
    }

    public String getSessionId() {
        return sessionId;
    }

    public Integer getUserId() {
        return userId;
    }

    public boolean isValid() { return System.currentTimeMillis() - this.timestamp < EXPIRATION_TIME; }
}
