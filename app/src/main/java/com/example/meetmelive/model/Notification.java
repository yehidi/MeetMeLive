package com.example.meetmelive.model;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Notification  {


    public String senderId;
    public String senderName;
    public Integer senderAge;
    public String senderEmail;
    public String  senderImage;

    public Notification() {

    }

    public Notification(String senderId, String senderName, Integer senderAge, String senderEmail, String senderImage) {
        this.senderId = senderId;
        this.senderName = senderName;
        this.senderAge=senderAge;
        this.senderEmail = senderEmail;
        this.senderImage = senderImage;
    }

    public Notification(User instance) {

        this.senderId=instance.id;
        this.senderName=instance.name;
//        this.senderAge=instance.age;
        this.senderEmail=instance.email;
        this.senderImage=instance.profilePic;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public Integer getSenderAge() {
        return senderAge;
    }

    public void setSenderAge(Integer SenderAge) {
         this.senderAge=senderAge;
    }

    public String getSenderEmail() {
        return senderEmail;
    }

    public void setSenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
    }

    public String getSenderImage() {
        return senderImage;
    }

    public void setSenderImage(String senderImage) {
        this.senderImage = senderImage;
    }
    //maya added new
//    public Notification()
//    {
//        currentUserEmail="";
//        Username="";
//        age=null;
//        image="";
//        SendToUser="";
//
//    }
//    public Notification(String SendToUser,String currentUserEmail )
//    {
//        this.currentUserEmail=currentUserEmail;
//        this.SendToUser=SendToUser;
//    }
//
//    public Map<String,Object> toMap(){
//        Map<String,Object> data = new HashMap<>();
//        data.put("profileImageUrl", task.getResult().toString());
//        data.put("username", username);
//        data.put("email", email);
//        data.put("gender", gender);
//        data.put("looking for", lookingForGender);
//    }

    public void SendRequest(Notification notif)
    {

    }

    //maya added new



}
