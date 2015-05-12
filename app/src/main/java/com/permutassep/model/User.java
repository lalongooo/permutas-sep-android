package com.permutassep.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.google.gson.annotations.SerializedName;

/**
 * Created by lalongooo on 02/03/15.
 */
public class User extends Model {

    @SerializedName("id")
    @Column(name = "id_")
    private int id;

    @SerializedName("name")
    @Column(name = "name")
    private String name;

    @SerializedName("email")
    @Column(name = "email")
    private String email;

    @SerializedName("phone")
    @Column(name = "phone")
    private String phone;

    @SerializedName("social_user_id")
    @Column(name = "social_user_id")
    private String socialUserId;

    @Column(name = "password")
    private String password;

    public User(){}

    public User(int id, String name, String email, String phone, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
    }

    public User(int id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public User(String name, String email, String phone, String password) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
    }

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public int getId_() {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}