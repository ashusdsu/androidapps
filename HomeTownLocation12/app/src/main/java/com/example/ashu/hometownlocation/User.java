package com.example.ashu.hometownlocation;

/**
 * Created by ashu on 11/4/17.
 */

public class User {

    public String countries;
    public String state;
    public String user_id;
    public String nick;
    public String city;
    public String timestamp;
    public String Longitude;
    public String Latitude;
    public String year;

    public User()
    {

    }

    public User(String n, String s, String c, String ui, String ti, String lo, String la, String city, String year)
    {
        this.nick = n;
        this.countries = c;
        this.state = s;
        this.timestamp = ti;
        this.user_id = ui;
        this.Longitude = lo;
        this.Latitude = la;
        this.city = city;
        this.year = year;
    }

    public String getNick()
    {
        return this.nick;
    }

    public String getCountries()
    {
        return this.countries;
    }

    public String getCity()
    {
        return this.city;
    }
    public String getState()
    {
        return this.state;
    }


    public String getUser_id()
    {
        return this.user_id;
    }
}
