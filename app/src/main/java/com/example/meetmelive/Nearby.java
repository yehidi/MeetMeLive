package com.example.meetmelive;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.meetmelive.adapter.GridAdapter;
import com.example.meetmelive.model.DataModel;
import com.example.meetmelive.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Nearby extends Fragment {

    // creating a variable for our
    // grid view, arraylist and
    // firebase Firestore.
    GridView gridadapter;
    ArrayList<DataModel> dataModelArrayList;
    FirebaseFirestore db;
    View view;
    //NearByViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        //Odeya added- swipeToRefresh
//        final SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.nearby_fragment_swipe_refresh);
//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                viewModel.refresh(new Model.CompListener() {
//                    @Override
//                    public void onComplete() {
//                        swipeRefreshLayout.setRefreshing(false);
//                    }
//                });
//            }
//        });
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
}


//        // below line is use to get data from Firebase
//        // firestore using collection in android.
//        Log.d("NearBy", "Looking for is " + User.getInstance().getPreferSex());
//        db.collection("userProfileData").whereEqualTo("gender", User.getInstance().getPreferSex()).get()
//                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//                    @Override
//                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                        // after getting the data we are calling on success method
//                        // and inside this method we are checking if the received
//                        // query snapshot is empty or not.
//                        if (!queryDocumentSnapshots.isEmpty()) {
//                            // if the snapshot is not empty we are hiding our
//                            // progress bar and adding our data in a list.
//                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
//                            for (DocumentSnapshot d : list) {
//
//                                // after getting this list we are passing
//                                // that list to our object class.
//                                DataModel dataModel = d.toObject(DataModel.class);
//
//                                // after getting data from Firebase
//                                // we are storing that data in our array list
//                                dataModelArrayList.add(dataModel);
//                            }
//                            // after that we are passing our array list to our adapter class.
//                            GridAdapter adapter = new GridAdapter(getActivity(), dataModelArrayList);
//
//                            // after passing this array list
//                            // to our adapter class we are setting
//                            // our adapter to our list view.
//                            gridadapter.setAdapter(adapter);
//                        } else {
//                            // if the snapshot is empty we are displaying a toast message.
//                            Toast.makeText(getContext(), "No data found in Database", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                // we are displaying a toast message
//                // when we get any error from Firebase.
//                Toast.makeText(getContext(), "Fail to load data..", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }


//        DocumentReference docRef = db.collection("Data").document("2");
//
//// Source can be CACHE, SERVER, or DEFAULT.
//        Source source = Source.CACHE;
//
//// Get the document, forcing the SDK to use the offline cache
//        docRef.get(source).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if (task.isSuccessful()) {
//                    // Document found in the offline cache
//                    DocumentSnapshot document = task.getResult();
//                    Log.d("TAG", "Cached document data: " + document.getData());
//                }
//
//                else {
//                    Log.d("res", "Cached get failed: ", task.getException());
//                }
//            }
//        });



//    private void loadDatainGridView() {
//        // below line is use to get data from Firebase
//        // firestore using collection in android.
//
//        db.collection("Data")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//
//                                //maya added
//                                DataModel dm=new DataModel();
//                                dm.fromMap(document.getData());
//                                dataModelArrayList.add(dm);
//
//
//                                Log.d("TAG", document.getId() + " => " + document.getData());
//                            }
//
//                        //maya added
//
////                            List<QuerySnapshot> list = task.getResult();
////                            for (QuerySnapshot d : list) {
////
////                                // after getting this list we are passing
////                                // that list to our object class.
////                                DataModel dataModel = d.toObject(DataModel.class);
////
////                                // after getting data from Firebase
////                                // we are storing that data in our array list
////                                dataModelArrayList.add(dataModel);
////                            }
//                            // after that we are passing our array list to our adapter class.
//
//
//                            //GridAdapter adapter = new GridAdapter(Nearby.this, dataModelArrayList);
//
//                            //maya added
//                              GridAdapter adapter=new GridAdapter(getContext(),dataModelArrayList);
//                            //maya added
//
//                            // after passing this array list
//                            // to our adapter class we are setting
//                            // our adapter to our list view.
//                            gridadapter.setAdapter(adapter);
//                        } else {
//                            Log.d("TAG", "Error getting documents: ", task.getException());
//                        }
//                    }
//                });
////        db.collection("data").get()
////                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
////
////                    @Override
////                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
////                        // after getting the data we are calling on success method
////                        // and inside this method we are checking if the received
////                        // query snapshot is empty or not.
////                        if (true) {
////                            Log.d("nearby", "Query: here: "+ queryDocumentSnapshots);
////                            // if the snapshot is not empty we are hiding our
////                            // progress bar and adding our data in a list.
////                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
////                            for (DocumentSnapshot d : list) {
////
////                                // after getting this list we are passing
////                                // that list to our object class.
////                                DataModel dataModel = d.toObject(DataModel.class);
////
////                                // after getting data from Firebase
////                                // we are storing that data in our array list
////                                dataModelArrayList.add(dataModel);
////                            }
////                            // after that we are passing our array list to our adapter class.
////
////
////                            //GridAdapter adapter = new GridAdapter(Nearby.this, dataModelArrayList);
////
////                            //maya added
////                              GridAdapter adapter=new GridAdapter(getContext(),dataModelArrayList);
////                            //maya added
////
////                            // after passing this array list
////                            // to our adapter class we are setting
////                            // our adapter to our list view.
////                            gridadapter.setAdapter(adapter);
////                        } else {
////                            // if the snapshot is empty we are displaying a toast message.
////                            Toast.makeText(getContext(), "No data found in Database", Toast.LENGTH_SHORT).show();
////                        }
////                    }
////                }).addOnFailureListener(new OnFailureListener() {
////            @Override
////            public void onFailure(@NonNull Exception e) {
////                // we are displaying a toast message
////                // when we get any error from Firebase.
////                Toast.makeText(getContext(), "Fail to load data..", Toast.LENGTH_SHORT).show();
////            }
////        });
//    }

