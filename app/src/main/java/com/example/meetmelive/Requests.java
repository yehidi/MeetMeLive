package com.example.meetmelive;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.meetmelive.adapter.RequestAdapter;
import com.example.meetmelive.model.User;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import org.jetbrains.annotations.NotNull;

public class Requests extends Fragment {

    Button acceptBtn, declineBtn;
    RecyclerView list;
    User currentUser;
    //    UserViewModel viewModel;
    private FirebaseFirestore db =  FirebaseFirestore.getInstance();
    private CollectionReference notebookRef = db.collection("userProfileData")
            .document(User.getInstance().getEmail()).collection("friendRequests");
    private RequestAdapter adapter;

//    @Override
//    public void onAttach(@NonNull Context context) {
//        super.onAttach(context);
//        viewModel = new ViewModelProvider(this).get(UserViewModel.class);
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_requests, container, false);

        Log.d("TAG", "User getInstance is " + User.getMyUser());

        acceptBtn = view.findViewById(R.id.list_row_requests_accept);
        declineBtn = view.findViewById(R.id.list_row_requests_decline);

        setUpRecyclerView();

        list = view.findViewById(R.id.requestsRecyclerView);
        list.setHasFixedSize(true);
        list.setLayoutManager(new LinearLayoutManager(getContext()));
        list.setAdapter(adapter);

        return view;
    }

    private void setUpRecyclerView() {
        Query query = notebookRef;
        FirestoreRecyclerOptions<User> options = new FirestoreRecyclerOptions.Builder<User>()
                .setQuery(query, User.class)
                .build();

        adapter = new RequestAdapter(options, new RequestAdapter.OnItemClickListener() {

            ///////////////////////Accept button
            @Override
            public void acceptOnItemClick(int position) {
                Log.d("TAG", "POSITION IS " + adapter.getItem(position).getCity());

                User userClicked = new User(
                        adapter.getItem(position).getUserId(),
                        adapter.getItem(position).getEmail(),
                        adapter.getItem(position).getUsername(),
                        adapter.getItem(position).getCity(),
                        adapter.getItem(position).getDescription(),
                        adapter.getItem(position).getGender(),
                        adapter.getItem(position).getLookingForGender(),
                        adapter.getItem(position).getDateOfBirth(),
                        adapter.getItem(position).getProfileImageUrl(),
                        adapter.getItem(position).getPic1(),
                        adapter.getItem(position).getPic2(),
                        adapter.getItem(position).getPic3(),
                        adapter.getItem(position).getLatitude(),
                        adapter.getItem(position).getLongtitude(),
                        adapter.getItem(position).getLastUpdatedLocation());

                currentUser = new User(User.getInstance().getUserId(), User.getInstance().getEmail(), User.getInstance().getUsername(), User.getInstance().getDateOfBirth(),
                        User.getInstance().getDescription(), User.getInstance().getGender(), User.getInstance().getLookingForGender(),
                        User.getInstance().getCity(), User.getInstance().getProfileImageUrl(), User.getInstance().getPic1(), User.getInstance().getPic2(), User.getInstance().getPic3(),
                        User.getInstance().getLatitude(), User.getInstance().getLongtitude(), User.getInstance().getLastUpdatedLocation());

                //Create Connections in the user is connect(the user that accept the request)
                db.collection("userProfileData").document(User.getInstance().getEmail()).collection("connections")
                        .document(userClicked.getEmail()).set(userClicked).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()){

                            //Create Connections in the user that ask for request
                            db.collection("userProfileData").document(userClicked.getEmail()).collection("connections")
                                    .document(User.getInstance().getEmail()).set(currentUser).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull @NotNull Task<Void> task) {

                                }
                            });

                            //Delete the request from friendRequests collection
                            db.collection("userProfileData").document(User.getInstance().getEmail())
                                    .collection("friendRequests").document(userClicked.getEmail()).delete()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                        }
                                    });
                            Toast.makeText(getContext(), "Connection has created", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

            ///////////////////////Decline button
            @Override
            public void declineOnItemClick(int position) {
                Log.d("TAG", "Email IS " + adapter.getItem(position).getEmail());

                User user = new User(
                        adapter.getItem(position).getUserId(),
                        adapter.getItem(position).getEmail(),
                        adapter.getItem(position).getUsername(),
                        adapter.getItem(position).getCity(),
                        adapter.getItem(position).getDescription(),
                        adapter.getItem(position).getGender(),
                        adapter.getItem(position).getLookingForGender(),
                        adapter.getItem(position).getDateOfBirth(),
                        adapter.getItem(position).getProfileImageUrl(),
                        adapter.getItem(position).getPic1(),
                        adapter.getItem(position).getPic2(),
                        adapter.getItem(position).getPic3(),
                        adapter.getItem(position).getLatitude(),
                        adapter.getItem(position).getLongtitude(),
                        adapter.getItem(position).getLastUpdatedLocation());

                //Delete the request from friendRequests collection
                db.collection("userProfileData").document(User.getInstance().getEmail())
                        .collection("friendRequests").document(user.getEmail()).delete()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                            }
                        });
                Toast.makeText(getContext(), "Request has Declined", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}