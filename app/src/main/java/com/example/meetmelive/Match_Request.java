package com.example.meetmelive;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Match_Request extends AppCompatActivity {

    //Lidor Match Request
    Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match__request);






        //Lidor Match Reeusst
        button= findViewById(R.id.button_alert);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder myAlertBuilder = new AlertDialog.Builder(Match_Request.this);
                myAlertBuilder.setTitle("Hi");
                myAlertBuilder.setMessage("Do You Want To Send A Request ?");
                myAlertBuilder.setPositiveButton("yes, I Want To Send", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(Match_Request.this,"your Request sent" ,Toast.LENGTH_SHORT).show();
                    }
                });

                myAlertBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(Match_Request.this,"you clicked no", Toast.LENGTH_SHORT).show();
                    }
                });

                myAlertBuilder.show();

            }
        });

    }
}