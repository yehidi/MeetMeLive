package com.example.meetmelive.model;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class Model {

    public static final Model instance = new Model();
    ModelFirebase modelFirebase = new ModelFirebase();
    LiveData<List<User>> userList;

    private Model(){
    }

    public interface Listener<T>{
        void onComplete(T result);
    }

    public interface GetAllActiveUsersListener{
        void onComplete();
    }

    public  LiveData<List<User>> getAllActiveUsers(){
        userList =  AppLocalDb.db.userDao().getAllActiveUsers();

        return userList;
    }



}
