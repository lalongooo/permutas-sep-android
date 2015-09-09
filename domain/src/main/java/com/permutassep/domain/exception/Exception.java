package com.permutassep.domain.exception;

/**
 * Interface to represent a wrapper around an {@link Exception} to manage errors.
 */
public interface Exception {
  Exception getException();

  String getErrorMessage();
}
