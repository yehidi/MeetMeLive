package com.example.meetmelive.profile;

import android.app.Notification;
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
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.meetmelive.CalculateAge;
import com.example.meetmelive.NotificationHelper;
import com.example.meetmelive.R;
import com.example.meetmelive.login;
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
    int age;
    NotificationHelper notifcation;

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
        Log.d("Profile", "Date Of Birth is " + User.getInstance().getDateOfBirth() +  "userId is" + userId);

        username.setText(User.getInstance().getUsername());
        city.setText(User.getInstance().getCity());

        String[] splitDOB = User.getInstance().getDateOfBirth().split("-");
        Log.d("Profile", "splitDOB is" + splitDOB);
        age = getAge(Integer.parseInt(splitDOB[2]),Integer.parseInt(splitDOB[0]),Integer.parseInt(splitDOB[1]));
        Log.d("TAG", "AGE IS " + age);
        dateOfBirth.setText(String.valueOf(age));

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
                Navigation.findNavController(view).navigate(R.id.action_Profile_to_requests);
            }
        });

        //slides pictures
        imageSlider= view.findViewById(R.id.matchProfile_slider);
        List<SlideModel> slideModels = new ArrayList<>();
        if(User.getInstance().getPic1()!=null && !User.getInstance().getPic1().equals("")){
            slideModels.add(new SlideModel(User.getInstance().getPic1()));
        }
        if(User.getInstance().getPic2()!=null  && !User.getInstance().getPic2().equals("")){
            slideModels.add(new SlideModel(User.getInstance().getPic2()));
        }
        if(User.getInstance().getPic3()!=null  && !User.getInstance().getPic3().equals("")){
            slideModels.add(new SlideModel(User.getInstance().getPic3()));
        }
        imageSlider.setImageList(slideModels,true);

        notifcation = new NotificationHelper(getContext());

        sendNotification();

        return view;
    }

    private void sendNotification() {
        NotificationCompat.Builder nb = notifcation.getChannel1Notification("BLA BLA", "WOW WOW");
        notifcation.getManager().notify(1, nb.build());
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
                startActivity(new Intent(getActivity(), login.class));
            }
            default:
               return super.onOptionsItemSelected(item);
        }
    }

    public int getAge(int year, int month, int day)
    {
        Calendar dateOfBirth = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dateOfBirth.set(year, month, day);

        int age = today.get(Calendar.YEAR) - dateOfBirth.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dateOfBirth.get(Calendar.DAY_OF_YEAR))
        {
            age--;
        }

        return age;
    }

}