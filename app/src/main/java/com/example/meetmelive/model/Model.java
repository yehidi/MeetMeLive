package com.example.meetmelive.model;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.meetmelive.MyApplication;

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


    //Get
    public interface GetUserListener{
        void onComplete(User user);
    }
    public void getUser(String email, GetUserListener listener){
        modelFirebase.getUser(email, listener);
    }



    public interface GetAllActiveUsersListener{
        void onComplete();
    }

    public  LiveData<List<User>> getAllActiveUsers(){
        userList =  AppLocalDb.db.userDao().getAllActiveUsers();

        return userList;
    }

    //Upload
    public interface UploadImageListener extends Listener<String> {}
    public void uploadImage(Bitmap imageBmp, String name, final UploadImageListener listener){
        modelFirebase.uploadImage(imageBmp,name,listener);
    }

    //update user profile

    public void updateUserProfile(User user) {
        ModelFirebase.updateUserProfile(user);
        new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... strings) {
                AppLocalDb.db.userDao().insertAll(user);
                return "";
            }
        }.execute();
    }


    //Delete
    public interface DeleteUserListener {
        void onComplete();
    }
    public void deleteUser(User user, DeleteUserListener listener){
        modelFirebase.deleteUserCollection(user);
        modelFirebase.deleteUser(user, listener);
        modelFirebase.signOut();
        new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... strings) {
                AppLocalDb.db.userDao().deleteUser(user);
                return "";
            }
        }.execute();
    }

    //Refresh - Odeya added
    public interface CompListener{
        void onComplete();
    }
    public void refreshAllUsers(final CompListener listener){
        long lastUpdated = MyApplication.context.getSharedPreferences("TAG", Context.MODE_PRIVATE).getLong("UsersLastUpdateDate",0);

        ModelFirebase.getAllUsersSince(lastUpdated,new Listener<List<User>>() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onComplete(final List<User> data) {
                new AsyncTask<String,String,String>(){
                    @Override
                    protected String doInBackground(String... strings) {
                        long lastUpdated = 0;
                        for(User user : data){
                            AppLocalDb.db.userDao().insertAll(user);
                            if (user.lastUpdatedLocation > lastUpdated){
                                lastUpdated = user.lastUpdatedLocation;
                            }
                        }
                        SharedPreferences.Editor edit = MyApplication.context.getSharedPreferences("TAG",Context.MODE_PRIVATE).edit();
                        edit.putLong("UsersLastUpdateLocation",lastUpdated);
                        edit.commit();
                        return "";
                    }
                    @Override
                    protected void onPostExecute(String s) {
                        super.onPostExecute(s);
                        //cleanLocalDb();
                        if (listener!=null)  listener.onComplete();
                    }
                }.execute("");
            }
        });
    }
    }

