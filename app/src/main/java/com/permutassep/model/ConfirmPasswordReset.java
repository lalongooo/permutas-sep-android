package com.permutassep.model;

import com.google.gson.annotations.SerializedName;

public class ConfirmPasswordReset {

    private String token;
    private String password;
    @SerializedName("password_confirm")
    private String passwordConfirm;

    public ConfirmPasswordReset(String token, String password, String passwordConfirm) {
        this.token = token;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
