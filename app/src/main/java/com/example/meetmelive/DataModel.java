package com.example.meetmelive;

public class DataModel {

    // variables for storing our image and name.
    private String name;
    private String imgUrl;

    public DataModel() {
        // empty constructor required for firebase.
    }

    // constructor for our object class.
    public DataModel(String name, String imgUrl) {
        this.name = name;
        this.imgUrl = imgUrl;
    }

    // getter and setter methods
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
