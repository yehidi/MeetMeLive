package com.example.meetmelive;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.meetmelive.adapter.GridAdapter;
import com.example.meetmelive.authentication.login;
import com.example.meetmelive.model.DataModel;
import com.example.meetmelive.model.Model;
import com.example.meetmelive.model.ModelFirebase;
import com.example.meetmelive.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;

import org.jetbrains.annotations.NotNull;

import java.security.acl.Group;
import java.util.ArrayList;
import java.util.List;


public class Nearby<OnOption> extends Fragment{

    // implements RadioGroup.OnCheckedChangeListener

    // creating a variable for our
    // grid view, arraylist and
    // firebase Firestore.
    GridView gridadapter;
    ArrayList<DataModel> dataModelArrayList;
    FirebaseFirestore db;
    View view;

    //try
    String one;
    String two;
    String three;
    RadioGroup Age;
   //try
    public Nearby() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //  return inflater.inflate(R.layout.fragment_nearby, container, false);  activiygrid
        view = inflater.inflate(R.layout.fragment_nearby, container, false);

        // below line is use to initialize our variables.
        gridadapter = view.findViewById(R.id.idGVCourses);
        dataModelArrayList = new ArrayList<>();

        // initializing our variable for firebase
        // firestore and getting its instance.
        db = FirebaseFirestore.getInstance();

        //try
//        Age = view.findViewById(R.id.group_age);

        //try

        // here we are calling a method
        // to load data in our list view.
        Model.instance.loadDatainGridView(dataModelArrayList,gridadapter,this);
        return view;
    }

//    @Override
//    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
//        super.onCreateOptionsMenu(menu, inflater);
//        inflater.inflate(R.menu.nearby_menu,menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch ((item.getItemId())){
//            case R.id.group_age:{
//                onCheckedChanged(Age, item.getGroupId());
//                }
//
//            }
//
//        switch (item.getItemId()){
//            case R.id.editProfileFragment:{
//                Navigation.findNavController(view).navigate(R.id.action_Profile_to_editProfileFragment);
//                return true;
//            }
//            case R.id.SignOut:{
//                FirebaseAuth mAuth = FirebaseAuth.getInstance();
//                mAuth.signOut();
//                startActivity(new Intent(getActivity(), login.class));
//            }
//            case R.id.DeleteAccount:{
//                Model.instance.deleteUser(user, new Model.DeleteUserListener() {
//                    @Override
//                    public void onComplete() {
//                        firebaseuser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
//                            @Override
//                            public void onComplete(@NonNull @NotNull Task<Void> task) {
//                                Navigation.findNavController(view).popBackStack();
//                                startActivity(new Intent(getActivity(), login.class));
//                            }
//                        }).addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull @NotNull Exception e) {
//                                Toast.makeText(MyApplication.context, "Failed To Delete Account", Toast.LENGTH_SHORT).show();
//                            }
//                        });
//                    }
//                });
//
//            }
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//        return super.onOptionsItemSelected(item);
//    }


//   @Override
//    public void onCheckedChanged(RadioGroup group, int i) {
//        switch(i) {
//            case R.id.one:
//                one="18-21";
//                break;
//            case R.id.two:
//                one="21-23";
//                break;
//            case R.id.three:
//                one="23-30";
//                break;
//
//        }
//    }
}