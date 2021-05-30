package com.example.meetmelive.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.HashMap;
import java.util.Map;

@Entity
public class User{

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
    private String age;
    private String profileImageUrl;
    private String pic1;
    private String pic2;
    private String pic3;
    public String currentLocation="";
//    public Boolean isMatch=false;

//    public Boolean isMatch() {
//        return isMatch;
//    }
//
//    public void setMatch(boolean match) {
//        isMatch = match;
//    }


    private double latitude;
    private double longtitude;

    public User() {
    }

    public User(String username){
        this.username = username;
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
        this.age = age;
        this.profileImageUrl = profileImageUrl;
        this.pic1 = pic1;
        this.pic2 = pic2;
        this.pic3 = pic3;
        this.latitude = latitude;
        this.longtitude = longtitude;
    }

    public Map<String, Object> toMap() {
        Map<String,Object> data = new HashMap<>();
        data.put("profileImageUrl", profileImageUrl);
        data.put("username", username);
        data.put("email", email);
        data.put("gender", sex);
        data.put("looking for", preferSex);
        data.put("current Location",null);
        data.put("dateOfBirth",dateOfBirth);
//        data.put("age", age);
        data.put("description",description);
//        data.put("isMatch", false);
        data.put("city",city);
        data.put("pic1", pic1);
        data.put("pic2", pic2);
        data.put("pic3", pic3);
        return data;
    }

    public void fromMap(Map<String, Object> map) {
        setProfileImageUrl((String) map.get("profileImageUrl"));
        setUsername((String) map.get("username"));
        setEmail((String) map.get("email"));
        setSex((String) map.get("gender"));
        setPreferSex((String) map.get("looking for"));
        currentLocation=(String) map.get("current Location");
        setDateOfBirth((String) map.get("dateOfBirth"));
//        setAge((String) map.get("age"));
        setDescription((String) map.get("description"));
        setCity((String) map.get("city"));
        setPic1((String) map.get("pic1"));
        pic2=(String) map.get("pic2");
        pic3=(String) map.get("pic3");
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
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
