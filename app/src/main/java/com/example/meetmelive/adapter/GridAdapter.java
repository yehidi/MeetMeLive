package com.example.meetmelive.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.example.meetmelive.Nearby;
import com.example.meetmelive.R;
import com.example.meetmelive.model.DataModel;
import com.example.meetmelive.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class GridAdapter extends ArrayAdapter<DataModel> {

    Context context;
    public GridAdapter(@NonNull Context context,  ArrayList<DataModel> dataModalArrayList) {
        super(context,0, dataModalArrayList);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listitemView = convertView;
        GridAdapter holder = null;

        if (listitemView == null && getContext()!= null) {
            listitemView = LayoutInflater.from(context).inflate(R.layout.item_grid, parent, false);

        }

        DataModel dataModel = getItem(position);
        TextView nameTV = listitemView.findViewById(R.id.idTVtext);
        ImageView courseIV = listitemView.findViewById(R.id.idIVimage);

        nameTV.setText(dataModel.getUsername());
        if (dataModel.getProfileImageUrl()!=null) {
            Picasso.get().load(dataModel.getProfileImageUrl()).into(courseIV);
        }

        listitemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG", "Item clicked is : " + dataModel.getUsername());

                AlertDialog myAlertBuilder = new AlertDialog.Builder(v.getContext()).create();
                Picasso.get().load(dataModel.getProfileImageUrl()).into(courseIV);
                myAlertBuilder.setTitle("Send Request");
                myAlertBuilder.setMessage(Html.fromHtml("Are you sure you want to send<br>a request to <b>" + dataModel.getUsername() + "</b>?"));
                myAlertBuilder.setButton(AlertDialog.BUTTON_POSITIVE, "Yes, I want to send", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("@ GRID @", "Username CLICK IS " + dataModel.getUsername());
                        Log.d("@ GRID @", "Email CLICK IS " + dataModel.getEmail());

                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        User user = new User();
                        user = User.getInstance();
                        Log.d("@ GRID @", "user is: " + user);

                        db.collection("userProfileData").document(dataModel.getEmail()).collection("friendRequests")
                                .document(User.getInstance().getEmail()).set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful())
                                {
                                }
                            }
                        });

//                        db.collection("userProfileData").document(User.getInstance().getEmail()).collection("like")
//                                .document(dataModel.getEmail()).set(user).add(new OnCompleteListener<Void>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Void> task) {
//                                if (task.isSuccessful())
//                                {
//
//                                }
//                            }
//                        });

                        Toast.makeText(v.getContext(),"Your Request has Sent from " + User.getInstance().getUsername() + "to " + dataModel.getUsername() ,Toast.LENGTH_SHORT).show();
                    }
                });

                myAlertBuilder.setButton(AlertDialog.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                ImageView image = new ImageView(getContext());
                if (dataModel.getProfileImageUrl() != null)
                    Picasso.get().load(dataModel.getProfileImageUrl()).noPlaceholder().into(image);
                image.setAdjustViewBounds(true);
                image.setMaxHeight(300);
                image.setMaxWidth(300);
                myAlertBuilder.setView(image);
                myAlertBuilder.show();

                Button btnPositive = myAlertBuilder.getButton(AlertDialog.BUTTON_POSITIVE);
                Button btnNegative = myAlertBuilder.getButton(AlertDialog.BUTTON_NEGATIVE);

                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) btnPositive.getLayoutParams();
                layoutParams.weight = 5;
                btnPositive.setLayoutParams(layoutParams);
                btnNegative.setLayoutParams(layoutParams);
            }
        });
        return listitemView;
    }
}

