package com.tricol.Tricol.exception;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ApiErreur {
    private String message;
    private int code;
    private LocalDateTime timestamp;
}
