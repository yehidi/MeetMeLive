package com.example.meetmelive;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meetmelive.model.Notification;
import com.example.meetmelive.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class NotificationAdapter extends ArrayAdapter<Notification> {

    View view;

    public NotificationAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Notification> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        View v=convertView;
        if (v == null) {
            v = LayoutInflater.from(getContext()).inflate(R.layout.list_row_requests, parent, false);
        }
        Notification notification=getItem(position);
//
//        TextView name=view.findViewById(R.id.list_row_chats_username);
//        TextView age=view.findViewById(R.id.list_row_chats_age);
//        ImageView image=view.findViewById(R.id.list_row_chats_image_view);
//
//        name.setText(User.getInstance().name);
//        age.setText(User.getInstance().age);
//        Picasso.get().load(User.getInstance().profilePic).into(image);


        return v;
    }

//    public NotificationAdapter(Context context, ArrayList<com.example.meetmelive.model.Notification> notificationList) {
//        this.context = context;
//        this.notificationList=notificationList;
//    }
//
//    @NonNull
//    @Override
//    public HolderNotification onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//         view= LayoutInflater.from(context).inflate(R.layout.list_row_requests,parent,false);
//
//         //maya added new new new
//
//        TextView name=view.findViewById(R.id.list_row_chats_username);
//        TextView age=view.findViewById(R.id.list_row_chats_age);
//        ImageView image=view.findViewById(R.id.list_row_chats_image_view);
//
//        name.setText(User.getInstance().name);
//        age.setText(User.getInstance().age);
//        Picasso.get().load(User.getInstance().profilePic).into(image);
//
//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                String name=User.getInstance().name;
//                Integer age=User.getInstance().age;
//                String image=User.getInstance().profilePic;
//
//                SendRequestFragmentDirections.ActionSendRequestFragmentToNotificationFragment2 action=
//                        SendRequestFragmentDirections.actionSendRequestFragmentToNotificationFragment2(name,image,age);
//
//                Navigation.findNavController(v).navigate(action);
//            }
//        });
//
//
//
//
//
//
//        //maya added new new new
//        return new HolderNotification(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull HolderNotification holder, int position) {
//        //get and set data to views
//
//        //get data
//        Notification notification1=notificationList.get(position);
//        String Name=notification1.senderName;
//        String id=notification1.senderId;
//        Integer age=notification1.senderAge;
//        String image=notification1.senderImage;
//        String Email=notification1.senderEmail;
//
//
//        //set to views
//        holder.Name.setText(Name);
//        holder.Age.setText(age);
//
//        try{
//            Picasso.get().load(image).placeholder(R.drawable.user).into(holder.Image);
//        }
//        catch (Exception e){
//            holder.Image.setImageResource(R.drawable.user);
//
//        }
//    }
//
//    @Override
//    public int getItemCount() {
//        return notificationList.size();
//    }
//
//
//    class HolderNotification extends RecyclerView.ViewHolder{
//
//        ImageView Image;
//        TextView Name;
//        TextView Age;
//
//        public HolderNotification(@NonNull View itemView) {
//            super(itemView);
//            Image=itemView.findViewById(R.id.list_row_chats_image_view);
//            Name=itemView.findViewById(R.id.list_row_chats_username);
//            Age=itemView.findViewById(R.id.list_row_chats_age);
//        }
//    }
//
//
//    //maya added new new new


}
