package com.permutassep.domain.exception;

/**
 * Interface to represent a wrapper around an {@link ErrorBundle} to manage errors.
 */
public interface ErrorBundle {
    Exception getException();

    String getErrorMessage();
}
