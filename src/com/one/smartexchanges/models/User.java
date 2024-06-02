package com.one.smartexchanges.models;

import java.util.List;
import java.util.ArrayList;

public class User {
    private String username;
    private String password;
    private  List<Consult> consultHistory;

    public User(String username, String password){
        this.username = username;
        this.password = password;
        this.consultHistory = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public List<Consult> getConsultHistory(){
        return consultHistory;
    }
}
