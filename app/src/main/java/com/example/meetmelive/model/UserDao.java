package com.example.meetmelive.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {
    @Query("select * from User")
    LiveData<List<User>> getAllActiveUsers();

//    @Query("select * from User where userId = :userId")
//    LiveData<List<User>> getUserRecipes(String userId);

//    @Query("select * from User where category= :category")
//    LiveData<List<User>> getRecipesByCategory(String category);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(User... users);

    @Delete
    void deleteUser(User user);
}