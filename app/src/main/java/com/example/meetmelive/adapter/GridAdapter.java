package com.example.meetmelive.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
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
    //maya added


    //maya added


    public GridAdapter(@NonNull Context context,  ArrayList<DataModel> dataModalArrayList) {
        super(context,0, dataModalArrayList);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // below line is use to inflate the
        // layout for our item of list view.
        View listitemView = convertView;
        if (listitemView == null) {
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.item_grid, parent, false);
        }

        // after inflating an item of listview item
        // we are getting data from array list inside
        // our modal class.
        DataModel dataModel = getItem(position);

        // initializing our UI components of list view item.
        TextView nameTV = listitemView.findViewById(R.id.idTVtext);
        ImageView courseIV = listitemView.findViewById(R.id.idIVimage);

        // after initializing our items we are
        // setting data to our view.
        // below line is use to set data to our text view.
        nameTV.setText(dataModel.getUsername());

        // in below line we are using Picasso to load image
        // from URL in our Image VIew.
        Picasso.get().load(dataModel.getProfileImageUrl()).into(courseIV);

        // below line is use to add item
        // click listener for our item of list view.
        listitemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on the item click on our list view.
                // we are displaying a toast message.
                Log.d("TAG", "Item clicked is : " + dataModel.getUsername());

//                Toast.makeText(getContext(), "Item clicked is : " + dataModel.getUsername(), Toast.LENGTH_SHORT).show();



                //added

                AlertDialog.Builder myAlertBuilder = new AlertDialog.Builder(v.getContext());

                //maya added new
                Picasso.get().load(dataModel.getProfileImageUrl()).into(courseIV);

                //maya added new

                myAlertBuilder.setTitle("Hi");
                myAlertBuilder.setMessage("Do You Want To Send A Request ?");

                myAlertBuilder.setPositiveButton("yes, I Want To Send", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("TAG", "NAME CLICK IS " + dataModel.getUsername());

                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        User user = new User();
                        user = User.getInstance();

                        db.collection("userProfileData").document(dataModel.getEmail()).collection("friendRequests")
                                .document(User.getInstance().getEmail()).set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful())
                                {

                                }
                            }
                        });


                        Toast.makeText(v.getContext(),"your Request sent" ,Toast.LENGTH_SHORT).show();

                    }
                });

                myAlertBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(v.getContext(),"you clicked no", Toast.LENGTH_SHORT).show();

                    }
                });

                myAlertBuilder.show();

                //added
            }
        });
        return listitemView;
    }

}//package com.example.meetmelive.adapters;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.meetmelive.R;
//import com.example.meetmelive.model.User;
//import com.squareup.picasso.Picasso;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class userAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
//    final private OnItemClickListener listener;
//    Context context;
//    List<User> userArrayList;
//
//    public RecipesAdapter(Context context, List<User> recipeArrayList, OnItemClickListener onClickListener) {
//        this.context = context;
//        this.userArrayList = recipeArrayList;
//        this.listener = onClickListener;
//    }
//
//    public interface OnItemClickListener {
//        void onItemClick(int position);
//    }
//
//    @NonNull
//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View rootView = LayoutInflater.from(context).inflate(R.layout.list_row, parent, false);
//        return new RecipesViewHolder(rootView);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//
//        User user = userArrayList.get(position);
//        RecipesViewHolder viewHolder = (RecipesViewHolder) holder;
//        // getImageFromFireBase(recipe);
//        viewHolder.profilePic.setImageResource(R.drawable.ic_round_person_grey);
//        viewHolder.nickname.setText(recipe.getUserName());
//        viewHolder.recipeTitle.setText(recipe.getTitleRecipe());
//        viewHolder.category.setText(recipe.getCategory());
//        viewHolder.postImg.setImageResource(R.drawable.icon_upload_image);
//        if (user.getImageUrl() != null) {
//            Picasso.get().load(user.getImageUrl()).placeholder(R.drawable.recipe_placeholder).into(viewHolder.postImg);
//        }
//
//        if( user.getUserPic()!=null){
//            Picasso.get().load(user.getUserPic()).placeholder(R.drawable.ic_round_person_grey).into(viewHolder.profilePic);
//        }
//    }
//
//
//    @Override
//    public int getItemCount() {
//        return userArrayList.size();
//    }
//
//    class RecipesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
//
//        CircleImageView profilePic;
//        TextView nickname;
//        TextView recipeTitle;
//        TextView category;
//        ImageView postImg;
//
//        public RecipesViewHolder(@NonNull View itemView) {
//            super(itemView);
//            itemView.setOnClickListener(this);
//
//            profilePic=itemView.findViewById(R.id.detailsprofile_profile_im);
//            nickname = itemView.findViewById(R.id.listRow_nickname);
//            recipeTitle=itemView.findViewById(R.id.listRow_titleRec);
//            category= itemView.findViewById(R.id.listRow_category);
//            postImg=itemView.findViewById(R.id.listRow_img);
//        }
//
//        @Override
//        public void onClick(View v) {
//            int position = getAdapterPosition();
//            listener.onItemClick(position);
//        }
//    }
//
//}
//
