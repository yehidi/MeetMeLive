package com.example.meetmelive;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.meetmelive.model.Model;
import com.example.meetmelive.model.User;

import java.util.List;

public class NearByViewModel extends ViewModel {
    LiveData<List<User>> liveData;

    public void refresh(Model.CompListener listener) {
        Model.instance.refreshAllUsers(listener);
    }

}
