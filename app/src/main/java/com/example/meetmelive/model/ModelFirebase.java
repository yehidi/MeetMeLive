package com.example.meetmelive.model;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.DatePicker;
import android.widget.GridView;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.meetmelive.MyApplication;
import com.example.meetmelive.Nearby;
import com.example.meetmelive.R;
import com.example.meetmelive.adapter.GridAdapter;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.util.Listener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModelFirebase{

    public static FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    public static FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseStorage storage = FirebaseStorage.getInstance();
    MenuInflater menu;


    public interface Listener<T>{
        void onComplete();
        void onFail();
    }

    public static void loginUser(final String email, String password, final Listener<Boolean> listener){

        if (email != null && !email.equals("") && password != null && !password.equals("")){
            if (firebaseAuth.getCurrentUser() != null) {
                firebaseAuth.signOut();
            }
            firebaseAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    Toast.makeText(MyApplication.context, "Welcome!", Toast.LENGTH_SHORT).show();
                    setUserAppData(email);
                    listener.onComplete();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MyApplication.context, "Failed to login: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    listener.onFail();
                }
            });
        }
        else {
            Toast.makeText(MyApplication.context, "Please fill both data fields", Toast.LENGTH_SHORT).show();
        }
    }

    public static void registerUserAccount(final String name, String password, final String email,
                                           final String gender, final String lookingForGender,final String dateOfBirth,
                                           final String currentLocation ,final String description ,final String city,final Uri profileImage,
                                           final String image1,final String image2,final String image3 ,Listener<Boolean> listener){

        if (firebaseAuth.getCurrentUser() != null){
            firebaseAuth.signOut();
        }
        if (firebaseAuth.getCurrentUser() == null &&
                name != null && !name.equals("") &&
                password != null && !password.equals("") &&
                email != null && !email.equals("")) {
            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    Toast.makeText(MyApplication.context, "User registered", Toast.LENGTH_SHORT).show();
                    uploadUserData(name, email, gender, lookingForGender,dateOfBirth,currentLocation,description,city,profileImage,image1,image2,image3);
                    setUserAppData(email);

                    //add user data to local DB
                    User user =  new User(email, name, dateOfBirth, description, gender, lookingForGender, city, User.getInstance().profilePic, "","","");
                    new AsyncTask<String, String, String>() {
                        @Override
                        protected String doInBackground(String... strings) {
                            AppLocalDb.db.userDao().insertAll(user);
                            return "";
                        }
                    }.execute();
                    listener.onComplete();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MyApplication.context, "Failed registering user", Toast.LENGTH_SHORT).show();
                    listener.onFail();
                }
            });
        }
        else {
            Toast.makeText(MyApplication.context, "Please fill all input fields and profile image", Toast.LENGTH_SHORT).show();
            listener.onFail();
        }
    }

    private static void uploadUserData(final String username, final String email, final String gender, final String lookingForGender,final String dateOfBirth,final String currentLocation,final String description ,final String city, Uri profileImage,String image1,String image2,String image3){


        StorageReference storageReference = FirebaseStorage.getInstance().getReference("images");

        if (profileImage != null){
            String imageName = username + "." + getExtension(profileImage);
            final StorageReference imageRef = storageReference.child(imageName);

            UploadTask uploadTask = imageRef.putFile(profileImage);
            uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()){
                        throw task.getException();
                    }
                    return imageRef.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()){

                        Map<String,Object> data = new HashMap<>();
                        data.put("id",FirebaseAuth.getInstance().getCurrentUser().getUid());
                        data.put("profileImageUrl", task.getResult().toString());
                        data.put("username", username);
                        data.put("email", email);
                        data.put("looking for", lookingForGender);
                        data.put("gender", gender);
                        data.put("current Location",null);
                        data.put("dateOfBirth",dateOfBirth);
                        data.put("info",description);
                        data.put("city",city);

                        data.put("picture 1", "");
                        data.put("picture 2", "");
                        data.put("picture 3", "");

                        db.collection("userProfileData").document(email).set(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                setUserAppData(email);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(MyApplication.context, "Fails to create user and upload data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    else if (!task.isSuccessful()){
                        Toast.makeText(MyApplication.context, task.getException().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        else {
            Toast.makeText(MyApplication.context, "Please choose a profile image", Toast.LENGTH_SHORT).show();
        }
    }

    public static void setUserAppData(final String email){
        FirebaseFirestore db = FirebaseFirestore.getInstance();;
        final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        db.collection("userProfileData").document(email).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){ ;
                    User.getInstance().name = (String) task.getResult().get("username");
                    User.getInstance().profilePic = (String) task.getResult().get("profileImageUrl");
                    User.getInstance().description = (String) task.getResult().get("info");
                    User.getInstance().email = email;
                    User.getInstance().gender = (String) task.getResult().get("gender");
                    User.getInstance().lookingForGender = (String) task.getResult().get("looking for");
                    User.getInstance().birthday= (String) task.getResult().get("dateOfBirth");
                    User.getInstance().currentLocation= (String) task.getResult().get("current Location");
                    User.getInstance().city= (String) task.getResult().get("city");
                    User.getInstance().pic1= (String) task.getResult().get("picture 1");
                    User.getInstance().pic2= (String) task.getResult().get("picture 2");
                    User.getInstance().pic3= (String) task.getResult().get("picture 3");
                    User.getInstance().id = firebaseAuth.getUid();
                }
            }
        });
    }

    public static  void updateUserProfile(User user){

            db.collection("userProfileData").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {

                            if(document.getData().get("email").equals(user.email)){
                                db.collection("userProfileData")
                                        .document(User.getInstance().email).set(user.toMap());
                            }
                        }
                    } else {
                        Log.d("TAG", "Error getting documents: ", task.getException());
                    }
                }
            });
    }


    public static void trying(){
        DocumentReference washingtonRef = db.collection("userProfileData").document(User.getInstance().email);

     //   Log.d("pull data", "document: "+washingtonRef.get());
        washingtonRef
                .update("current Location", "1")
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("TAG", "DocumentSnapshot successfully updated!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error updating document", e);
                    }
                });
    }


    //image uploading
    public void uploadImage(Bitmap imageBmp, String name, Model.UploadImageListener listener){
        final StorageReference imagesRef = storage.getReference().child("images").child(name);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imageBmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        UploadTask uploadTask = imagesRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception exception) {
                listener.onComplete(null);
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                imagesRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Uri downloadUrl = uri;
                        listener.onComplete(downloadUrl.toString());
                    }
                });
            }
        });
    }

    public static String getExtension(Uri uri){
        try{
            ContentResolver contentResolver = MyApplication.context.getContentResolver();
            MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
            return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));

        } catch (Exception e) {
            Toast.makeText(MyApplication.context, "Register page: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    public static void signOut(){
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();
    }


    public void getUser(String email, Model.GetUserListener listener) {
        db.collection("userProfileData").document(email).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NotNull Task<DocumentSnapshot> task) {
                User user=null;
                if(task.isSuccessful()){
                    DocumentSnapshot doc = task.getResult();
                    if (doc!=null) {
                        user = new User();
                        user.fromMap(task.getResult().getData());
                    }
                }
                listener.onComplete(user);
            }
        });
    }

    public static void getImageFromFireBase(String picName){

        db.collection("userProfileData").whereEqualTo("email",User.getInstance().email).get().addOnCompleteListener((OnCompleteListener<QuerySnapshot>) task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    if(picName.contains("profileImageUrl")){
                    User.getInstance().profilePic=(String) document.getData().get("profileImageUrl");
                        Log.d("fire photo","profile  "+User.getInstance().profilePic);
                    }
                    if(picName.contains("picture 1")){
                        User.getInstance().pic1=(String) document.getData().get("picture 1");
                    }
                    if(picName.contains("picture 2")){
                        User.getInstance().pic2=(String) document.getData().get("picture 2");
                    }
                    if(picName.contains("picture 3")){
                        User.getInstance().pic3=(String) document.getData().get("picture 3");
                    }
                }
            } else {
                Log.d("TAG", "Error getting documents: ", task.getException());
            }
        });
    }


    public void deleteRecipeCollection(User user) {
        db.collection("Deleted Users")
                .document(user.email).set(user.toMap()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("TAG", "********* Recipe remove Successfully ************");
            }

        });
    }

    public void deleteUser(User user, Model.DeleteUserListener listener) {
        db.collection("userProfileData").document(user.email).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                listener.onComplete();
            }
        });
    }


    //NearBy
    int age;
    public void loadDatainGridView(ArrayList<DataModel> dataModelArrayList,GridView gridadapter,Nearby near,Integer small,Integer big) {
        // below line is use to get data from Firebase
        // firestore using collection in android.
        db.collection("userProfileData").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        // after getting the data we are calling on success method
                        // and inside this method we are checking if the received
                        // query snapshot is empty or not.
                        if (!queryDocumentSnapshots.isEmpty()) {
                            // if the snapshot is not empty we are hiding our
                            // progress bar and adding our data in a list.
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {

                                //try
                                setUserAppData(User.getInstance().email);
                                //try
                                // after getting this list we are passing
                                // that list to our object class
                                Log.d("TAG","the gender is:"+User.getInstance().gender);
                                //from datamodel to user

                                if(User.getInstance().lookingForGender.equals(d.get("gender")) ) {
                                    DataModel dataModel = d.toObject(DataModel.class);
                                    dataModelArrayList.add(dataModel);
                                }

                                if(User.getInstance().lookingForGender.equals(d.get("gender")) ) {
                                    DataModel dataModel = d.toObject(DataModel.class);
                                    dataModelArrayList.add(dataModel);
                                }



                                //new try
                                Calendar today = Calendar.getInstance();
                                Object s = d.get("dateOfBirth");
                                String string=String.valueOf(s);
                                String[] split = string.split("-");

//                                int s= d.get("dateOfBirth").equals();
                                        //.toString().split("-");

                                Log.d("TAG","the dateeeeeeeeee is:"+ split);
//                                Log.d("TAG","the age is:"+ split[2]);
//                                 while((small!=null)&&(big!=null))
//                                 {
//                                     if(age>=small && age<=big)
//                                     {
//                                         DataModel dataModel = d.toObject(DataModel.class);
//                                         dataModelArrayList.add(dataModel);
//                                     }
//
//                                 }





//                                DataModel dataModel = d.toObject(DataModel.class);
//                                    dataModelArrayList.add(dataModel);

                                //try
//                                if(User.getInstance().lookingForGender.equals("Female"))
//                                {
//                                    if(dataModel.equals("Female"))
//                                      dataModelArrayList.add(user);
//                                }
                                //try
//                                        dataModelArrayList.add(dataModel);
                                // after getting data from Firebase
                                // we are storing that data in our array list

                            }
                            // after that we are passing our array list to our adapter class.
                            GridAdapter adapter = new GridAdapter(near.getContext(), dataModelArrayList);

                            // after passing this array list
                            // to our adapter class we are setting
                            // our adapter to our list view.
                            gridadapter.setAdapter(adapter);
                        } else {
                            // if the snapshot is empty we are displaying a toast message.
                            Toast.makeText(near.getContext(), "No data found in Database", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // we are displaying a toast message
                // when we get any error from Firebase.
                Toast.makeText(near.getContext(), "Fail to load data..", Toast.LENGTH_SHORT).show();
            }
        });
    }


//new try



}
