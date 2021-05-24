package com.example.meetmelive.model;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.List;

public class Model {

    public static final Model instance = new Model();
    LiveData<ArrayList<String>> matchList;

    private Model(){
    }

    public List<User> getAllActiveUsers(){
        LiveData<List<User>> data =  AppLocalDb.db.userDao().getAllActiveUsers();
        return null;
    }

//    public LiveData<ArrayList<String>> getRecipesByCategory(String gender) {
//        matchList = AppLocalDb.db.userDao().getUserMatch(gender);
//        return matchList;
//    }



}
