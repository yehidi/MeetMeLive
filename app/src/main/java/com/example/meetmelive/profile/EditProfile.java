package com.example.meetmelive.profile;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.meetmelive.MainActivity;
import com.example.meetmelive.R;
import com.example.meetmelive.Utils;
import com.example.meetmelive.model.Model;
import com.example.meetmelive.model.User;
import com.example.meetmelive.register;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


public class EditProfile extends Fragment implements RadioGroup.OnCheckedChangeListener {


    EditText name;
    EditText city;
    EditText description;

    String lookingForGender;
    RadioGroup radioGroupLookingFor;

    CircleImageView profilePic;
    ImageButton editProfilePic;
    ImageView pic1;
    ImageView pic2;
    ImageView pic3;

    Uri profileImageUri = null;
    Uri pic1Uri = null;
    Uri pic2Uri = null;
    Uri pic3Uri = null;

    int picType=0;

    Button cancel;
    Button save;

    UserProfileChangeRequest profileUpdates;
    FirebaseUser user;
    User currentUser;
    View view;

    static int REQUEST_CODE = 1;

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

        editProfilePic=view.findViewById(R.id.EditProfile_ediProfilePic);
        editProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picType=4;
                chooseImageFromGallery();
            }
        });

        name = view.findViewById(R.id.editProfile_name);
        name.setText(user.getDisplayName());

        city= view.findViewById(R.id.editProfile_city);
        city.setText(User.getInstance().city);

        radioGroupLookingFor = view.findViewById(R.id.editProfile_ratioLookingFor);
        radioGroupLookingFor.setOnCheckedChangeListener((RadioGroup.OnCheckedChangeListener)this);

        description= view.findViewById(R.id.editProfile_aboutme);
        description.setText(User.getInstance().description);

        pic1 = view.findViewById(R.id.editProfile_img1);
        pic2 = view.findViewById(R.id.editProfile_img2);
        pic3 = view.findViewById(R.id.editProfile_img3);


        pic1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picType=1;
                Log.d("Photos", "photo: " + picType);
                chooseImageFromGallery();
            }
        });

        pic2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picType=2;
                chooseImageFromGallery();
                Log.d("Photos", "photo: " + picType);
            }
        });

        pic3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picType=3;
                Log.d("Photos", "photo: " + picType);
                chooseImageFromGallery();
            }
        });
        if(User.getInstance().pic1!=null){
            Picasso.get().load(User.getInstance().pic1).noPlaceholder().into(pic1);
        }
        if(User.getInstance().pic2!=null){
            Picasso.get().load(User.getInstance().pic2).noPlaceholder().into(pic2);
        }
        if(User.getInstance().pic3!=null){
            Picasso.get().load(User.getInstance().pic3).noPlaceholder().into(pic3);
        }


        save = view.findViewById(R.id.editProfile_save_btn);
        cancel = view.findViewById(R.id.editProfile_cancel_btn);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUserProfile();
            }
        });

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

    private void updateUserProfile() {

//        if (isExist){
//            profileUpdates = new UserProfileChangeRequest.Builder()
//                    .setDisplayName(name.getText().toString())
//                    .build();
//            BitmapDrawable drawable = (BitmapDrawable) profilePic.getDrawable();
//            Model.instance.uploadImage(drawable.getBitmap(), user.getEmail(), new Model.UploadImageListener() {
//                @Override
//                public void onComplete(String url) {
//                    if(url==null){
//                        displayFailedError();
//                    }
//                    else{
//                        currentUser= new User( user.getUid(),newFullName.getText().toString(),user.getEmail(),url);
//                        Model.instance.updateUserProfile(currentUser);
//
//                    }
//
//                }
//            });
//
//        }
//        else{
            currentUser= new User( user.getUid(),name.getText().toString(),description.toString(),lookingForGender,city.toString(),profilePic.toString(),pic1.toString(),pic2.toString(),pic3.toString());
            Model.instance.updateUserProfile(currentUser);
            profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(name.getText().toString())
                    .build();

//        }
        user.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    NavController navCtrl = Navigation.findNavController(view);
                    navCtrl.popBackStack();
                    navCtrl.popBackStack();
                }
            }
        });

    }

    public void displayFailedError() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Operation Failed");
        builder.setMessage("Saving image failed, please try again later...");
        builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    void chooseImageFromGallery() {
        try {
            Intent openGalleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
            openGalleryIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");

            startActivityForResult(openGalleryIntent, REQUEST_CODE);
        } catch (Exception e) {
            Toast.makeText(getActivity(), "New post recipe Page: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("photo result", "hereeeeee: " );
        if(data.getData() != null && data != null){
            switch (picType) {
                case  1:// pic1
                    pic1Uri = data.getData();
                    pic1.setImageURI(pic1Uri);
                    Log.d("photo result", "URI is: " + pic1);
                    break;

                case  2:// pic2
                    pic2Uri = data.getData();
                    pic2.setImageURI(pic2Uri);
                    Log.d("photo result", "URI is: " + pic2);
                    break;

                case  3:// pic3
                    pic3Uri = data.getData();
                    pic3.setImageURI(pic3Uri);
                    Log.d("photo result", "URI is: " + pic3);
                    break;

                case  4:// profile Pic
                    profileImageUri = data.getData();
                    profilePic.setImageURI(profileImageUri);
                    Log.d("TAG", "URI is: " + profileImageUri);
                    break;
            }

        }
        else {
            Toast.makeText(getContext(), "No image was selected", Toast.LENGTH_SHORT).show();
        }
    }


}