package com.example.meetmelive.model;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.webkit.MimeTypeMap;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.meetmelive.CalculateAge;
import com.example.meetmelive.MyApplication;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.util.Listener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ModelFirebase {

    public static FirebaseAuth mAuth;
    public FirebaseAuth.AuthStateListener mAuthListener;
    public ModelFirebase modelFirebase;
    public static FirebaseDatabase mFirebaseDatabase;
    public static DatabaseReference myRef;
    public Context mContext;

    public static FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    public static FirebaseFirestore db = FirebaseFirestore.getInstance();
    public static String userID;

    public ModelFirebase(Context context) {
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        mContext = context;

        if (mAuth.getCurrentUser() != null) {
            userID = mAuth.getCurrentUser().getUid();
        }
    }

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

    public static void registerUserAccount(final String username, String password, final String email, final String city,
                                           final String gender, final String lookingForGender, final String dateOfBirth, final String description, final Uri imageUri, final Uri pic1, final Uri pic2, final Uri pic3, final Listener<Boolean> listener){
        if (firebaseAuth.getCurrentUser() != null){
            firebaseAuth.signOut();
        }
        if (firebaseAuth.getCurrentUser() == null &&
                username != null && !username.equals("") &&
                password != null && !password.equals("") &&
                email != null && !email.equals("") &&
                imageUri != null){
            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    Toast.makeText(MyApplication.context, "User registered", Toast.LENGTH_SHORT).show();
                    userID = firebaseAuth.getCurrentUser().getUid();
//                    User user = new User(gender, lookingForGender, userID, email, username, city, description, dateOfBirth, "", "", "", "");
                    setUserAppData(email);
                    uploadUserData(username, email, city, gender, lookingForGender, dateOfBirth, description, imageUri);
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

    public static void uploadUserData(final String username, final String email, final String city, final String gender, final String lookingForGender, final String dateOfBirth, final String description, Uri imageUri){

        final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        StorageReference storageReference = FirebaseStorage.getInstance().getReference("images");

        if (imageUri != null){
            String imageName = username + "." + getExtension(imageUri);
            final StorageReference imageRef = storageReference.child(imageName);

            UploadTask uploadTask = imageRef.putFile(imageUri);
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
                        data.put("username", username);
                        data.put("email", email);
                        data.put("city", city);
                        data.put("gender", gender);
                        data.put("looking for", lookingForGender);
                        data.put("dateOfBirth", dateOfBirth);
                        data.put("description", description);
                        data.put("userId", FirebaseAuth.getInstance().getUid());
                        data.put("profileImageUrl", task.getResult().toString());
                        data.put("pic1", "");
                        data.put("pic1", "");
                        data.put("pic1", "");

                        firebaseFirestore.collection("userProfileData").document(email).set(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                if (firebaseAuth.getCurrentUser() != null){
                                    firebaseAuth.signOut();
                                }
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
                if (task.isSuccessful()){
                    User.getInstance().setUsername((String) task.getResult().get("username"));
                    User.getInstance().setEmail(email);
                    User.getInstance().setCity((String) task.getResult().get("city"));
                    User.getInstance().setSex((String) task.getResult().get("gender"));
                    User.getInstance().setPreferSex((String) task.getResult().get("looking for"));
                    User.getInstance().setDateOfBirth((String) task.getResult().get("dateOfBirth"));
                    User.getInstance().setDescription((String) task.getResult().get("description"));
                    User.getInstance().setUser_id(firebaseAuth.getUid());
                    User.getInstance().setProfileImageUrl((String) task.getResult().get("profileImageUrl"));
                }
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

//    public static void checkUserSex() {
//        db.collection("userProfileData").document().get
//    }

    public static void signOut(){
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();
    }

}
