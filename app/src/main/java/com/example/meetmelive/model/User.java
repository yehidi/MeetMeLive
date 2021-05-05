package com.example.meetmelive.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;
import java.util.List;

@Entity
public class User {

    // static variable single_instance of type Singleton
    private static User MyUser = null;
    @PrimaryKey
    @NonNull
    public String id = "";
    public String name = "";
    public String description="";// about me
    public long birthday;
    public String gender="";
    public String lookingForGender ="";
    public String currentLocation="";
    public String email = "";
    public long lastUpdated;
    public String password;
    public String city;
   // List<String> listNotification;// need to be tested
    //List<ChatMessage> listFriends;
    public String profilePic;
    public String pic1;
    public String pic2;
    public String pic3;

    public boolean isActive;

    public User()
    {
        email = null;
        name = null;
        password = null;
        city = null;
        id = null;
        description=null;
        gender=null;
        lookingForGender =null;
        currentLocation=null;
//        listNotification=null;
//        listFriends=null;
        profilePic=null;
        pic1=null;
        pic2=null;
        pic3=null;
        isActive=true;
    }

    // create instance of Singleton class
    public static User getInstance()
    {
        if (MyUser == null)
            MyUser = new User();

        return MyUser;
    }

}
