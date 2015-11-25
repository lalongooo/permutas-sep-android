package com.permutassep.domain.repository;

import com.permutassep.domain.ConfirmPasswordResetDataWrapper;
import com.permutassep.domain.Email;

import rx.Observable;

/**
 * By Jorge E. Hernandez (@lalongooo) 2015
 */
public interface PasswordResetRepository {

    Observable<String> resetPassword(Email email);

    Observable<String> confirmPasswordReset(ConfirmPasswordResetDataWrapper confirmPasswordResetDataWrapper);
}
