package com.example.meetmelive.authentication;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.example.meetmelive.CalenderActivity;

import com.example.meetmelive.MainActivity;
import com.example.meetmelive.R;
import com.example.meetmelive.Utils;
import com.example.meetmelive.model.ModelFirebase;
import com.example.meetmelive.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class register extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener{

    EditText username;
    EditText email;
    EditText password;
    EditText city;
    EditText description;
    String gender, lookingForGender;
    RadioGroup radioGroupGender, radioGroupLookingFor;
    Button register, choosePhoto;
    String currentLocation;
    DatePicker dateOfBirth;

    ImageView profilePic;

    Uri profileImageUri = null;
    String pic1 = null;
    String pic2 = null;
    String pic3 = null;
    //try


    //try
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = findViewById(R.id.register_activity_username);
        email = findViewById(R.id.register_activity_email_edit_text);
        password = findViewById(R.id.register_activity_password);
        city = findViewById(R.id.register_frag_city);
        radioGroupGender = findViewById(R.id.register_radiogroupGender);
        radioGroupLookingFor = findViewById(R.id.register_radiogroupLookingFor);
        register = findViewById(R.id.register_activity_register_btn);
        radioGroupGender.setOnCheckedChangeListener((RadioGroup.OnCheckedChangeListener) this);
        radioGroupLookingFor.setOnCheckedChangeListener((RadioGroup.OnCheckedChangeListener)this);
        dateOfBirth=findViewById(R.id.register_birthDate);
        choosePhoto = findViewById(R.id.register_btnChoosePhoto);
        profilePic = findViewById(R.id.register_profileImageView);
        description= findViewById(R.id.register_about);

        int age = getAge(dateOfBirth.getYear(),dateOfBirth.getMonth(),dateOfBirth.getDayOfMonth());
        Log.d("REGISTER", "AGE IS " + age);
        User.getInstance().birthday=(String.valueOf(age));





        choosePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.chooseImageFromGallery(register.this);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat dateFormatter = new SimpleDateFormat("MM-dd-yyyy");
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.YEAR, dateOfBirth.getYear());
                cal.set(Calendar.MONTH, dateOfBirth.getMonth());
                cal.set(Calendar.DAY_OF_MONTH, dateOfBirth.getDayOfMonth());
                Date dateOfBirth2 = cal.getTime();
                String strDateOfBirth = dateFormatter.format(dateOfBirth2);
                Log.d("TAG", "strDATEOFBIRTH IS " + strDateOfBirth);

                ModelFirebase.registerUserAccount(username.getText().toString(),
                        password.getText().toString(),
                        email.getText().toString(),
                        gender,
                        lookingForGender,
                        strDateOfBirth,
                        currentLocation,
                        description.getText().toString(),
                        city.getText().toString(),
                        profileImageUri,
                        pic1,pic2,pic3,
                        new ModelFirebase.Listener<Boolean>() {
                            @Override
                            public void onComplete() {
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(username.getText().toString())
                                        .build();
                                user.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()){
                                            startActivity(new Intent(register.this, MainActivity.class));
                                            finish();
                                        }
                                    }
                                });
                            }

                            @Override
                            public void onFail() {
                                Log.d("TAG", "FAILED");
                            }
                        });
            }
        });


    }
    public int getAge(int year, int month, int day)
    {
        Calendar dateOfBirth = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dateOfBirth.set(year, month, day);

        int age = today.get(Calendar.YEAR) - dateOfBirth.get(Calendar.YEAR);
        Log.d("getAge", "age is " + age);
        if (today.get(Calendar.DAY_OF_YEAR) < dateOfBirth.get(Calendar.DAY_OF_YEAR))
        {
            age--;
        }

        Log.d("getAge", "age is " + age);
        return age;
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
            Toast.makeText(this, "No image was selected", Toast.LENGTH_SHORT).show();
        }
    }
}




















//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.navigation.Navigation;
//
//import android.content.Intent;
//import android.net.Uri;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.RadioGroup;
//import android.widget.Toast;
//
//import com.example.meetmelive.model.ModelFirebase;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.auth.UserProfileChangeRequest;
//
//public class register extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener{
//
//    EditText username;
//    EditText email;
//    EditText password;
//    EditText city;
//    String gender, lookingForGender;
//    RadioGroup radioGroupGender, radioGroupLookingFor;
//    Button register, choosePhoto;
//    EditText dateB;
//
//    ImageView profilePic;
//    Uri profileImageUri = null;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_register);
//
//        username = findViewById(R.id.register_activity_username);
//        email = findViewById(R.id.register_activity_email_edit_text);
//        password = findViewById(R.id.register_activity_password);
//        city = findViewById(R.id.register_frag_city);
//        radioGroupGender = findViewById(R.id.register_radiogroupGender);
//        radioGroupLookingFor = findViewById(R.id.register_radiogroupLookingFor);
//        register = findViewById(R.id.register_activity_register_btn);
//        radioGroupGender.setOnCheckedChangeListener((RadioGroup.OnCheckedChangeListener) this);
//        radioGroupLookingFor.setOnCheckedChangeListener((RadioGroup.OnCheckedChangeListener)this);
//        dateB = findViewById(R.id.register_birthDate);
//        choosePhoto = findViewById(R.id.register_btnChoosePhoto);
//        profilePic = findViewById(R.id.register_profileImageView);
//
//        choosePhoto.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Utils.chooseImageFromGallery(register.this);
//            }
//        });
//
//        register.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ModelFirebase.registerUserAccount(username.getText().toString(),
//                        password.getText().toString(),
//                        email.getText().toString(), gender, lookingForGender, profileImageUri, new ModelFirebase.Listener<Boolean>() {
//                            @Override
//                            public void onComplete() {
//                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
//                                        .setDisplayName(username.getText().toString())
//                                        .build();
//                                user.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                    @Override
//                                    public void onComplete(@NonNull Task<Void> task) {
//                                        if (task.isSuccessful()){
//                                            startActivity(new Intent(register.this, MainActivity.class));
//                                            finish();
//                                        }
//                                    }
//                                });
//                            }
//
//                            @Override
//                            public void onFail() {
//                                Log.d("TAG", "FAILED");
//                            }
//                        });
//
//                Log.d("TAG", "Gender is: " + dateB.getText().toString());
//
//            }
//        });
//    }
//
//    @Override
//    public void onCheckedChanged(RadioGroup radioGroup, int i) {
//        switch(i) {
//            case R.id.register_male_radiobutton:
//                gender = "Male";
//                Log.d("TAG", "Gender is: " + gender);
//                break;
//            case R.id.register_female_radiobutton:
//                gender = "Female";
//                Log.d("TAG", "Gender is: " + gender);
//                break;
//
//            case R.id.register_lookingfor_male_radiobutton:
//                lookingForGender = "Male";
//                Log.d("TAG", "Looking For Gender is: " + lookingForGender);
//                break;
//
//            case R.id.register_lookingfor_female_radiobutton:
//                lookingForGender = "Female";
//                Log.d("TAG", "Looking For Gender is: " + lookingForGender);
//                break;
//
//        }
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if(data.getData() != null && data != null){
//            profileImageUri = data.getData();
//            profilePic.setImageURI(profileImageUri);
//            Log.d("TAG", "URI is: " + profileImageUri);
//
//        }
//        else {
//            Toast.makeText(this, "No image was selected", Toast.LENGTH_SHORT).show();
//        }
//    }
//}