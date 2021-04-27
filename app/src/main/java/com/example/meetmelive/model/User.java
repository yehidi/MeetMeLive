package com.example.meetmelive.model;

public class User {

    // static variable single_instance of type Singleton
    private static User theUser = null;

    public String userUsername;
    public String userEmail;
    public String profileImageUrl;
    public String userId;
    public String userInfo;
    public String gender;

    private User()
    {
        userEmail = null;
        userUsername = null;
        profileImageUrl = null;
        userId = null;
        userInfo = null;
        gender = null;
    }

    // create instance of Singleton class
    public static User getInstance()
    {
        if (theUser == null)
            theUser = new User();

        return theUser;
    }

}
