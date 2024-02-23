package com.gofar.graphql.exception;

import org.springframework.graphql.execution.ErrorType;

public class BookException extends RuntimeException {
    private final ErrorType errorType;

    public BookException() {
        super();
        this.errorType = ErrorType.INTERNAL_ERROR;
    }

    public BookException(String message, ErrorType errorType) {
        super(message);
        this.errorType = errorType;
    }

    public ErrorType getErrorType() {
        return errorType;
    }
}
