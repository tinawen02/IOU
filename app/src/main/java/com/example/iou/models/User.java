package com.example.iou.models;

import com.parse.Parse;
import com.parse.ParseClassName;
import com.parse.ParseUser;

// Represents a person who can use the IOU app
@ParseClassName("_User")
public class User extends ParseUser {

    private String username;
    private String password;

    // Empty constructor
    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}