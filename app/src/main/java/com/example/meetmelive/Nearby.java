package com.example.meetmelive;

import android.os.Bundle;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import com.example.meetmelive.adapter.GridAdapter;
import com.example.meetmelive.model.DataModel;
import com.example.meetmelive.model.Model;
import com.example.meetmelive.model.ModelFirebase;
import com.example.meetmelive.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import static android.content.ContentValues.TAG;

public class Nearby extends Fragment {

    // creating a variable for our
    // grid view, arraylist and
    // firebase Firestore.
    GridView gridadapter;
    ArrayList<DataModel> dataModelArrayList;
    ArrayList<DataModel> dataModelArrayList2;
    String one;
    int Age1,Age2;
    int minPrefer=0,maxPrefer=0;

    User user = new User();

    FirebaseFirestore db;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        //  return inflater.inflate(R.layout.fragment_nearby, container, false);  activiygrid
        view = inflater.inflate(R.layout.fragment_nearby, container, false);

        // below line is use to initialize our variables.
        gridadapter = view.findViewById(R.id.idGVCourses);
        dataModelArrayList = new ArrayList<>();
        dataModelArrayList2 = new ArrayList<>();
        // initializing our variable for firebase
        // firestore and getting its instance.
        db = FirebaseFirestore.getInstance();

        setUser(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        Log.d("NearBy", "!!!!!!!!!!!!!!!!!!!");

        // here we are calling a method
        // to load data in our list view.
        loadDatainGridView(Age1,Age2);
        return view;
    }

    private void setUser(final String email){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        db.collection("userProfileData").document(email).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){ ;

                   user.userId = (String) task.getResult().get("userId");
                    user.getInstance().setEmail(email);
                    User.getInstance().setUsername((String) task.getResult().get("username"));
                    User.getInstance().setCity((String) task.getResult().get("city"));
                    User.getInstance().setDescription((String) task.getResult().get("description"));
                    User.getInstance().setGender((String) task.getResult().get("gender"));
                    User.getInstance().setLookingForGender((String) task.getResult().get("lookingForGender"));
                    User.getInstance().setDateOfBirth((String) task.getResult().get("dateOfBirth"));
                    User.getInstance().setProfileImageUrl((String) task.getResult().get("profileImageUrl"));
                    User.getInstance().setPic1((String) task.getResult().get("pic1"));
                    User.getInstance().setPic2((String) task.getResult().get("pic2"));
                    User.getInstance().setPic3((String) task.getResult().get("pic3"));
                    User.getInstance().setLatitude((double) task.getResult().get("latitude"));
                    User.getInstance().setLongtitude((double) task.getResult().get("longtitude"));

                    Log.d("SetUser", "******************");

                    loadDatainGridView(Age1,Age2);
                    // User.getInstance().lookingForAge= (String) task.getResult().get("looking For Age");
                }
            }
        });
    }
    public void loadDatainGridView(int first, int second) {

        Log.d("LoadDataInGridView",FirebaseAuth.getInstance().getCurrentUser().getEmail());

        db.collection("userProfileData").document(FirebaseAuth.getInstance().getCurrentUser().getEmail()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NotNull Task<DocumentSnapshot> task) {
                String range=(String) task.getResult().get("perferAge");
                //minPrefer=fromStr_toInt1(range);
                //maxPrefer=fromStr_toInt2(range);
                Log.d("preferrrrrr","min : "+minPrefer);
                Log.d("preferrrrrr","max: "+maxPrefer);
            }
        });

        db.collection("userProfileData")
                .whereEqualTo("gender", User.getInstance().getLookingForGender())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                Log.d("user", " my lat: " + (double) User.getInstance().getLatitude());
                                //                              double myLat=(double)User.getInstance().getLatitude();
                                Log.d("user", " my lon: " + (double) User.getInstance().getLongtitude());
                                Log.d("user", " their lat: " + (double) document.get("latitude"));
                                Log.d("user", " their lon " + (double) document.get("longtitude"));
                                double dis = distance((double) User.getInstance().getLatitude(), (double) User.getInstance().getLongtitude(), (double) document.get("latitude"), (double) document.get("longtitude"));
                                Log.d("distance", " the distance is : " + dis);
                                Timestamp timestamp = (Timestamp) document.get("lastUpdatedLocation");

                                if ( (dis <= 0.4) && (Timestamp.now().getSeconds()-timestamp.getSeconds() <= 300) ) {

                                    // after getting this list we are passing
                                    // that list to our object class.
                                    DataModel dataModel = document.toObject(DataModel.class);
                                    dataModelArrayList.clear();
                                    dataModelArrayList2.clear();

                                    TimeZone timeZone = TimeZone.getTimeZone("Israel");
                                    Calendar today = Calendar.getInstance();

                                    DateFormat timeFormat = new SimpleDateFormat("HH:mm");
                                    timeFormat.setTimeZone(TimeZone.getTimeZone("Asia/Jerusalem"));
                                    String curTime = timeFormat.format(new Date());

                                    //try
                                    DateFormat dayFormat = new SimpleDateFormat("yyyy-MM-dd");
                                    String curday = dayFormat.format(new Date());
                                    //try


                                    //last updated locaion from fb
//                                Object c=document.get("lastUpdatedLocation");
//                                String g = String.valueOf(c);
//                                String substring=g.substring(18,28);
//                                Log.d("lll","new rrrrrrrrrrrrr "+substring);
//
//                                long l=Long.parseLong(substring);
//                               // long l1=Long.parseLong(substring1);
//                                Log.d("lll","seconddddd "+l);
//                               // Log.d("lll","new yyyyyy "+l1);
//
//                                long ts = System.currentTimeMillis()/1000;
//                                Log.d("lll","firsttttt "+ts);
//                                long res=ts-l;
//                                Log.d("lll","mmmmmmmmmmm "+res);
//
//                                long minutes = TimeUnit.MILLISECONDS.toMinutes(res);
//                                Log.d("lll","resssssssss "+minutes);


//                                String stringToConvert = String.valueOf(c);
//                                Long convertedLong = Long.parseLong(stringToConvert);
//                                Log.d("lll","new tttttttttttt "+convertedLong);


//                                Log.d("Timeee", " timeeeeeee: "+ts );


//                                Timestamp tsr = (Timestamp)c ;
//                                Log.d("new time","new time"+tsr);


//                                Log.d("lll","new rrrrrrrrrr "+g);
////                                Character ch=g.charAt(1);
////


                                    today.setTimeZone(timeZone);
                                    Object s = document.get("dateOfBirth");
                                    Object date = document.get("email");
                                    String dateb = String.valueOf(date);
                                    Log.d("email", "email is: " + dateb);
                                    String string = String.valueOf(s);
                                    String[] split = string.split("-");
                                    int i = Integer.parseInt(split[2]);
                                    int age = today.get(Calendar.YEAR) - i;
                                    Log.d("TAG", " the date is: " + split[2]);
                                    Log.d("TAG", " the age is: " + age);
                                    Log.d("TAG", " the first is: " + first);
                                    Log.d("TAG", " the second is: " + second);
                                    Log.d("time", " the current time is : " + curTime);


                                    Log.d("time", " the day is : " + curday);


                                    if (first == 0 && second == 0) {
                                        if (age >= minPrefer && age <= maxPrefer) {
                                            dataModelArrayList.add(0, dataModel);
                                        }
                                        else {
                                            dataModelArrayList.add(dataModel);
                                        }

                                        GridAdapter adapter = new GridAdapter(getActivity(), dataModelArrayList);
                                        gridadapter.setAdapter(adapter);


                                    }
                                    else {
                                        if (age >= first && age <= second) {
                                            dataModelArrayList2.add(dataModel);
                                            GridAdapter adapter = new GridAdapter(getActivity(), dataModelArrayList2);
                                            gridadapter.setAdapter(adapter);

                                        }
//                                    }

                                    }

                                    //try
                                    // after getting data from Firebase
                                    // we are storing that data in our array list
//                                dataModelArrayList.add(dataModel);
                                    Log.d("TAG", document.getId() + " => " + document.getData());
                                }
                            }

                            Log.d("ARRAY LIST", "" + dataModelArrayList);


//                            if (first== 0 && second== 0)
//                            {
//                                GridAdapter adapter = new GridAdapter(getActivity(), dataModelArrayList);
//                                gridadapter.setAdapter(adapter);
//                            }

//                            else
//                            {
//                                GridAdapter adapter = new GridAdapter(getActivity(), dataModelArrayList2);
//
//                                gridadapter.setAdapter(adapter);
//                            }





//                            if (getActivity()!= null){
//                                GridAdapter adapter = new GridAdapter(getActivity(), dataModelArrayList);
//                                gridadapter.setAdapter(adapter);}
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.filter_menu, menu);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:{
                 Navigation.findNavController(view).navigate(R.id.action_Nearby_to_city);

                return true;
            }

            case R.id.subitem1: {
                one = "18-21";
                Model.instance.UpdateUserSuggestions(one);
                Age1=fromStr_toInt1(one);
                Age2=fromStr_toInt2(one);
                dataModelArrayList.clear();
                dataModelArrayList2.clear();
                loadDatainGridView(Age1,Age2);
                return true;
            }
            case R.id.subitem2:{
                one="22-25";
                Model.instance.UpdateUserSuggestions(one);
                Age1=fromStr_toInt1(one);
                Age2=fromStr_toInt2(one);
                dataModelArrayList.forEach(i->{
                    Log.d("array list", "item "+i.getEmail());
                });
//
//                ArrayList<String> list=new ArrayList<String>();
//                String[] locales = Locale.getISOCountries();
//                for (String countryCode : locales) {
//                    Locale obj = new Locale("", countryCode);
//                    list.add(obj.getDisplayCountry());
//                    Log.d("countries", "country :"+obj.getDisplayCountry() +"code "+obj);
//                }

                dataModelArrayList.clear();
                dataModelArrayList2.clear();
                loadDatainGridView(Age1,Age2);
                return true;
            }
            case R.id.subitem3:{
                one="26-31";
                Model.instance.UpdateUserSuggestions(one);
                Age1=fromStr_toInt1(one);
                Age2=fromStr_toInt2(one);
                dataModelArrayList.clear();
                dataModelArrayList2.clear();
                loadDatainGridView(Age1,Age2);
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }


    }


    public Integer fromStr_toInt1(String str)
    {
        int age1;
        String[] split =str.split("-");
        age1 =Integer.parseInt(split[0]);

        return age1;
    }

    public Integer fromStr_toInt2(String str)
    {
        int age2;
        String[] split =str.split("-");
        age2 =Integer.parseInt(split[1]);

        return age2;
    }

    public double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist);
    }

    public double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    public double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }


}
