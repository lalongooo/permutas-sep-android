package com.permutassep.domain;

public class ConfirmPasswordResetDataWrapper {

    private String token;
    private String password;
    private String passwordConfirm;

    public ConfirmPasswordResetDataWrapper(String token, String password, String passwordConfirm) {
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
