package com.example.meetmelive;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class RegisterFragment extends Fragment implements RadioGroup.OnCheckedChangeListener {

    EditText username;
    EditText email;
    EditText password;
    EditText city;
    String gender, lookingforGender;
    RadioGroup radioGroupGender, radioGroupLookingFor;


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

        radioGroupGender.setOnCheckedChangeListener((RadioGroup.OnCheckedChangeListener) this);
        radioGroupLookingFor.setOnCheckedChangeListener((RadioGroup.OnCheckedChangeListener)this);

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
                lookingforGender = "Male";
                Log.d("TAG", "Looking For Gender is: " + lookingforGender);
                break;

            case R.id.register_lookingfor_female_radiobutton:
                lookingforGender = "Female";
                Log.d("TAG", "Looking For Gender is: " + lookingforGender);
                break;

        }
    }
}