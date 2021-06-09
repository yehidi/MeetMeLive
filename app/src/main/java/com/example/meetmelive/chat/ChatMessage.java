package com.example.meetmelive.chat;

import java.util.Date;

public class ChatMessage {
    private String messageText;
    private String messageUser; //User sender
    private long messageTime;
    //private long ts;

    public ChatMessage(String messageText, String messageUser) {
        this.messageText = messageText;
        this.messageUser = messageUser;

        // Initialize to current time
        long ts=System.currentTimeMillis()/1000;
        messageTime=new Date().getTime();
        //ts=messageTime.toString();

    }

    public ChatMessage(){

    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getMessageUser() {
        return messageUser;
    }

    public void setMessageUser(String messageUser) {
        this.messageUser = messageUser;
    }

    public long getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(long messageTime) {
        this.messageTime = messageTime;
    }

}

