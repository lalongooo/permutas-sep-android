package com.permutassep.domain;

/**
 * By Jorge E. Hernandez (@lalongooo) 2015
 */

/**
 * Class that represents a User in the domain layer
 */
public class User {

    private int id;
    private String name;
    private String email;
    private String phone;
    private String socialUserId;
    private String password;

    public User(){
        // empty
    }

    public User(String name, String email, String phone, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSocialUserId() {
        return socialUserId;
    }

    public void setSocialUserId(String socialUserId) {
        this.socialUserId = socialUserId;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", socialUserId='" + socialUserId + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}