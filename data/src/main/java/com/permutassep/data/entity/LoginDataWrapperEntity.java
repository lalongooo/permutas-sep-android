package com.permutassep.data.entity;

/**
 * By Jorge E. Hernandez (@lalongooo) 2015
 */

import com.google.gson.annotations.SerializedName;

public class LoginDataWrapperEntity {


    @SerializedName("email")
    private String email;
    @SerializedName("password")
    private String password;

    public LoginDataWrapperEntity(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
