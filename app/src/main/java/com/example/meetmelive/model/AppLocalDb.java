package com.example.meetmelive.model;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.meetmelive.MyApplication;


@Database(entities = {User.class}, version = 1)
abstract class AppLocalDbRepository extends RoomDatabase {
    public abstract UserDao userDao();
}

public class AppLocalDb{

    static public AppLocalDbRepository db =
            Room.databaseBuilder(MyApplication.context,
                    AppLocalDbRepository.class,
                    "users.db")
                    .fallbackToDestructiveMigration()
                    .build();
}