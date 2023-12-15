package com.guruji.app;

public class User {
    public String email, name, phoneNo;

    public User(){

    }

    public User(String name, String email, String phoneNo){
        this.name = name;
        this.email = email;
        this.phoneNo = phoneNo;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNo() {
        return phoneNo;
    }


}
