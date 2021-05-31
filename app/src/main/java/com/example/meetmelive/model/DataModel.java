package com.example.meetmelive.model;

public class DataModel {

    // variables for storing our image and name.
    private String username;
    private String profileImageUrl;
    private String userId;
    private String email;


    public DataModel() {
        // empty constructor required for firebase.
    }

    // constructor for our object class.
    public DataModel(String username, String profileImageUrl, String userId) {
        this.username = username;
        this.profileImageUrl = profileImageUrl;
        this.userId = userId;
    }

    // getter and setter methods
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    //maya added
//    public void fromMap(Map<String, Object> map) {
//        name = (String)map.get("name");
//        imgUrl = (String)map.get("imgUrl");
//    }
    //maya added
}