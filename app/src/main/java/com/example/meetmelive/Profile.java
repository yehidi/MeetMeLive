package com.example.meetmelive;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.meetmelive.model.ModelFirebase;
import com.example.meetmelive.model.User;
import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.meetmelive.model.ModelFirebase.setUserAppData;

public class Profile<OnOption> extends Fragment {

    String userId;
    CircleImageView profilePic;
    TextView username, city, description, dateOfBirth;
    ImageSlider imageSlider;//the pictures
    View view;
    Button connection, test;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        profilePic=view.findViewById(R.id.profile_profile_im);
        if (User.getInstance().getProfileImageUrl() != null)
            Picasso.get().load(User.getInstance().getProfileImageUrl()).noPlaceholder().into(profilePic);
        username=view.findViewById(R.id.profile_username);
        dateOfBirth = view.findViewById(R.id.matchProfile_age);
        city = view.findViewById(R.id.matchProfile_city);
        description = view.findViewById(R.id.matchProfile_aboutMe);
        test = view.findViewById(R.id.profile_test);

        userId = FirebaseAuth.getInstance().getUid();
        Log.d("Profile", "Username is " + User.getInstance().getUsername() +  "userId is" + userId);

        username.setText(User.getInstance().getUsername());
        city.setText(User.getInstance().getCity());

        Log.d("Profile", "dateOfBirth is " + User.getInstance().getDateOfBirth());
//        CalculateAge cal = new CalculateAge(User.getInstance().getDateOfBirth());
//        int age = cal.getAge();
//        Log.d("TAG", "AGE IS " + age);
//        dateOfBirth.setText(String.valueOf(age));

        description.setText(User.getInstance().getDescription());


        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                Map<String,Object> data = new HashMap<>();
                User user = new User();
                user = User.getInstance();

                Log.d("Profile", "User is " + user);

                db.collection("userProfileData").document(User.getInstance().getEmail()).collection(userId)
                        .document("RequestSent").set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())
                        {

                        }
                    }
                });


            }
        });

        connection = view.findViewById(R.id.profile_unmatch_btn);
        connection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                mAuth.signOut();
                startActivity(new Intent(getActivity(),login.class));
            }
        });

        //slides pictures
        imageSlider= view.findViewById(R.id.matchProfile_slider);
        List<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel("https://i.pinimg.com/originals/cf/8a/11/cf8a11b44a748c4ce286fb020f920ada.png"));
        slideModels.add(new SlideModel("https://i.pinimg.com/originals/46/da/e5/46dae512e375bee2664a025507da8795.jpg"));
        slideModels.add(new SlideModel("https://i.pinimg.com/564x/23/37/db/2337db3ed61500b113e0db86d0fbf9b8.jpg"));
        imageSlider.setImageList(slideModels,true);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.profile_menu,menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.editProfileFragment:{
                Navigation.findNavController(view).navigate(R.id.action_Profile_to_editProfileFragment);
                return true;
            }
            case R.id.menu_signOut: {
                ModelFirebase.signOut();
                LoginManager.getInstance().logOut();
                startActivity(new Intent(getActivity(),login.class));
            }
            default:
               return super.onOptionsItemSelected(item);
        }
    }
}