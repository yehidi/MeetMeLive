package com.example.meetmelive;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meetmelive.model.User;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.squareup.picasso.Picasso;

public class AdapterUsers extends FirestoreRecyclerAdapter<User, AdapterUsers.UserHolder> {

    final private OnItemClickListener listener;

    public AdapterUsers(@NonNull FirestoreRecyclerOptions<User> options, OnItemClickListener listener) {
        super(options);
        this.listener = listener;
    }

    @Override
    protected void onBindViewHolder(@NonNull UserHolder holder, int position, @NonNull User model) {
        holder.userName.setText(model.getUsername());
        if (model.getProfileImageUrl() != null) {
            Picasso.get().load(model.getProfileImageUrl()).noPlaceholder().into(holder.userProfileImage);
        }

        //CalculateAge calculateAge = new CalculateAge(model.getDateOfBirth());
        //int age = calculateAge.getAge();
        //holder.age.setText(String.valueOf(age));
    }
    public interface OnItemClickListener {
        void onItemClick(int position);
        void onItemClick2(int position);

    }

    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_chats, parent, false);
        return new UserHolder(v, listener);
    }

    class UserHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView userProfileImage;
        TextView userName;
        //TextView userNameChats;
        //TextView age;

        public UserHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            itemView.setOnClickListener(this);
            userProfileImage=itemView.findViewById(R.id.list_row_chats_image_view);
            userName = itemView.findViewById(R.id.list_row_chats_username);
            //userNameChats=itemView.findViewById(R.id.user_name);
            //userNameChats.setText();
            //age=itemView.findViewById(R.id.list_row_chats_age);

            itemView.findViewById(R.id.list_row_connections_message).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    listener.onItemClick(position);
                }
            });

            itemView.findViewById(R.id.list_row_connections_unmatch).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    listener.onItemClick2(position);
                }
            });
        }
        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            listener.onItemClick(position);
            //image
//            String image= User.getInstance().getProfileImageUrl();
//            RequestsDirections.ActionRequestsToConnectionsFragment action=RequestsDirections.actionRequestsToConnectionsFragment(image);
//            Navigation.findNavController(v).navigate(action);
            //image
        }
    }
}
