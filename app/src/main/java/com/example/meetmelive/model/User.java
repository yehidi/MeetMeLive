package com.example.meetmelive.model;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Timestamp;
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
    public String gender="";
    public String lookingForGender ="";
    public String currentLocation="";
    public String email = "";
    public long lastUpdated;
    public String password;
    public String city;
    // List<String> listNotification;// need to be tested
    //List<ChatMessage> listFriends;
    public String profilePic;
    public String pic1;
    public String pic2;
    public String pic3;

    public boolean isActive;

    public User()
    {
        email = null;
        name = null;
        password = null;
        city = null;
        birthday=null;
        id = null;
        description=null;
        gender=null;
        lookingForGender =null;
        currentLocation=null;
//        listNotification=null;
//        listFriends=null;
        profilePic=null;
        pic1=null;
        pic2=null;
        pic3=null;
        isActive=true;
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
        data.put("info",description);
        data.put("city",city);
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
        currentLocation=(String) map.get("current Location");
        birthday=(String) map.get("birthDate");
        description=(String) map.get("info");
        city=(String) map.get("city");
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
        this.isActive=true;
//        lastUpdated=FieldValue.serverTimestamp();
    }

    // create instance of Singleton class
    public static User getInstance()
    {
        if (MyUser == null)
            MyUser = new User();

        return MyUser;
    }

}





















//
//import androidx.annotation.NonNull;
//import androidx.room.Entity;
//import androidx.room.PrimaryKey;
//
//import com.google.firebase.auth.FirebaseUser;
//
//import java.util.Date;
//import java.util.LinkedList;
//import java.util.List;
//
//@Entity
//public class User {
//
//
//    //maya added new
////   public List<Notification> Mynotificationlist;
//    public Integer age;
//    //maya added new
//
//
//    // static variable single_instance of type Singleton
//    private static User MyUser = null;
//    @PrimaryKey
//    @NonNull
//    public String id = "";
//    public String name = "";
//    public String description="";// about me
//    public long birthday;
//    public String gender="";
//    public String lookingForGender ="";
//    public String currentLocation="";
//    public String email = "";
//    public long lastUpdated;
//    public String password;
//    public String city;
//
////    public List<Notification> getMynotificationlist() {
////        return Mynotificationlist;
////    }
////
////    public void setMynotificationlist(List<Notification> mynotificationlist) {
////        Mynotificationlist = mynotificationlist;
////    }
//
//    // List<String> listNotification;// need to be tested
//    //List<ChatMessage> listFriends;
//    public String profilePic;
//    public String pic1;
//    public String pic2;
//    public String pic3;
//
//    public boolean isActive;
//
//    public User()
//    {
//        email = null;
//        name = null;
//        password = null;
//        city = null;
//        id = null;
//        description=null;
//        gender=null;
//        lookingForGender =null;
//        currentLocation=null;
////        listNotification=null;
////        listFriends=null;
//        profilePic=null;
//        pic1=null;
//        pic2=null;
//        pic3=null;
//        isActive=true;
////        Mynotificationlist =new LinkedList<Notification>();
//    }
//
//
//
//    // create instance of Singleton class
//    public static User getInstance()
//    {
//        if (MyUser == null)
//            MyUser = new User();
//
//        return MyUser;
//    }
//
//    public Integer getAge() {
//        return age;
//    }
//
//    public void setAge(Integer age) {
//        this.age = age;
//    }
//
//    @NonNull
//    public String getId() {
//        return id;
//    }
//
//    public void setId(@NonNull String id) {
//        this.id = id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//
//    public long getBirthday() {
//        return birthday;
//    }
//
//    public void setBirthday(long birthday) {
//        this.birthday = birthday;
//    }
//
//    public String getGender() {
//        return gender;
//    }
//
//    public void setGender(String gender) {
//        this.gender = gender;
//    }
//
//    public String getLookingForGender() {
//        return lookingForGender;
//    }
//
//    public void setLookingForGender(String lookingForGender) {
//        this.lookingForGender = lookingForGender;
//    }
//
//    public String getCurrentLocation() {
//        return currentLocation;
//    }
//
//    public void setCurrentLocation(String currentLocation) {
//        this.currentLocation = currentLocation;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public long getLastUpdated() {
//        return lastUpdated;
//    }
//
//    public void setLastUpdated(long lastUpdated) {
//        this.lastUpdated = lastUpdated;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public String getCity() {
//        return city;
//    }
//
//    public void setCity(String city) {
//        this.city = city;
//    }
//
//    public String getProfilePic() {
//        return profilePic;
//    }
//
//    public void setProfilePic(String profilePic) {
//        this.profilePic = profilePic;
//    }
//
//    public String getPic1() {
//        return pic1;
//    }
//
//    public void setPic1(String pic1) {
//        this.pic1 = pic1;
//    }
//
//    public String getPic2() {
//        return pic2;
//    }
//
//    public void setPic2(String pic2) {
//        this.pic2 = pic2;
//    }
//
//    public String getPic3() {
//        return pic3;
//    }
//
//    public void setPic3(String pic3) {
//        this.pic3 = pic3;
//    }
//
//
//
//
//    //maya added new
//    public static User FindUserBy_nameANDimg(String name, String img)
//    {
//        User user=new User();
//        user.profilePic=img;
//        user.name=name;
//
//        List<User> list=Model.instance.getAllUsers();
//        if (list.contains(user))
//            return user;
//        return null;
//    }
//
//    public static User FindUserBy_email( String email)
//    {
//
//        List<User> list=Model.instance.getAllUsers();
//        for(int i=0;i<list.size();i++)
//        {
//            if (list.get(i).getEmail()==email)
//                return list.get(i);
//            return null;
//        }
//        return null;
//    }
//    //maya added new
//}
