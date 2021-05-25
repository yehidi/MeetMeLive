package com.example.meetmelive;

import android.content.ClipData;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.meetmelive.model.Model;
import com.example.meetmelive.model.ModelFirebase;
import com.example.meetmelive.model.Notification;
import com.example.meetmelive.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.squareup.picasso.Picasso;

import java.util.HashMap;


public class SendRequestFragment extends Fragment {
    Button button_send;
    Button button_cancel;
    TextView textView_request;
    ImageView userimage;
    View view;
    TextView username;


    int position;
    GridAdapter adapter;


    //maya added
    private FirebaseAuth mAuth;
    private String currentUserId;
    private Query profile;

    //maya added

    // try



    // try

    public SendRequestFragment()  {


        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_send_request, container, false);

        textView_request =view.findViewById(R.id.textView_request);
        button_send = view.findViewById(R.id.button_send);
        button_cancel = view.findViewById(R.id.button_cancel);
        userimage=view.findViewById(R.id.userImg);
        username=view.findViewById(R.id.textView);

        //maya added
        mAuth=FirebaseAuth.getInstance();
        currentUserId=mAuth.getCurrentUser().getUid();
        profile=FirebaseFirestore.getInstance().collectionGroup(currentUserId);
        //maya added


        String Name=SendRequestFragmentArgs.fromBundle(getArguments()).getUsername();

//        Name=getArguments().getString("name");
//        String message=Name;
          username.setText(Name);
//
        String Image=SendRequestFragmentArgs.fromBundle(getArguments()).getUserimage();
////        Name=getArguments().getString("name");
////        String message=Name;
        Picasso.get().load(Image).noPlaceholder().into(userimage);
//            userimage.=Image;


        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).popBackStack();
            }
        });


        //maya added new
        button_send.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
            Notification no=new Notification(User.getInstance());
                String Name=SendRequestFragmentArgs.fromBundle(getArguments()).getUsername();
                String Image=SendRequestFragmentArgs.fromBundle(getArguments()).getUserimage();
//                User user=User.FindUserBy_nameANDimg(Name,Image);







//               user.getMynotificationlist().add(no);

//                com.example.meetmelive.model.Notification notification=new com.example.meetmelive.model.Notification(Name,User.getInstance().email);
//                Navigation.findNavController(v).navigate(R.id.action_sendRequestFragment_to_request_ListActivity);
//                User theSelectedUser=User.getInstance().FindUserBy_nameANDimg(Name,Image);
//                theSelectedUser.Mynotificationlist.add(notification);
            }


        });
        //maya added new



//        DataModel data;
//        data=adapter.getItem(position);
//        name.setText(data.getUsername());
        return view;
    }



//    public TextView get_name(TextView nm)
//    {
//        nm=view.findViewById(R.id.textView);
//        return nm;
//    }

}