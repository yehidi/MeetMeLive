package com.example.meetmelive.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.firebase.Timestamp;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Entity
public class User implements Serializable{

    // static variable single_instance of type Singleton
    private static User MyUser = null;

    @PrimaryKey
    @NonNull
    public String userId;
    public String email;
    public String username;
    public String city;
    public String description;
    public String gender;
    public String lookingForGender;
    public String dateOfBirth;
    public String profileImageUrl;
    public String pic1;
    public String pic2;
    public String pic3;
    public Double latitude;
    public Double longtitude;
    public long lastUpdatedLocation;
//    public String minAgeLookingFor;
//    public String maxAgeLookingFor;

    public User() {

    }

    public User(String userId,String email,String username, String city,
                String description,String gender,String lookingForGender,String dateOfBirth,
                String profileImageUrl, String pic1, String pic2, String pic3,
                Double latitude,Double longtitude, long lastUpdatedLocation) {

        this.userId = userId;
        this.email = email;
        this.username = username;
        this.city = city;
        this.description = description;
        this.gender = gender;
        this.lookingForGender = lookingForGender;
        this.dateOfBirth = dateOfBirth;
        this.profileImageUrl = profileImageUrl;
        this.pic1 = pic1;
        this.pic2 = pic2;
        this.pic3 = pic3;
        this.latitude = latitude;
        this.longtitude = longtitude;
        this.lastUpdatedLocation=lastUpdatedLocation;
    }


    public Map<String, Object> toMap() {
        Map<String,Object> data = new HashMap<>();
        data.put("userId",userId);
        data.put("email", email);
        data.put("username", username);
        data.put("city",city);
        data.put("description",description);
        data.put("gender", gender);
        data.put("lookingForGender", lookingForGender);
        data.put("dateOfBirth",dateOfBirth);
        data.put("profileImageUrl", profileImageUrl);
        data.put("pic1", pic1);
        data.put("pic2", pic2);
        data.put("pic3", pic3);
        data.put("latitude",latitude);
        data.put("longtitude",longtitude);
        data.put("lastUpdatedLocation",lastUpdatedLocation);
        //data.put("looking For Age",lookingForAge);
        return data;
    }


    public void fromMap(Map<String, Object> map) {
        userId=(String) map.get("userId");
        email=(String) map.get("email");
        username=(String) map.get("username");
        city=(String) map.get("city");
        description=(String) map.get("description");
        gender=(String) map.get("gender");
        lookingForGender=(String) map.get("lookingForGender");
        dateOfBirth=(String) map.get("dateOfBirth");
        profileImageUrl=(String) map.get("profileImageUrl");
        pic1=(String) map.get("pic1");
        pic2=(String) map.get("pic2");
        pic3=(String) map.get("pic3");
        latitude= (Double) map.get("latitude");
        longtitude= (Double) map.get("longtitude");
        Timestamp ts = (Timestamp) map.get("lastUpdatedLocation");
        lastUpdatedLocation=ts.getSeconds();
//        lookingForAge= (String) map.get("looking For Age");
    }

    public static User getMyUser() {
        return MyUser;
    }

    public static void setMyUser(User myUser) {
        MyUser = myUser;
    }

    @NonNull
    public String getUserId() {
        return userId;
    }

    public void setUserId(@NonNull String userId) {
        this.userId = userId;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLookingForGender() {
        return lookingForGender;
    }

    public void setLookingForGender(String lookingForGender) {
        this.lookingForGender = lookingForGender;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public String getPic1() {
        return pic1;
    }

    public void setPic1(String pic1) {
        this.pic1 = pic1;
    }

    public String getPic2() {
        return pic2;
    }

    public void setPic2(String pic2) {
        this.pic2 = pic2;
    }

    public String getPic3() {
        return pic3;
    }

    public void setPic3(String pic3) {
        this.pic3 = pic3;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(Double longtitude) {
        this.longtitude = longtitude;
    }

    public long getLastUpdatedLocation() {
        return lastUpdatedLocation;
    }

    public void setLastUpdatedLocation(long lastUpdatedLocation) {
        this.lastUpdatedLocation = lastUpdatedLocation;
    }

    // create instance of Singleton class
    public static User getInstance()
    {
        if (MyUser == null)
            MyUser = new User();

        return MyUser;
    }

}