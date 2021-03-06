package com.example.meetmelive.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meetmelive.CalculateAge;
import com.example.meetmelive.R;
import com.example.meetmelive.model.User;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class RequestAdapter extends FirestoreRecyclerAdapter<User, RequestAdapter.UserHolder> {

    final private OnItemClickListener listener;

    public RequestAdapter(@NonNull FirestoreRecyclerOptions<User> options, OnItemClickListener listener) {
        super(options);
        this.listener = listener;
    }

    @Override
    protected void onBindViewHolder(@NonNull UserHolder holder, int position, @NonNull User model) {
        holder.nickname.setText(model.getUsername() + ", ");
        if (model.getProfileImageUrl() != null) {
            Picasso.get().load(model.getProfileImageUrl()).noPlaceholder().into(holder.profilePic);
        }

        CalculateAge calculateAge = new CalculateAge(model.getDateOfBirth());
        int age = calculateAge.getAge();
        holder.age.setText(String.valueOf(age));
    }

    public interface OnItemClickListener {
        void acceptOnItemClick(int position);
        void declineOnItemClick(int position);

    }


    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_requests, parent, false);
        return new UserHolder(v, listener);
    }

    class UserHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CircleImageView profilePic;
        TextView nickname;
        TextView age;

        public UserHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            itemView.setOnClickListener(this);
            profilePic=itemView.findViewById(R.id.list_row_chats_image_view);
            nickname = itemView.findViewById(R.id.list_row_chats_username);
            age=itemView.findViewById(R.id.list_row_chats_age);

            itemView.findViewById(R.id.list_row_requests_accept).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    listener.acceptOnItemClick(position);
                }
            });

            itemView.findViewById(R.id.list_row_requests_decline).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    listener.declineOnItemClick(position);
                }
            });
        }

        @Override
        public void onClick(View v) {

        }
    }
}