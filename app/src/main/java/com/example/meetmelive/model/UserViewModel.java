//package com.example.meetmelive.model;
//
//import android.util.Log;
//
//import androidx.annotation.NonNull;
//import androidx.lifecycle.LiveData;
//import androidx.lifecycle.ViewModel;
//
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.firestore.FirebaseFirestore;
//import com.google.firebase.firestore.QueryDocumentSnapshot;
//import com.google.firebase.firestore.QuerySnapshot;
//
//import java.util.LinkedList;
//import java.util.List;
//
//public class UserViewModel extends ViewModel {
//
//    private LiveData<List<User>> userLiveData;
//
//    public UserViewModel() {
//        Log.d("TAG", "RecipeViewModel");
//        userLiveData = Model.instance.getAllRequests();
//
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//
//        db.collection("userProfileData").document(User.getInstance().getEmail()).collection("friendRequests").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                List<User> userList = new LinkedList<User>();
//                if (task.isSuccessful()) {
//                    for (QueryDocumentSnapshot document : task.getResult()) {
//                        User use = new User();
//                        use.fromMap(document.getData());
//                        userList.add(use);
//                        Log.d("TAG", document.getId() + " => " + document.getData());
//                    }
//                }
//                userLiveData = (LiveData<List<User>>) userList;
//            }
//        });
//    }
//
//    public LiveData<List<User>> getData() {
//        return userLiveData;
//    }
//}