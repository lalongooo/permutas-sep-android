package com.permutassep.data.repository.datasource.authentication;

import com.permutassep.data.entity.ConfirmPasswordResetEntity;
import com.permutassep.data.entity.EmailEntity;

import rx.Observable;

/**
 * By Jorge E. Hernandez (@lalongooo) 2015
 */
public interface PasswordResetDataStore {

    Observable<String> resetPassword(EmailEntity emailEntity);

    Observable<String> confirmPasswordReset(ConfirmPasswordResetEntity confirmPasswordResetEntity);
}