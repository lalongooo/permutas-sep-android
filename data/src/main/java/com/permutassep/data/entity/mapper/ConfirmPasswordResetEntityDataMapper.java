package com.permutassep.data.entity.mapper;

import com.permutassep.data.entity.ConfirmPasswordResetEntity;
import com.permutassep.domain.ConfirmPasswordResetDataWrapper;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * By Jorge E. Hernandez (@lalongooo) 2015
 */
@Singleton
public class ConfirmPasswordResetEntityDataMapper {

    @Inject
    public ConfirmPasswordResetEntityDataMapper() {
    }

    public ConfirmPasswordResetEntity transform(ConfirmPasswordResetDataWrapper confirmPasswordResetDataWrapper) {
        ConfirmPasswordResetEntity confirmPasswordResetEntity = null;
        if (confirmPasswordResetDataWrapper != null) {
            confirmPasswordResetEntity = new ConfirmPasswordResetEntity(confirmPasswordResetDataWrapper.getToken(), confirmPasswordResetDataWrapper.getPassword(), confirmPasswordResetDataWrapper.getPasswordConfirm());
        }

        return confirmPasswordResetEntity;
    }

    public ConfirmPasswordResetDataWrapper transform(ConfirmPasswordResetEntity confirmPasswordResetEntity) {
        ConfirmPasswordResetDataWrapper confirmPasswordResetDataWrapper = null;
        if (confirmPasswordResetEntity != null) {
            confirmPasswordResetDataWrapper = new ConfirmPasswordResetDataWrapper(confirmPasswordResetEntity.getToken(), confirmPasswordResetEntity.getPassword(), confirmPasswordResetEntity.getPasswordConfirm());
        }

        return confirmPasswordResetDataWrapper;
    }
}