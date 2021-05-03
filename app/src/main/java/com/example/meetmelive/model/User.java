package com.example.meetmelive.model;

import java.util.Date;
import java.util.List;
public class User {

    // static variable single_instance of type Singleton
    private static User MyUser = null;
    //@PrimaryKey
    //@NonNull
    public String id = "";
    public String name = "";
    public String description="";
    public Date birthday;
    public String gender="";
    public String interestedIn ="";
    public String currentLocation="";
    public String email = "";
    long lastUpdated;
    public String password;
    public String city;
    List<String> listNotification;
    List<ChatMessage> listFriends;
    public String profilePic;
    public String pic1;
    public String pic2;
    public String pic3;

    private User()
    {
        email = null;
        name = null;
        password = null;
        city = null;
        id = null;
        description=null;
        birthday=null;
        gender=null;
        interestedIn =null;
        currentLocation=null;
        listNotification=null;
        listFriends=null;
        profilePic=null;
        pic1=null;
        pic2=null;
        pic3=null;
    }

    // create instance of Singleton class
    public static User getInstance()
    {
        if (MyUser == null)
            MyUser = new User();

        return MyUser;
    }

}
