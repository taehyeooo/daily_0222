package com.example.daily0303.mission2.dto;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class ErrorResponseDto {

    private final int status;
    private final String message;
    private final LocalDateTime timestamp;
    private final List<String> errors;

    public ErrorResponseDto(int status, String message, List<String> errors) {
        this.status = status;
        this.message = message;
        this.timestamp = LocalDateTime.now();
        this.errors = errors;
    }

    public ErrorResponseDto(int status, String message) {
        this(status, message, null);
    }
}
