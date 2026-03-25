package com.example.sign.dto;

import java.time.LocalDateTime;

public class SignatureSubmitResponse {

    private Long id;
    private LocalDateTime submittedAt;
    private String message;

    public SignatureSubmitResponse(Long id, LocalDateTime submittedAt, String message) {
        this.id = id;
        this.submittedAt = submittedAt;
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }

    public String getMessage() {
        return message;
    }
}
