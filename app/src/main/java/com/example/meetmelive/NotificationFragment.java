package com.example.meetmelive;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.meetmelive.model.Notification;
import com.example.meetmelive.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class NotificationFragment extends Fragment {

    RecyclerView notificationRv;
    ArrayList<Notification> notificationList;
    FirebaseFirestore db;
    View view;
    RecyclerView.LayoutManager layoutManager;

    TextView senderName;
    TextView senderAge;
    ImageView senderImage;


    public NotificationFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_notification, container, false);

        notificationRv=view.findViewById(R.id.recycler);
        notificationRv.hasFixedSize();
        layoutManager=new LinearLayoutManager(this.getContext());
        notificationRv.setLayoutManager(layoutManager);

        notificationList= new ArrayList<>();
        db = FirebaseFirestore.getInstance();



        loadDatainRecyclerView();
        return view;
    }

    private void loadDatainRecyclerView() {
        db.collection("userProfileData").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>()
         {
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
                        Notification notificationModel = d.toObject(Notification.class);

                        // after getting data from Firebase
                        // we are storing that data in our array list
                        notificationList.add(notificationModel);
                    }
                    // after that we are passing our array list to our adapter class.
                    NotificationAdapter adapter = new NotificationAdapter(getContext(),0, notificationList);
//
//                    // after passing this array list
//                    // to our adapter class we are setting
//                    // our adapter to our list view.
 //                   notificationRv.setAdapter();
                } else {
                    // if the snapshot is empty we are displaying a toast message.
                    Toast.makeText(getContext(), "No data found in Database", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener(){
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Fail to load data..", Toast.LENGTH_SHORT).show();
            }
        });
    }


}