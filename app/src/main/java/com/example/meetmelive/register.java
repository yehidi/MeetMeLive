package com.example.meetmelive;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;

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

import com.example.meetmelive.model.ModelFirebase;

public class register extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener{

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

        choosePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.chooseImageFromGallery(register.this);
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
                                startActivity(new Intent(register.this, MainActivity.class));
                                finish();
                            }

                            @Override
                            public void onFail() {
                                Log.d("TAG", "FAILED");
                            }
                        });

                Log.d("TAG", "Gender is: " + dateB.getText().toString());

            }
        });
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