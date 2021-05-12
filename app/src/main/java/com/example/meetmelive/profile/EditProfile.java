package com.example.meetmelive.profile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.example.meetmelive.R;
import com.example.meetmelive.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


public class EditProfile extends Fragment implements RadioGroup.OnCheckedChangeListener {


    EditText name;
    EditText city;
    EditText description;

    String lookingForGender;
    RadioGroup radioGroupLookingFor;

    CircleImageView profilePic;
    ImageButton pic1;
    ImageButton pic2;
    ImageButton pic3;

    Button cancel;
    Button save;

    FirebaseUser user;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_edit_profile, container, false);
        user = FirebaseAuth.getInstance().getCurrentUser();

        profilePic= view.findViewById(R.id.editProfile_profile_im);
        if(User.getInstance().profilePic!=null){
            Picasso.get().load(User.getInstance().profilePic).noPlaceholder().into(profilePic);
        }

        name = view.findViewById(R.id.editProfile_name);
        name.setText(user.getDisplayName());

        city= view.findViewById(R.id.editProfile_city);
        city.setText(User.getInstance().city);

        radioGroupLookingFor = view.findViewById(R.id.editProfile_ratioLookingFor);
        radioGroupLookingFor.setOnCheckedChangeListener((RadioGroup.OnCheckedChangeListener)this);

        description= view.findViewById(R.id.editProfile_aboutme);
        description.setText(User.getInstance().description);

        pic1 = view.findViewById(R.id.editProfile_img1);
        if(User.getInstance().pic1!=null){
            Picasso.get().load(User.getInstance().pic1).noPlaceholder().into(pic1);
        }


        save = view.findViewById(R.id.editProfile_save_btn);
        cancel = view.findViewById(R.id.editProfile_cancel_btn);


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).popBackStack();
            }
        });






        return view;
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch(checkedId) {


            case R.id.editProfile_male_radiobutton:
                lookingForGender = "Male";
                Log.d("TAG", "Looking For Gender is: " + lookingForGender);
                break;

            case R.id.editProfile_female_radiobutton:
                lookingForGender = "Female";
                Log.d("TAG", "Looking For Gender is: " + lookingForGender);
                break;

        }
    }
}