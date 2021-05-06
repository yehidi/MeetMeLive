package com.example.meetmelive;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.BitmapFactory;
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

import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.ArrayList;

public class GridAdapter extends ArrayAdapter<DataModel> {
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
        nameTV.setText(dataModel.getName());

        // in below line we are using Picasso to load image
        // from URL in our Image VIew.
        Picasso.get().load(dataModel.getImgUrl()).into(courseIV);

        // below line is use to add item
        // click listener for our item of list view.
        listitemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on the item click on our list view.
                // we are displaying a toast message.
                Toast.makeText(getContext(), "Item clicked is : " + dataModel.getName(), Toast.LENGTH_SHORT).show();



                //added

                    AlertDialog.Builder myAlertBuilder = new AlertDialog.Builder(v.getContext());
                    myAlertBuilder.setTitle("Hi");
                    myAlertBuilder.setMessage("Do You Want To Send A Request ?");



                    myAlertBuilder.setPositiveButton("yes, I Want To Send", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
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
}
