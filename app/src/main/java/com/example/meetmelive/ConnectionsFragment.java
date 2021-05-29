package com.example.meetmelive;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ConnectionsFragment extends Fragment {
        private RecyclerView myFrirendsList;
        private DatabaseReference FriendsReference;
        private DatabaseReference UsersReference;
        private FirebaseAuth mAuth;

        String online_user_id;
        private View myMainView;

    public ConnectionsFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        myMainView= inflater.inflate(R.layout.fragment_connections, container, false);
        myFrirendsList = (RecyclerView) myMainView.findViewById(R.id.friends_list);

        mAuth=FirebaseAuth.getInstance();
        online_user_id=mAuth.getCurrentUser().getUid();

        FriendsReference= FirebaseDatabase.getInstance().getReference().child("Friends").child(online_user_id);
        UsersReference=FirebaseDatabase.getInstance().getReference().child("Users");

        myFrirendsList.setLayoutManager(new LinearLayoutManager(getContext()));

        // Inflate the layout for this fragment
        return myMainView;
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<Friends, FriendsViewHolder> firebaseRecyclerAdapter
                = new FirebaseRecyclerAdapter<Friends, FriendsViewHolder>
                (
                        Friends.class,
                        R.layout.item_grid,
                        FriendsViewHolder.class,
                        FriendsReference
                )

         {
            @Override
            protected void populateViewHolder(FriendsViewHolder viewHolder, Friends model, int position) {
                viewHolder.setDate(model.getDate());

                String list_user_id= getRef(position).getKey();

                UsersReference.child(list_user_id).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String userName=dataSnapshot.child("user_name").getValue().toString();
                        String thumbImage = dataSnapshot.child("user_thumb_image").getValue().toString();
                        FriendsViewHolder.setUserName(userName);
                       // FriendsViewHolder.setThumbImage(thumbImage, getContext());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        };
        myFrirendsList.setAdapter(firebaseRecyclerAdapter);
    }

    public static class FriendsViewHolder extends RecyclerView.ViewHolder{
        static View mView;
        public FriendsViewHolder(@NonNull View itemView) {
            super(itemView);
            mView= itemView;
        }

//        public static void setThumbImage(final String thumbImage, final Context ctx) {
//            @SuppressLint("WrongViewCast") final CircleImageView thumb_image = (CircleImageView) mView.findViewById(R.id.idIVimage);
//
//            Picasso.with(ctx).load(thumbImage).networkPolicy(NetworkPolicy.OFFLINE)
//                    .into(thumb_image, new Callback() {
//                        @Override
//                        public void onSuccess() {
//
//                        }
//
//                        @Override
//                        public void onError(Exception e) {
//                            Picasso.with(ctx).load(thumbImage).placeholder(R.drawable.user);
//                        }
//                    });
//        }

        public void setDate(String date){
            TextView sinceFriendsDate = (TextView) mView.findViewById(R.id.idTVtext);
            sinceFriendsDate.setText(date);
        }

        public static void setUserName(String userName){
            TextView userNameDisplay= (TextView) mView.findViewById(R.id.idTVtext);
            userNameDisplay.setText(userName);
        }
    }
}