package com.example.meetmelive;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.meetmelive.model.ModelFirebase;

import java.util.List;
import java.util.Locale;


public class Search extends Fragment implements LocationListener {

    public static final int DEFAULT_UPDATE_INTERVAL= 5; // need to be 30


    Button button_location;
    Button button_search;
    TextView textView_location;
    LocationManager locationManager;
    //added
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        view = inflater.inflate(R.layout.fragment_search, container, false);
        //added

        textView_location =view.findViewById(R.id.text_location);
        button_location = view.findViewById(R.id.button_location);
        button_search = view.findViewById(R.id.button_search);
        //Runtime permissions
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){

            // added
            ActivityCompat.requestPermissions(getActivity(),new String[]{
                    // added
                    Manifest.permission.ACCESS_FINE_LOCATION
            },100);
        }


        button_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //create method
                getLocation();
            }
        });


//added
        button_search.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Navigation.findNavController(v).navigate(R.id.action_search_to_Nearby);
            }

        });
//added


        getLocation();

        return view;
    }

    @SuppressLint("MissingPermission")
    private void getLocation() {
        try {
            //fragment
            locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000* DEFAULT_UPDATE_INTERVAL, 0, new LocationListener() {
                @Override
                public void onLocationChanged(@NonNull Location location) {
                    Log.d("location","changed location: "+location.getLatitude()+ " longtitude: "+ location.getLongitude());
                    ModelFirebase.updateLocation(location);
                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void onLocationChanged(Location location) {
        Toast.makeText(getContext(), ""+location.getLatitude()+","+location.getLongitude(), Toast.LENGTH_SHORT).show();
        try {
            Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
            String address = addresses.get(0).getAddressLine(0);

            textView_location.setText(address);

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}