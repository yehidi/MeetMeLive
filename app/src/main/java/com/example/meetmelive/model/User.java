package com.example.meetmelive.model;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FieldValue;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
public class User {

    // static variable single_instance of type Singleton
    private static User MyUser = null;

    @PrimaryKey
    @NonNull
    public String id = "";
    public String name = "";
    public String description="";// about me
    public String birthday;
    public String lookingForAge;
    public String gender="";
    public String lookingForGender ="";
    public String email = "";
    public String password;
    public String city;
    public Double latitude;
    public Double longtitude;
    public long lastUpdatedLocation;
    public String profilePic;
    public String pic1;
    public String pic2;
    public String pic3;


    public User()
    {
        email = null;
        name = null;
        password = null;
        city = null;
        birthday=null;
        lookingForAge="18-24";
        id = null;
        description=null;
        gender=null;
        lookingForGender =null;
        latitude = 0.0;
        longtitude = 0.0;
        lastUpdatedLocation = 0;
        profilePic=null;
        pic1=null;
        pic2=null;
        pic3=null;
    }


    public Map<String, Object> toMap() {
        Map<String,Object> data = new HashMap<>();
        data.put("profileImageUrl", profilePic);
        data.put("username", name);
        data.put("email", email);
        data.put("gender", gender);
        data.put("looking for", lookingForGender);
        data.put("current Location",null);
        data.put("birthDate",birthday);
        data.put("looking For Age",lookingForAge);
        data.put("info",description);
        data.put("city",city);
        data.put("latitude",latitude);
        data.put("longtitude",longtitude);
        data.put("last Updated Location",lastUpdatedLocation);
        data.put("picture 1", pic1);
        data.put("picture 2", pic2);
        data.put("picture 3", pic3);
        return data;
    }

    public void fromMap(Map<String, Object> map) {
        profilePic=(String) map.get("profileImageUrl");
        name=(String) map.get("username");
        email=(String) map.get("email");
        gender=(String) map.get("gender");
        lookingForGender=(String) map.get("looking for");
        birthday=(String) map.get("birthDate");
        lookingForAge= (String) map.get("looking For Age");
        description=(String) map.get("info");
        city=(String) map.get("city");
        latitude= (Double) map.get("latitude");
        longtitude= (Double) map.get("longtitude");
        Timestamp ts = (Timestamp) map.get("last Updated Location");
        lastUpdatedLocation=ts.getSeconds();
        pic1=(String) map.get("picture 1");
        pic2=(String) map.get("picture 2");
        pic3=(String) map.get("picture 3");
    }


    public User(String email,String name,String birthday,String description,String gender,String lookingForGender,String city,String profilePic,String pic1,String pic2,String pic3) {
        this.name = name;
        this.city = city;
        this.birthday=birthday;
        this.email = email;
        this.description=description;
        this.gender=gender;
        this.lookingForGender =lookingForGender;
        this.profilePic=profilePic;
        this.pic1=pic1;
        this.pic2=pic2;
        this.pic3=pic3;
    }

    // create instance of Singleton class
    public static User getInstance()
    {
        if (MyUser == null)
            MyUser = new User();

        return MyUser;
    }

}
