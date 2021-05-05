package com.example.meetmelive;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MatchProfile extends Fragment {

    String userId;
    CircleImageView profilePic;
    TextView username;
    Button connections;
    Button editProfileBtn;
    Button signoutBtn;
    ImageSlider imageSlider;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_match_profile, container, false);

        profilePic=view.findViewById(R.id.profile_profile_im);

        username=view.findViewById(R.id.profile_username);
        //username.setText(FirebaseAuth.getInstance().getCurretnuser().getDisplayNamr);

        //slides pictures
        imageSlider= view.findViewById(R.id.matchProfile_slider);
        List<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel("https://i.pinimg.com/originals/cf/8a/11/cf8a11b44a748c4ce286fb020f920ada.png","picture1"));
        slideModels.add(new SlideModel("https://i.pinimg.com/originals/46/da/e5/46dae512e375bee2664a025507da8795.jpg","picture2"));
        slideModels.add(new SlideModel("https://i.pinimg.com/564x/23/37/db/2337db3ed61500b113e0db86d0fbf9b8.jpg","picture3"));
        imageSlider.setImageList(slideModels,true);
        return view;
    }
}