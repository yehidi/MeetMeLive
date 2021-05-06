package com.example.meetmelive;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.meetmelive.model.Model;
import com.example.meetmelive.model.Notification;

import java.util.List;

public class Request_ListActivity extends AppCompatActivity {
    RecyclerView list;
    List<Notification> data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request__list);


        list=findViewById(R.id.requestlist_recyclerv);
        list.hasFixedSize();

        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        list.setLayoutManager(layoutManager);

        data=Model.instance.getAllNotifications();

        MyAdapter adapter=new MyAdapter();
        list.setAdapter(adapter);
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView username;
        TextView age;
        ImageView image;
        Button confirm;
        Button delete;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            username= itemView.findViewById(R.id.list_row_chats_username);
            age=itemView.findViewById(R.id.list_row_chats_age);
            image=itemView.findViewById(R.id.list_row_chats_image_view);
            confirm=itemView.findViewById(R.id.list_row_chats_btn_view_profile);
            delete=itemView.findViewById(R.id.button5);
        }
    }
    class MyAdapter extends RecyclerView.Adapter<MyViewHolder>{
        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            //added
           View view=getLayoutInflater().inflate(R.layout.list_row_requests,null);
            MyViewHolder holder=new MyViewHolder(view);

            //added
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
         Notification notification=data.get(position);
         holder.username.setText(notification.Username);

        }

        @Override
        public int getItemCount() {
             return data.size();
        }
    }
}