package com.example.musicbackend.model.respone;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ErrorResponse {
    private boolean success;
    private String error;
    private String message;
    private LocalDateTime timestamp;
}
