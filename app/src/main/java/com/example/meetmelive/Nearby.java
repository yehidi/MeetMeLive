package com.example.meetmelive;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.meetmelive.adapter.GridAdapter;
import com.example.meetmelive.model.DataModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

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

        // here we are calling a method
        // to load data in our list view.
        loadDatainGridView();
        return view;
    }

    private void loadDatainGridView() {
        // below line is use to get data from Firebase
        // firestore using collection in android.
        db.collection("userProfileData").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        // after getting the data we are calling on success method
                        // and inside this method we are checking if the received
                        // query snapshot is empty or not.
                        if (!queryDocumentSnapshots.isEmpty()) {
                            // if the snapshot is not empty we are hiding our
                            // progress bar and adding our data in a list.
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {
                                // after getting this list we are passing
                                // that list to our object class.
                                DataModel dataModel = d.toObject(DataModel.class);

                                // after getting data from FirebaseHow to Create an Android Ch
                                // we are storing that data in our array list
                                dataModelArrayList.add(dataModel);
                            }
                            // after that we are passing our array list to our adapter class.
                            GridAdapter adapter = new GridAdapter(getContext(), dataModelArrayList);

                            // after passing this array list
                            // to our adapter class we are setting
                            // our adapter to our list view.
                            gridadapter.setAdapter(adapter);
                        } else {
                            // if the snapshot is empty we are displaying a toast message.
                            Toast.makeText(getContext(), "No data found in Database", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // we are displaying a toast message
                // when we get any error from Firebase.
                Toast.makeText(getContext(), "Fail to load data..", Toast.LENGTH_SHORT).show();
            }
        });
    }


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

















    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Nearby() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NearbyFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Nearby newInstance(String param1, String param2) {
        Nearby fragment = new Nearby();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_nearby, container, false);
//    }
}