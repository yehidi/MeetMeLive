package com.example.meetmelive.model;

import java.util.LinkedList;
import java.util.List;

public class Model {

    public static final Model instance = new Model();
    //ModelFirebase modelFirebase = new ModelFirebase();

    private Model(){
    }

    //added
    List<Notification> data = new LinkedList<Notification>();
    public List<Notification> getAllNotifications() {
        return data;
    }

    //added
}
