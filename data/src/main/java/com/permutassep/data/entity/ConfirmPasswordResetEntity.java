package com.permutassep.data.entity;

import com.google.gson.annotations.SerializedName;

public class ConfirmPasswordResetEntity {

    private String token;
    private String password;
    @SerializedName("password_confirm")
    private String passwordConfirm;

    public ConfirmPasswordResetEntity(String token, String password, String passwordConfirm) {
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
