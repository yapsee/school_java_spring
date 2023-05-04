package com.ecole221.school.school_api.exception;

public class InvalidRequestException extends RuntimeException  {
    private final String message;

    public InvalidRequestException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
