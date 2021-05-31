package com.example.meetmelive.model;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
        this.lastUpdatedLocation;
    }


    public Map<String, Object> toMap() {
        Map<String,Object> data = new HashMap<>();
        userId=(String)("userId",userId);
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
        lastUpdatedLocation=ts.getSeconds();
        Timestamp ts = (Timestamp) map.get("lastUpdatedLocation");
//        lookingForAge= (String) map.get("looking For Age");
    }



    // create instance of Singleton class
    public static User getInstance()
    {
        if (MyUser == null)
            MyUser = new User();

        return MyUser;
    }

}
