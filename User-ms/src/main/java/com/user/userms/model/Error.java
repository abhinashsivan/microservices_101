package com.user.userms.model;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class Error {
    private final ZonedDateTime timestamp;
    private String endpoint;
    private String message;

    public Error(String endpoint, String message) {
        this.timestamp = Instant.now().atZone(ZoneId.of("Asia/Kolkata"));
        this.endpoint = endpoint;
        this.message = message;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public String getEndpoint(){
        return endpoint;
    }

    public void setTimestamp(String endpoint){
        this.endpoint = endpoint;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
