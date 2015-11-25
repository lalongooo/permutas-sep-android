package com.permutassep.data.repository;

import com.permutassep.data.entity.mapper.ConfirmPasswordResetEntityDataMapper;
import com.permutassep.data.entity.mapper.EmailEntityDataMapper;
import com.permutassep.data.repository.datasource.authentication.PasswordResetDataStoreFactory;
import com.permutassep.domain.ConfirmPasswordResetDataWrapper;
import com.permutassep.domain.Email;
import com.permutassep.domain.repository.PasswordResetRepository;

import javax.inject.Inject;

import rx.Observable;

/**
 * By Jorge E. Hernandez (@lalongooo) 2015
 */
public class PasswordResetDataRepository implements PasswordResetRepository {

    private PasswordResetDataStoreFactory passwordResetDataStoreFactory;
    private EmailEntityDataMapper emailEntityDataMapper;
    private ConfirmPasswordResetEntityDataMapper confirmPasswordResetEntityDataMapper;

    @Inject
    public PasswordResetDataRepository(ConfirmPasswordResetEntityDataMapper confirmPasswordResetEntityDataMapper, EmailEntityDataMapper emailEntityDataMapper, PasswordResetDataStoreFactory passwordResetDataStoreFactory) {
        this.confirmPasswordResetEntityDataMapper = confirmPasswordResetEntityDataMapper;
        this.emailEntityDataMapper = emailEntityDataMapper;
        this.passwordResetDataStoreFactory = passwordResetDataStoreFactory;
    }

    @Override
    public Observable<String> resetPassword(Email email) {
        return passwordResetDataStoreFactory.createCloudDataStore().resetPassword(emailEntityDataMapper.transform(email));
    }

    @Override
    public Observable<String> confirmPasswordReset(ConfirmPasswordResetDataWrapper confirmPasswordResetDataWrapper) {
        return passwordResetDataStoreFactory.createCloudDataStore().confirmPasswordReset(confirmPasswordResetEntityDataMapper.transform(confirmPasswordResetDataWrapper));
    }
}
