package com.example.meetmelive.profile;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
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

import com.example.meetmelive.MyApplication;
import com.example.meetmelive.R;
import com.example.meetmelive.model.Model;
import com.example.meetmelive.model.ModelFirebase;
import com.example.meetmelive.model.User;
import com.example.meetmelive.model.UserDao;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.meetmelive.model.ModelFirebase.getExtension;
import static com.example.meetmelive.model.ModelFirebase.getImageFromFireBase;


public class EditProfile extends Fragment implements RadioGroup.OnCheckedChangeListener {


    private static final int PICK_IMAGE=1;

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

    ImageButton editPic1;
    ImageButton editPic2;
    ImageButton editPic3;
    ImageButton deletePic1;
    ImageButton deletePic2;
    ImageButton deletePic3;

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
        if(User.getInstance().getProfileImageUrl()!=null){
            Picasso.get().load(User.getInstance().getProfileImageUrl()).noPlaceholder().into(profilePic);
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
        name.setText(User.getMyUser().getUsername());

        city= view.findViewById(R.id.editProfile_city);
        city.setText(User.getInstance().city);

        radioGroupLookingFor = view.findViewById(R.id.editProfile_ratioLookingFor);
        radioGroupLookingFor.setOnCheckedChangeListener((RadioGroup.OnCheckedChangeListener)this);



        if(User.getInstance().lookingForGender.equals("Male")){
            radioGroupLookingFor.check(R.id.editProfile_male_radiobutton);
        }
        if(User.getInstance().lookingForGender.equals("Female")){
            radioGroupLookingFor.check(R.id.editProfile_female_radiobutton);
        }


        description= view.findViewById(R.id.editProfile_aboutme);
        description.setText(User.getInstance().description);

        pic1 = view.findViewById(R.id.editProfile_img1);
        pic2 = view.findViewById(R.id.editProfile_img2);
        pic3 = view.findViewById(R.id.editProfile_img3);

        editPic1 = view.findViewById(R.id.EditProfile_edit_btn1);
        editPic2 = view.findViewById(R.id.EditProfile_edit_btn2);
        editPic3 = view.findViewById(R.id.EditProfile_edit_btn3);

        editPic1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picType=1;
                Log.d("Photos", "photo: " + picType);
                chooseImageFromGallery();
            }
        });

        editPic2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picType=2;
                chooseImageFromGallery();
                Log.d("Photos", "photo: " + picType);
            }
        });

        editPic3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picType=3;
                chooseImageFromGallery();
                Log.d("pic3","pic3"+User.getInstance().pic3);
            }
        });


        if(User.getInstance().pic1!=null && !User.getInstance().pic1.equals("")){
            Picasso.get().load(User.getInstance().pic1).noPlaceholder().into(pic1);
        }
        if(User.getInstance().pic2!=null && !User.getInstance().pic2.equals("")){
            Picasso.get().load(User.getInstance().pic2).noPlaceholder().into(pic2);
        }
        if(User.getInstance().pic3!=null && !User.getInstance().pic3.equals("")){
            Picasso.get().load(User.getInstance().pic3).noPlaceholder().into(pic3);
        }


        deletePic1=view.findViewById(R.id.EditProfile_delete_btn1);
        deletePic2=view.findViewById(R.id.EditProfile_delete_btn2);
        deletePic3=view.findViewById(R.id.EditProfile_delete_btn3);

        deletePic1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeletePhoto("pic1");
                pic1.setImageURI(null);
            }
        });

        deletePic2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeletePhoto("pic2");
            }
        });

        deletePic3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeletePhoto("pic3");
            }
        });

        Log.d("last updated","time:" +User.getInstance().getLastUpdatedLocation());


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

    private void DeletePhoto(String name) {
        Model.instance.DeleteImage(name);
    }

    private void updateUserProfile() {

        profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(name.getText().toString())
                    .build();
        Log.d("user photo","pic 1 "+User.getInstance().pic1);
        getImageFromFireBase("pic1");
        getImageFromFireBase("pic2");
        getImageFromFireBase("pict3");
        getImageFromFireBase("profileImageUrl");
        Log.d("user photo","pic 1 "+User.getInstance().pic1);

//        String userId,String email,String username, String city,
//                String description,String gender,String lookingForGender,String dateOfBirth,
//                String profileImageUrl, String pic1, String pic2, String pic3,
//                Double latitude,Double longtitude, long lastUpdatedLocation
//



        currentUser= new User(User.getInstance().getUserId(), User.getInstance().getEmail(),name.getText().toString(),city.getText().toString(),
                description.getText().toString(),User.getInstance().getGender(),lookingForGender,
                User.getInstance().getDateOfBirth(), User.getInstance().getProfileImageUrl(),User.getInstance().getPic1(),User.getInstance().getPic2(),User.getInstance().getPic3(),
                User.getInstance().getLatitude(),User.getInstance().getLongtitude(),User.getInstance().getLastUpdatedLocation());

        Model.instance.updateUserProfile(currentUser);

        user.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    NavController navCtrl = Navigation.findNavController(view);
                    navCtrl.popBackStack();
                    navCtrl.popBackStack();
                    //Navigation.findNavController(view).navigate( R.id.profile);
                }
            }
        });

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
        if(data.getData() != null && data != null){
            switch (picType) {
                case  1:// pic1
                    pic1Uri = data.getData();
                    pic1.setImageURI(pic1Uri);
                    SavePic(pic1Uri,"pic1");
                    Log.d("photo result", "URI is: " + pic1);
                    break;

                case  2:// pic2
                    pic2Uri = data.getData();
                    pic2.setImageURI(pic2Uri);
                    SavePic(pic2Uri,"pic2");
                    Log.d("photo result", "URI is: " + pic2);
                    break;

                case  3:// pic3
                    pic3Uri = data.getData();
                    pic3.setImageURI(pic3Uri);
                    SavePic(pic3Uri,"pic3");
                    Log.d("photo result", "URI is: " + pic3);
                    break;

                case  4:// profile Pic
                    profileImageUri = data.getData();
                    profilePic.setImageURI(profileImageUri);
                    SavePic(profileImageUri,"profileImageUrl");
                    Log.d("TAG", "URI is: " + profileImageUri);
                    break;
            }
        }
        else {
            Toast.makeText(getContext(), "No image was selected", Toast.LENGTH_SHORT).show();
        }
    }

    public void SavePic(Uri image,String nameImage){
            StorageReference storageReference = FirebaseStorage.getInstance().getReference("images");

            if (image != null){
                String imageName = User.getInstance().email + "." +nameImage+"."+ getExtension(image);
                final StorageReference imageRef = storageReference.child(imageName);

                UploadTask uploadTask = imageRef.putFile(image);
                uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()){
                            throw task.getException();
                        }
                        return imageRef.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()){
                            ModelFirebase.uploadImageToFirebase(nameImage,task.getResult().toString());
                        }
                        else if (!task.isSuccessful()){
                            Toast.makeText(MyApplication.context, task.getException().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
    }
}

