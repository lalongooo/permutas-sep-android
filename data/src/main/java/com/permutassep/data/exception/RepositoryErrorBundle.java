package com.permutassep.data.exception;

import com.permutassep.domain.exception.*;
import com.permutassep.domain.exception.Exception;

/**
 * Wrapper around Exceptions used to manage errors in the repository.
 */
public class RepositoryErrorBundle implements com.permutassep.domain.exception.Exception {

    private final Exception exception;

    public RepositoryErrorBundle(Exception exception) {
        this.exception = exception;
    }

    @Override
    public Exception getException() {
        return exception;
    }

    @Override
    public String getErrorMessage() {
        String message = "";
        if (this.exception != null) {
            this.exception.getErrorMessage();
        }
        return message;
    }
}
