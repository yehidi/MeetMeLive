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

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.meetmelive.R;
import com.example.meetmelive.login;
import com.example.meetmelive.model.ModelFirebase;
import com.example.meetmelive.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.meetmelive.model.ModelFirebase.setUserAppData;

public class    Profile<OnOption> extends Fragment {

    String userId;
    CircleImageView profilePic;
    TextView username;
    TextView age,city,description;
    ImageSlider imageSlider;//the pictures
    View view;
    Button connection;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        profilePic=view.findViewById(R.id.profile_profile_im);
        username=view.findViewById(R.id.profile_username);
        age=view.findViewById(R.id.profile_age);
        city=view.findViewById(R.id.profile_city);
        description=view.findViewById(R.id.profile_aboutMe);

        if (User.getInstance().profilePic != null){
            Picasso.get().load(User.getInstance().profilePic).noPlaceholder().into(profilePic);
        }
        username.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
        age.setText(User.getInstance().birthday);
        city.setText(User.getInstance().city);
        description.setText(User.getInstance().description);


        //slides pictures
        imageSlider= view.findViewById(R.id.matchProfile_slider);
        List<SlideModel> slideModels = new ArrayList<>();
        if(User.getInstance().pic1!=null){
            slideModels.add(new SlideModel(User.getInstance().pic1));
        }
        if(User.getInstance().pic2!=null){
            slideModels.add(new SlideModel(User.getInstance().pic2));
        }
        if(User.getInstance().pic3!=null){
            slideModels.add(new SlideModel(User.getInstance().pic3));
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
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                mAuth.signOut();
                startActivity(new Intent(getActivity(), login.class));
            }
            default:
               return super.onOptionsItemSelected(item);
        }
    }
}