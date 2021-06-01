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

import com.example.meetmelive.adapter.RequestAdapter;
import com.example.meetmelive.model.User;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class Requests extends Fragment {

    Button acceptBtn, declineBtn;
    RecyclerView list;
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

        acceptBtn = view.findViewById(R.id.list_row_requests_accept);
        declineBtn = view.findViewById(R.id.list_row_requests_decline);

        setUpRecyclerView();

        list = view.findViewById(R.id.requestsRecyclerView);
        list.setHasFixedSize(true);
        list.setLayoutManager(new LinearLayoutManager(getContext()));
        list.setAdapter(adapter);

//        viewModel.getData().observe(getViewLifecycleOwner(), userUpdateObserver);

        return view;
    }

    private void setUpRecyclerView() {
        Query query = notebookRef;
        FirestoreRecyclerOptions<User> options = new FirestoreRecyclerOptions.Builder<User>()
                .setQuery(query, User.class)
                .build();

        adapter = new RequestAdapter(options, new RequestAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Log.d("TAG", "POSITION IS " + adapter.getItem(position).getCity());

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

                acceptBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        db.collection("userProfileData").document(User.getInstance().getEmail()).collection("connections")
                                .document(user.getEmail()).set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if (task.isSuccessful()){
                                    db.collection("userProfileData").document(User.getInstance().getEmail())
                                            .collection("friendRequests").document(user.getEmail()).delete()
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {

                                                }
                                            });
                                }
                            }
                        });
                    }
                });

                declineBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        db.collection("userProfileData").document(User.getInstance().getEmail())
                                .collection("friendRequests").document(user.getEmail()).delete()
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                    }
                                });
                    }
                });
            }
        });

//        list.setHasFixedSize(true);
//        list.setLayoutManager(new LinearLayoutManager(getContext()));
//        list.setAdapter(adapter);
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

//    Observer<List<User>> userUpdateObserver = new Observer<List<User>>() {
//        @Override
//        public void onChanged(List<User> recipeArrayList) {
//            List<User> data = new LinkedList<User>();
//            for (User user: recipeArrayList)
//                data.add(0, user);
//
//            recipeArrayList = data;
//            List<User> finalUserArrayList = recipeArrayList;
//            adapter = new RequestAdapter(getContext(), recipeArrayList, new RequestAdapter.OnItemClickListener() {
//                @Override
//                public void onItemClick(int position) {
//                    User use = finalUserArrayList.get(position);
//                    String recipeId = use.getUser_id();
////                    AllPostsDirections.ActionAllPostsToRecipeDetails direction = AllPostsDirections.actionAllPostsToRecipeDetails(recipeId);
////                    Navigation.findNavController(getActivity(), R.id.mainactivity_navhost).navigate(direction);
//                    Log.d("TAG", "row was clicked " + recipeId);
//                }
//            });
//            list.setLayoutManager(new LinearLayoutManager(getContext()));
//            list.setAdapter(adapter);
//        }
//    };
}