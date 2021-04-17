package com.example.qrcare;

public class User {

    public String username;
    public String email;
    public String mobile_no;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String username, String email, String mobile_no) {
        this.username = username;
        this.email = email;
        this.mobile_no= mobile_no;
    }

}

