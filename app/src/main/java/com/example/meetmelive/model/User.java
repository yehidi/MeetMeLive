package com.example.meetmelive.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
public class User implements Serializable{

    // static variable single_instance of type Singleton
    private static User MyUser = null;
    @PrimaryKey
    @NonNull
    private String user_id;
    private String email;
    private String username;
    private String city;
    private String description;
    private String sex;
    private String preferSex;
    private String dateOfBirth;
    private String profileImageUrl;
    private String pic1;
    private String pic2;
    private String pic3;
    private double latitude;
    private double longtitude;

    public User() {
    }

    public User(String sex, String preferSex, String user_id, String email, String username, String city, String description, String dateOfBirth, String profileImageUrl, String pic1, String pic2, String pic3) {
        this.sex = sex;
        this.user_id = user_id;
        this.email = email;
        this.username = username;
        this.city = city;
        this.description = description;
        this.preferSex = preferSex;
        this.dateOfBirth = dateOfBirth;
        this.profileImageUrl = profileImageUrl;
        this.pic1 = pic1;
        this.pic2 = pic2;
        this.pic3 = pic3;
        this.latitude = latitude;
        this.longtitude = longtitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(double longtitude) {
        this.longtitude = longtitude;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) { this.profileImageUrl = profileImageUrl; }

    public String getPic1() { return pic1; }

    public void setPic1(String pic1) { this.pic1 = pic1; }

    public String getPic2() { return pic2; }

    public void setPic2(String pic3) { this.pic2 = pic2; }

    public String getPic3() { return pic3; }

    public void setPic3(String pic3) { this.pic3 = pic3; }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCity() { return city; }

    public void setCity(String city) { this.city = city; }

    public String getDescription() { return description; }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPreferSex() {
        return preferSex;
    }

    public void setPreferSex(String preferSex) {
        this.preferSex = preferSex;
    }

    // Added new attribute called date of birth.
    public String getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(String dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    // create instance of Singleton class
    public static User getInstance()
    {
        if (MyUser == null)
            MyUser = new User();

        return MyUser;
    }

}
