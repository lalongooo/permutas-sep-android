package com.permutassep.presentation.mapper;

import com.permutassep.domain.ConfirmPasswordResetDataWrapper;
import com.permutassep.presentation.model.ConfirmPasswordResetDataWrapperModel;

import javax.inject.Inject;

/**
 * By Jorge E. Hernandez (@lalongooo) 2015
 */

/**
 * Mapper class used to transform a {@link ConfirmPasswordResetDataWrapper} (in the domain layer) to a {@link ConfirmPasswordResetDataWrapperModel} and vice versa.
 */
public class ConfirmPasswordResetDataMapper {

    @Inject
    public ConfirmPasswordResetDataMapper() {
    }

    public ConfirmPasswordResetDataWrapperModel transform(ConfirmPasswordResetDataWrapper confirmPasswordResetDataWrapper) {
        ConfirmPasswordResetDataWrapperModel confirmPasswordResetDataWrapperModel = null;
        if (confirmPasswordResetDataWrapper != null) {
            confirmPasswordResetDataWrapperModel = new ConfirmPasswordResetDataWrapperModel(confirmPasswordResetDataWrapper.getToken(), confirmPasswordResetDataWrapper.getPasswordConfirm(), confirmPasswordResetDataWrapper.getPasswordConfirm());
        }

        return confirmPasswordResetDataWrapperModel;
    }

    public ConfirmPasswordResetDataWrapper transform(ConfirmPasswordResetDataWrapperModel confirmPasswordResetDataWrapperModel) {
        ConfirmPasswordResetDataWrapper confirmPasswordResetDataWrapper = null;
        if (confirmPasswordResetDataWrapperModel != null) {
            confirmPasswordResetDataWrapper = new ConfirmPasswordResetDataWrapper(confirmPasswordResetDataWrapperModel.getToken(), confirmPasswordResetDataWrapperModel.getPassword(), confirmPasswordResetDataWrapperModel.getPasswordConfirm());
        }

        return confirmPasswordResetDataWrapper;
    }

}