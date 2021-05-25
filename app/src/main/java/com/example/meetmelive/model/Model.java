package com.example.meetmelive.model;




import android.app.Application;
import android.graphics.Bitmap;
import android.os.AsyncTask;

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
        modelFirebase.deleteRecipeCollection(user);
        modelFirebase.deleteUser(user, listener);
        new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... strings) {
                AppLocalDb.db.userDao().deleteUser(user);
                return "";
            }
        }.execute();
    }

}





























//
//import android.annotation.SuppressLint;
//import android.app.Application;
//import android.os.AsyncTask;
//
//import androidx.lifecycle.LiveData;
//
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//
//import java.util.LinkedList;
//import java.util.List;
//
//public class Model {
//
//    public static final Model instance = new Model();
//    ModelFirebase modelFirebase = new ModelFirebase();
//
//
//    //maya added new new new
//    public interface Listener<T>{
//        void onComplete(T data);
//    }
//    public interface CompListener{
//        void onComplete();
//    }
//
//
////    public LiveData<List<Notification>> getAllNotifications(){
////        LiveData<List<Notification>> liveData = AppLocalDb.db.userDao().getAllNotification();
////        return liveData;
////    }
//    //maya added new new new
//
//
//    private Model(){
//    }
//
//
//
//
//
//
//    //maya added new
//    List<Notification> notificationlist=new LinkedList<Notification>();
//
//    List<User> usersList=new LinkedList<User>();
//
//
//    //maya added new
//
//
//    public List<User> getAllActiveUsers(){
//        LiveData<List<User>> data =  AppLocalDb.db.userDao().getAllActiveUsers();
//        return null;
//    }
//
//
//    //maya added new
//    public List<Notification> getAllNotifications() {
//        return notificationlist;
//    }
//
//    public List<User>getAllUsers(){
//        return usersList;
//    }
//
//    //maya added new
//
//
//    //maya added new new new
//    @SuppressLint("StaticFieldLeak")
//    public void addNotification(final Notification not,Listener<Boolean> listener) {
//        modelFirebase.addNotification(not,listener);
////        new AsyncTask<String, String, String>() {
////            @Override
////            protected String doInBackground(String... strings) {
////                AppLocalDb.db.userDao().insertAll(not);
////                return "";
////            }
////        }.execute();
//
//    }
//    //maya added new new new
//}
