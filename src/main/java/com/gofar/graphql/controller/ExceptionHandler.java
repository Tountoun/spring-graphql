package com.gofar.graphql.controller;

import com.gofar.graphql.exception.AuthorException;
import com.gofar.graphql.exception.BookException;
import graphql.GraphQLError;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.stereotype.Component;

@Component
public class ExceptionHandler extends DataFetcherExceptionResolverAdapter {
    @Override
    protected GraphQLError resolveToSingleError(Throwable ex, DataFetchingEnvironment env) {
        if (ex instanceof BookException) {
            return GraphQLError.newError()
                    .errorType(((BookException)ex).getErrorType())
                    .message(ex.getMessage())
                    .path(env.getExecutionStepInfo().getPath())
                    .location(env.getField().getSourceLocation())
                    .build();
        }
        if (ex instanceof AuthorException) {
            return GraphQLError.newError()
                    .errorType(((AuthorException)ex).getErrorType())
                    .message(ex.getMessage())
                    .path(env.getExecutionStepInfo().getPath())
                    .location(env.getField().getSourceLocation())
                    .build();
        }
        return super.resolveToSingleError(ex, env);
    }
}
