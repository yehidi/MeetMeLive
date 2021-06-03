package com.example.meetmelive.profile;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.meetmelive.CalculateAge;
import com.example.meetmelive.MyApplication;
import com.example.meetmelive.R;
import com.example.meetmelive.authentication.login;
import com.example.meetmelive.model.Model;
import com.example.meetmelive.model.ModelFirebase;
import com.example.meetmelive.model.User;
import com.facebook.login.LoginManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class    Profile<OnOption> extends Fragment {

    String userId;
    CircleImageView profilePic;
    TextView username;
    TextView dateOfBirth,city,description;
    ImageSlider imageSlider;//the pictures
    View view;
    Button connection;
    int age;

    User user;
    FirebaseUser firebaseuser ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        firebaseuser = FirebaseAuth.getInstance().getCurrentUser();

        user= new User(User.getInstance().getUserId(), User.getInstance().getEmail(),User.getInstance().getUsername(),User.getInstance().getCity(),
                User.getInstance().getDescription(),User.getInstance().getGender(),User.getInstance().getLookingForGender(),
                User.getInstance().getDateOfBirth(), User.getInstance().getProfileImageUrl(),User.getInstance().getPic1(),User.getInstance().getPic2(),User.getInstance().getPic3(),
                User.getInstance().getLatitude(),User.getInstance().getLongtitude(),User.getInstance().getLastUpdatedLocation());


        profilePic=view.findViewById(R.id.profile_profile_im);
        username=view.findViewById(R.id.profile_username);
        dateOfBirth=view.findViewById(R.id.profile_age);
        city=view.findViewById(R.id.profile_city);
        description=view.findViewById(R.id.profile_aboutMe);



        Log.d("Profile", "Username is " + User.getInstance().getUsername() +  "userId is" +firebaseuser.getUid());

        String[] splitDOB = User.getInstance().getDateOfBirth().split("-");
        Log.d("Profile", "splitDOB is" + splitDOB);
        age = getAge(Integer.parseInt(splitDOB[2]),Integer.parseInt(splitDOB[0]),Integer.parseInt(splitDOB[1]));
        Log.d("TAG", "AGE IS " + age);





        //  ModelFirebase.trying();

        if(User.getInstance().getProfileImageUrl()!=null){
            Picasso.get().load(User.getInstance().getProfileImageUrl()).noPlaceholder().into(profilePic);
        }

        Model.instance.getUser(User.getInstance().getEmail(),new Model.GetUserListener() {
            @Override
            public void onComplete(User user) {

                username.setText(user.getUsername());
                dateOfBirth.setText(String.valueOf(age));
                city.setText(user.getCity());
                description.setText(user.getDescription());
            }
        });



        //slides pictures
        imageSlider= view.findViewById(R.id.matchProfile_slider);
        List<SlideModel> slideModels = new ArrayList<>();
        if(User.getInstance().getPic1()!=null && !User.getInstance().getPic1().equals("")){
            slideModels.add(new SlideModel(User.getInstance().pic1));
        }
        if(User.getInstance().getPic2()!=null  && !User.getInstance().getPic2().equals("")){
            slideModels.add(new SlideModel(User.getInstance().getPic2()));
        }
        if(User.getInstance().getPic3()!=null  && !User.getInstance().getPic3().equals("")){
            slideModels.add(new SlideModel(User.getInstance().getPic3()));
        }
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
            case R.id.SignOut:{
                ModelFirebase.signOut();
                LoginManager.getInstance().logOut();
                startActivity(new Intent(getActivity(),login.class));
            }
            case R.id.DeleteAccount:{
                Model.instance.deleteUser(user, new Model.DeleteUserListener() {
                    @Override
                    public void onComplete() {
                        firebaseuser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull @NotNull Task<Void> task) {

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull @NotNull Exception e) {
                                Toast.makeText(MyApplication.context, "Failed To Delete Account", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

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