package com.permutassep.presentation.model;

public class ConfirmPasswordResetDataWrapperModel {

    private String token;
    private String password;
    private String passwordConfirm;

    public ConfirmPasswordResetDataWrapperModel(String token, String password, String passwordConfirm) {
        this.token = token;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
    }

    public String getPassword() {
        return password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public String getToken() {
        return token;
    }
}
