package com.example.meetmelive;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.meetmelive.model.ModelFirebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class register extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener{

    EditText username;
    EditText email;
    EditText password;
    EditText city;
    EditText description;
    String gender, lookingForGender;
    RadioGroup radioGroupGender, radioGroupLookingFor;
    Button register, choosePhoto;
    EditText dateB;
    String currentLocation;
    ImageView profilePic;

    Uri profileImageUri = null;
    Uri pic1 = null;
    Uri pic2 = null;
    Uri pic3 = null;

    //Chat
    ChatActivity chat;
    //Chat

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
        dateB = findViewById(R.id.register_birthDate);
        choosePhoto = findViewById(R.id.register_btnChoosePhoto);
        profilePic = findViewById(R.id.register_profileImageView);
        description= findViewById(R.id.register_about);

        choosePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.chooseImageFromGallery(register.this);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("register","birth date:"+dateB.getText().toString());
                ModelFirebase.registerUserAccount(username.getText().toString(),
                        password.getText().toString(),
                        email.getText().toString(),
                        gender,
                        lookingForGender,
                        dateB.getText().toString(),
                        currentLocation,
                        description.toString(),
                        city.toString(),
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

        //Chat
        // Load chat room contents
        //chat.displayChatMessages();
        //Chat
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