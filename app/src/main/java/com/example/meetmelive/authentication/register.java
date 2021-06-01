package com.example.meetmelive.authentication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;

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
import android.widget.Toast;

import com.example.meetmelive.CalculateAge;
import com.example.meetmelive.MainActivity;
import com.example.meetmelive.R;
import com.example.meetmelive.Utils;
import com.example.meetmelive.model.ModelFirebase;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class register extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener{

    EditText username;
    EditText email;
    EditText password;
    EditText city;
    String gender, lookingForGender;
    RadioGroup radioGroupGender, radioGroupLookingFor;
    Button register, choosePhoto;
    EditText description;

    ImageView profilePic;

    Uri profileImageUri = null;
    DatePicker dateOfBirth;
    CalculateAge calculateAge;
    int age, age2;

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
        dateOfBirth = findViewById(R.id.register_birthDate);
        choosePhoto = findViewById(R.id.register_btnChoosePhoto);
        profilePic = findViewById(R.id.register_profileImageView);
        description = findViewById(R.id.register_about);



        int age = getAge(dateOfBirth.getYear(),dateOfBirth.getMonth(),dateOfBirth.getDayOfMonth());
        Log.d("REGISTER", "AGE IS " + age);

        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_PICK);
//                intent.setType("image/*");
//                startActivityForResult(intent, 1);
                Utils.chooseImageFromGallery(register.this);
            }
        });

        choosePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Utils.chooseImageFromGallery(register.this);

                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 1);

                int age = getAge(dateOfBirth.getYear(),dateOfBirth.getMonth(),dateOfBirth.getDayOfMonth());
                Log.d("TAG", "BIRTHDAY IS " + age);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.YEAR, dateOfBirth.getYear());
                cal.set(Calendar.MONTH, dateOfBirth.getMonth());
                cal.set(Calendar.DAY_OF_MONTH, dateOfBirth.getDayOfMonth());
                Date dateOfBirth2 = cal.getTime();
                String strDateOfBirth = dateFormatter.format(dateOfBirth2);
                Log.d("TAG", "strDATEOFBIRTH IS " + strDateOfBirth);

                ModelFirebase.registerUserAccount(email.getText().toString(), username.getText().toString(),
                            password.getText().toString(), city.getText().toString(),description.getText().toString(), gender, lookingForGender, strDateOfBirth, profileImageUri, new ModelFirebase.Listener<Boolean>() {

                                @Override
                                public void onComplete() {
                                            startActivity(new Intent(register.this, MainActivity.class));
                                            finish();
                                }

                                @Override
                                public void onFail() {

                                }
                            });
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