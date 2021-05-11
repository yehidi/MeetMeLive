package com.example.meetmelive;

public class DataModel {

    // variables for storing our image and name.
    private String username;
    private String profileImageUrl;

    public DataModel() {
        // empty constructor required for firebase.
    }

    // constructor for our object class.
    public DataModel(String username, String profileImageUrl) {
        this.username = username;
        this.profileImageUrl = profileImageUrl;
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


    //maya added
//    public void fromMap(Map<String, Object> map) {
//        name = (String)map.get("name");
//        imgUrl = (String)map.get("imgUrl");
//    }
    //maya added
}
