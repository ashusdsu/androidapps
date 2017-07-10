package com.example.ashu.hometownlocation;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by ashu on 10/4/17.
 */

public class FirebaseDB {
    public static String nickname;
    public String uid;
    public String email;
    private String country;
    private String state;



    public FirebaseDB()
    {

    }

    public FirebaseDB(String nickname, String email, String country, String state){
        this.nickname = nickname;
        this.email = email;
        this.country = country;
        this.state = state;

    }
    public String getNickname()
    {
        return nickname;

    }

    public String getCountry()
    {
        return country;
    }

    public String getEmail()
    {
        return email;

    }

    public String getState()
    {
        return state;
    }
}
