package com.example.meetmelive;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile<OnOption> extends Fragment {

    String userId;
    CircleImageView profilePic;
    TextView username;
    Button connections;
    Button editProfileBtn;
    Button signoutBtn;
    ImageSlider imageSlider;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        profilePic=view.findViewById(R.id.EditProfile_profile_im);

        username=view.findViewById(R.id.matchProfile_username);
        //username.setText(FirebaseAuth.getInstance().getCurretnuser().getDisplayNamr);




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
            default:
               return super.onOptionsItemSelected(item);
        }
    }
}