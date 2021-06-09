package com.example.meetmelive.chat;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meetmelive.R;
import com.example.meetmelive.model.User;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;


public class ConnectionsFragment extends Fragment {
    private RecyclerView myFrirendsList;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference FriendsReference;
    private CollectionReference UsersReference;
    private FirebaseAuth mAuth;
    private ConnectionsAdapter adapter;
    String online_user_id;
    private View myMainView;
    ImageView userprofilePic;
    Spinner spinner;
    private Button message,unmatch;
    User currentUser;

    public ConnectionsFragment() {
        // Required empty public constructor

    }


    @SuppressLint("WrongViewCast")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        myMainView = inflater.inflate(R.layout.fragment_connections, container, false);
        myFrirendsList = (RecyclerView) myMainView.findViewById(R.id.friends_list);

        mAuth = FirebaseAuth.getInstance();
        online_user_id = mAuth.getCurrentUser().getUid();

        FriendsReference = db.collection("userProfileData")
                .document(User.getInstance().getEmail()).collection("connections");
        UsersReference = db.collection("userProfileData")
                .document(User.getInstance().getEmail()).collection("users");

        setUpRecyclerView();

        myFrirendsList.setLayoutManager(new LinearLayoutManager(getContext()));
        myFrirendsList.setHasFixedSize(true);
        myFrirendsList.setAdapter(adapter);
        spinner =myMainView.findViewById(R.id.list_row_connections_progressBar);
        //spinner.setEnabled();
        message=myMainView.findViewById(R.id.list_row_connections_message);
        unmatch=myMainView.findViewById(R.id.list_row_connections_unmatch);
//להחזיר כשהצאט יעבוד
//        message.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Navigation.findNavController(view).navigate(R.id.action_connectionsFragment_to_chatActivity);
//            }
//        });



        //image
        //userprofilePic= myMainView.findViewById(R.id.list_row_chats_image_view);
        //String image= ConnectionsFragmentArgs.fromBundle(getArguments()).getUserprofilePic();
        //Picasso.get().load(image).noPlaceholder().into(userprofilePic);
        //image
        // Inflate the layout for this fragment
        return myMainView;
    }

    private void setUpRecyclerView() {
        Query query = FriendsReference;
        FirestoreRecyclerOptions<User> options = new FirestoreRecyclerOptions.Builder<User>()
                .setQuery(query, User.class)
                .build();

        adapter = new ConnectionsAdapter(options, new ConnectionsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //Log.d("TAG", "POSITION IS " + adapter.getItem(position).getCity());

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


                currentUser = new User(User.getInstance().getUserId(), User.getInstance().getEmail(), User.getInstance().getUsername(), User.getInstance().getDateOfBirth(),
                        User.getInstance().getDescription(), User.getInstance().getGender(), User.getInstance().getLookingForGender(),
                        User.getInstance().getCity(), User.getInstance().getProfileImageUrl(), User.getInstance().getPic1(), User.getInstance().getPic2(), User.getInstance().getPic3(),
                        User.getInstance().getLatitude(), User.getInstance().getLongtitude(), User.getInstance().getLastUpdatedLocation());

                db.collection("connections").document(User.getInstance().getEmail()).collection("chats")
                        .document(user.getEmail()).set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {
                            db.collection("userProfileData").document(User.getInstance().getEmail())
                                    .collection("connections").document(user.getEmail()).set(user) //delete()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                        }
                                    });
                            Toast.makeText(getContext(), "Chats  has created", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                unmatch.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        db.collection("userProfileData").document(User.getInstance().getEmail())
                                .collection("connections").document(currentUser.getEmail()).delete()
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                    }
                                });
                        Toast.makeText(getContext(), "Connection has deleted", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
//        FirebaseRecyclerAdapter<Friends, FriendsViewHolder> firebaseRecyclerAdapter
//                = new FirebaseRecyclerAdapter<Friends, FriendsViewHolder>
//                (
//                        Friends.class,
//                        R.layout.item_grid,
//                        FriendsViewHolder.class,
//                        FriendsReference
//                )
//
//        {
//            @Override
//            protected void populateViewHolder(FriendsViewHolder viewHolder, Friends model, int position) {
//                viewHolder.setDate(model.getDate());
//
//                String list_user_id= getRef(position).getKey();
//
//                UsersReference.child(list_user_id).addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        String userName=dataSnapshot.child("user_name").getValue().toString();
//                        String thumbImage = dataSnapshot.child("user_thumb_image").getValue().toString();
//                        FriendsViewHolder.setUserName(userName);
//                        // FriendsViewHolder.setThumbImage(thumbImage, getContext());
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//            }
//        };
        //     myFrirendsList.setAdapter(firebaseRecyclerAdapter);
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
//    public static class FriendsViewHolder extends RecyclerView.ViewHolder{
//        static View mView;
//        public FriendsViewHolder(@NonNull View itemView) {
//            super(itemView);
//            mView= itemView;
//        }
//
////        public static void setThumbImage(final String thumbImage, final Context ctx) {
////            @SuppressLint("WrongViewCast") final CircleImageView thumb_image = (CircleImageView) mView.findViewById(R.id.idIVimage);
////
////            Picasso.with(ctx).load(thumbImage).networkPolicy(NetworkPolicy.OFFLINE)
////                    .into(thumb_image, new Callback() {
////                        @Override
////                        public void onSuccess() {
////
////                        }
////
////                        @Override
////                        public void onError(Exception e) {
////                            Picasso.with(ctx).load(thumbImage).placeholder(R.drawable.user);
////                        }
////                    });
////        }
//
//
//        public static void setUserName(String userName){
//            TextView userNameDisplay= (TextView) mView.findViewById(R.id.idTVtext);
//            userNameDisplay.setText(userName);
//        }
//    }
//}