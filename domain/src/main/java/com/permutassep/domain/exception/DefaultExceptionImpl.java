package com.permutassep.domain.exception;

/**
 *  Wrapper around Exceptions used to manage default errors.
 */

public class DefaultExceptionImpl implements Exception {

    private static final String DEFAULT_ERROR_MSG = "Unknown error";

    private final Exception exception;

    public DefaultExceptionImpl(Exception exception) {
        this.exception = exception;
    }

    @Override
    public Exception getException() {
        return exception;
    }

    @Override
    public String getErrorMessage() {
        return (exception != null) ? this.exception.getErrorMessage() : DEFAULT_ERROR_MSG;
    }
}
