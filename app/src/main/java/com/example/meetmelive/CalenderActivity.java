package com.example.meetmelive;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import com.example.meetmelive.profile.Profile;

public class CalenderActivity extends AppCompatActivity {

    CalendarView calendarView;
    TextView myDate;
    Button save;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);

        calendarView=findViewById(R.id.calenderView);
        myDate=findViewById(R.id.myDate);
        save=findViewById(R.id.calender_btn_save);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String date=(month+1)+"/"+dayOfMonth +"/" +year;
                myDate.setText(date);
            }
        });

        Profile fragment=new Profile<>();
        getSupportFragmentManager().beginTransaction().add(R.id.Profile,fragment,Profile.TAG).commit();

        save.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String date=myDate.toString();

            }
        });
    }


}