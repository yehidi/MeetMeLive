package com.example.meetmelive;

import android.os.Bundle;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
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
import android.widget.Toast;

import com.example.meetmelive.adapter.GridAdapter;
import com.example.meetmelive.model.DataModel;
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

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class Nearby extends Fragment {

    // creating a variable for our
    // grid view, arraylist and
    // firebase Firestore.
    GridView gridadapter;
    ArrayList<DataModel> dataModelArrayList;
    FirebaseFirestore db;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        //  return inflater.inflate(R.layout.fragment_nearby, container, false);  activiygrid
        view = inflater.inflate(R.layout.fragment_nearby, container, false);

        // below line is use to initialize our variables.
        gridadapter = view.findViewById(R.id.idGVCourses);
        dataModelArrayList = new ArrayList<>();

        // initializing our variable for firebase
        // firestore and getting its instance.
        db = FirebaseFirestore.getInstance();

        setUser(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        Log.d("NearBy", "!!!!!!!!!!!!!!!!!!!");

        // here we are calling a method
        // to load data in our list view.
//        loadDatainGridView();
        return view;
    }

    private void setUser(final String email){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        db.collection("userProfileData").document(email).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){ ;
                    User.getInstance().userId = (String) task.getResult().get("userId");
                    User.getInstance().email = email;
                    User.getInstance().username = (String) task.getResult().get("username");
                    User.getInstance().city= (String) task.getResult().get("city");
                    User.getInstance().description = (String) task.getResult().get("description");
                    User.getInstance().gender = (String) task.getResult().get("gender");
                    User.getInstance().lookingForGender = (String) task.getResult().get("lookingForGender");
                    User.getInstance().dateOfBirth= (String) task.getResult().get("dateOfBirth");
                    User.getInstance().profileImageUrl = (String) task.getResult().get("profileImageUrl");
                    User.getInstance().pic1= (String) task.getResult().get("pic1");
                    User.getInstance().pic2= (String) task.getResult().get("pic2");
                    User.getInstance().pic3= (String) task.getResult().get("pic3");
                    User.getInstance().latitude= 0.0;
                    User.getInstance().longtitude= 0.0;
                    Log.d("SetUser", "******************");

                    loadDatainGridView();
                    // User.getInstance().lookingForAge= (String) task.getResult().get("looking For Age");
                }
            }
        });
    }
    private void loadDatainGridView() {

        Log.d("LoadDataInGridView", "#####################");

        db.collection("userProfileData")
                .whereEqualTo("gender", User.getInstance().getLookingForGender())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                // after getting this list we are passing
                                // that list to our object class.
                                DataModel dataModel = document.toObject(DataModel.class);

                                // after getting data from Firebase
                                // we are storing that data in our array list
                                if (!document.get("email").equals(User.getInstance().getEmail()))
                                {
                                    Log.d("@@@@@@@@@@@@@@@@@@@@", "datamodel email is " + document.get("email")
                                            + " || " + User.getInstance().getEmail());
                                    dataModelArrayList.add(dataModel);
                                }
                                Log.d("TAG", document.getId() + " => " + document.getData());

                            }

                            Log.d("ARRAY LIST", "" + dataModelArrayList);

                            if (getActivity()!= null){
                                GridAdapter adapter = new GridAdapter(getActivity(), dataModelArrayList);
                                gridadapter.setAdapter(adapter);}
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.filter_menu, menu);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:{
                 Navigation.findNavController(view).navigate(R.id.action_Nearby_to_city);

                return true;
            }
//
//            case R.id.subitem1: {
//                one = "18-21";
//                Model.instance.UpdateUserSuggestions(one);
//                Age1=fromStr_toInt1(one);
//                Age2=fromStr_toInt2(one);
//                dataModelArrayList.clear();
//                dataModelArrayList2.clear();
//                loadDatainGridView(Age1,Age2);
//                return true;
//            }
//            case R.id.subitem2:{
//                one="22-25";
//                Model.instance.UpdateUserSuggestions(one);
//                Age1=fromStr_toInt1(one);
//                Age2=fromStr_toInt2(one);
//                dataModelArrayList.forEach(i->{
//                    Log.d("array list", "item "+i.getEmail());
//                });

//                ArrayList<String> list=new ArrayList<String>();
//                String[] locales = Locale.getISOCountries();
//                for (String countryCode : locales) {
//                    Locale obj = new Locale("", countryCode);
//                    list.add(obj.getDisplayCountry());
//                    Log.d("countries", "country :"+obj.getDisplayCountry() +"code "+obj);
//                }

//                dataModelArrayList.clear();
//                dataModelArrayList2.clear();
//                loadDatainGridView(Age1,Age2);
//                return true;
//            }
//            case R.id.subitem3:{
//                one="26-31";
//                Model.instance.UpdateUserSuggestions(one);
//                Age1=fromStr_toInt1(one);
//                Age2=fromStr_toInt2(one);
//                dataModelArrayList.clear();
//                dataModelArrayList2.clear();
//                loadDatainGridView(Age1,Age2);
//                return true;
//            }
            default:
                return super.onOptionsItemSelected(item);
        }


    }
}
