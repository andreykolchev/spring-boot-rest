package com.example.model.error;

import lombok.Data;

@Data
public class ErrorException extends RuntimeException {

    private String message;

    public ErrorException(String s) {
        super(s);
        this.message = s;
    }
}
