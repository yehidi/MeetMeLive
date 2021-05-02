package com.example.meetmelive;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.meetmelive.model.ModelFirebase;

public class RegisterFragment extends Fragment implements RadioGroup.OnCheckedChangeListener {

    EditText username;
    EditText email;
    EditText password;
    EditText city;
    String gender, lookingForGender;
    RadioGroup radioGroupGender, radioGroupLookingFor;
    Button register, choosePhoto;
    EditText dateB;

    ImageView profilePic;
    Uri profileImageUri = null;

//    @Override
//    public void onAttach(@NonNull Context context) {
//        super.onAttach(context);
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_register, container, false);

        username = view.findViewById(R.id.register_activity_username);
        email = view.findViewById(R.id.register_activity_email_edit_text);
        password = view.findViewById(R.id.register_activity_password);
        city = view.findViewById(R.id.register_frag_city);
        radioGroupGender = view.findViewById(R.id.register_radiogroupGender);
        radioGroupLookingFor = view.findViewById(R.id.register_radiogroupLookingFor);
        register = view.findViewById(R.id.register_activity_register_btn);
        radioGroupGender.setOnCheckedChangeListener((RadioGroup.OnCheckedChangeListener) this);
        radioGroupLookingFor.setOnCheckedChangeListener((RadioGroup.OnCheckedChangeListener)this);
        dateB = view.findViewById(R.id.register_birthDate);
        choosePhoto = view.findViewById(R.id.register_btnChoosePhoto);
        profilePic = view.findViewById(R.id.register_profileImageView);

        choosePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.chooseImageFromGallery(RegisterFragment.this);
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ModelFirebase.registerUserAccount(username.getText().toString(),
                        password.getText().toString(),
                        email.getText().toString(), gender, lookingForGender, profileImageUri, new ModelFirebase.Listener<Boolean>() {

                            @Override
                            public void onComplete() {
                                Navigation.findNavController(view).popBackStack();
                            }

                            @Override
                            public void onFail() {
                                Log.d("TAG", "FAILED");
                            }
                        });

                Log.d("TAG", "Gender is: " + dateB.getText().toString());

            }
        });
        return view;
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch(i) {
            case R.id.register_male_radiobutton:
                gender = "Male";
                Log.d("TAG", "Gender is: " + gender);
                break;
            case R.id.register_female_radiobutton:
                gender = "Female";
                Log.d("TAG", "Gender is: " + gender);
                break;

            case R.id.register_lookingfor_male_radiobutton:
                lookingForGender = "Male";
                Log.d("TAG", "Looking For Gender is: " + lookingForGender);
                break;

            case R.id.register_lookingfor_female_radiobutton:
                lookingForGender = "Female";
                Log.d("TAG", "Looking For Gender is: " + lookingForGender);
                break;

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(data.getData() != null && data != null){
            profileImageUri = data.getData();
            profilePic.setImageURI(profileImageUri);
            Log.d("TAG", "URI is: " + profileImageUri);

        }
        else {
            Toast.makeText(getActivity(), "No image was selected", Toast.LENGTH_SHORT).show();
        }
    }
}