package com.example.meetmelive.model;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class Model {

    public static final Model instance = new Model();
    ModelFirebase modelFirebase = new ModelFirebase();

    private Model(){
    }

    public List<User> getAllActiveUsers(){
        LiveData<List<User>> data =  AppLocalDb.db.userDao().getAllActiveUsers();
        return null;
    }





}
