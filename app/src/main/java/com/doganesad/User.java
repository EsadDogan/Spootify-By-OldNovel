package com.doganesad;

public class User {
    private String uID;
    private String userName;
    private String email;
    protected String password;

    public User(String uID, String userName, String email, String password) {
        this.uID = uID;
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    //empty constructor
    public User() {
    }

    public String getuID() {
        return uID;
    }

    public void setuID(String uID) {
        this.uID = uID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
