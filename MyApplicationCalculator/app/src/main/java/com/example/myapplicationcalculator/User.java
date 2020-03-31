package com.example.myapplicationcalculator;

public class User {
    private int id;
    private String strUsername = "";
    private String strPassword = "";
    public User(){

    }
    public User(String username, String password){
        this.strUsername = username;
        this.strPassword = password;
    }

    public void setUsername(String username){
        this.strUsername = username;
    }

    public void setPassword(String password){
        this.strPassword = password;
    }

    public int getId(){
        return id;
    }

    public String getUsername(){
        return strUsername;
    }

    public String getPassword(){
        return strPassword;
    }

}
