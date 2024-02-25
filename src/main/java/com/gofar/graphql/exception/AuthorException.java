package com.gofar.graphql.exception;

import org.springframework.graphql.execution.ErrorType;

public class AuthorException extends RuntimeException{
    private final ErrorType errorType;

    public AuthorException() {
        super();
        this.errorType = ErrorType.INTERNAL_ERROR;
    }

    public AuthorException(String message, ErrorType errorType) {
        super(message);
        this.errorType = errorType;
    }

    public ErrorType getErrorType() {
        return errorType;
    }
}
