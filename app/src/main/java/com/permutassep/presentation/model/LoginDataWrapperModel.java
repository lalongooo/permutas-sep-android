package com.permutassep.presentation.model;

/**
 * By Jorge E. Hernandez (@lalongooo) 2015
 */

public class LoginDataWrapperModel {

    private String email;
    private String password;

    public LoginDataWrapperModel(String email, String password) {
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