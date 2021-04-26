package com.example.meetmelive;

import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Entity;

import java.util.Date;
import java.util.List;

@Entity
public class User  {

    private static User MyUser = null;
    //@PrimaryKey
    //@NonNull
    public String id = "";
    public String name = "";
    public String description="";
    public Date birthDay;
    public String gender="";
    public String currentLocation="";
    public String email = "";
    long lastUpdated;
    public String password;
    public String city;
    RecyclerView listNotification;
    List<ChatMessage> listFriends;


    private User()
    {
        email = null;
        name = null;
        password = null;
        city = null;
        id = null;
        description=null;
        birthDay=null;
        gender=null;
        currentLocation=null;
    }


    public static User getInstance()
    {
        if (MyUser == null)
            MyUser = new User();

        return MyUser;
    }

}
