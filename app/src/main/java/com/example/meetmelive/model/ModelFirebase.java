package com.example.meetmelive.model;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.util.Log;
import android.os.AsyncTask;
import android.webkit.MimeTypeMap;
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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
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
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ModelFirebase {

    public static FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    public static FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseStorage storage = FirebaseStorage.getInstance();


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


    //check if needed to add latitude and longtitude (igal)

    public static void registerUserAccount(String email, final String username, final String password,
                                           final String city, final String description,final String gender,
                                           final String lookingForGender, final String dateOfBirth, final Uri imageUri,
                                           final Listener<Boolean> listener){

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
                    uploadUserData(email,username,city,description, gender, lookingForGender, dateOfBirth,imageUri);
                    setUserAppData(email);

                    //add user data to local DB
                    User user =  new User(firebaseAuth.getUid(),email, username, city, description, gender, lookingForGender, dateOfBirth,
                                        User.getInstance().getProfileImageUrl(),"","","",0.0,0.0, User.getInstance().getLastUpdatedLocation());
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

    private static void uploadUserData(final String email,final String username,final String city, final String desctiption, final String gender, final String lookingForGender, final String dateOfBirth, Uri imageUri){

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
                        data.put("userId",FirebaseAuth.getInstance().getCurrentUser().getUid());
                        data.put("email", email);
                        data.put("username", username);
                        data.put("city",city);
                        data.put("description",desctiption);
                        data.put("gender", gender);
                        data.put("lookingForGender", lookingForGender);
                        data.put("dateOfBirth",dateOfBirth);
                        data.put("profileImageUrl", task.getResult().toString());
                        data.put("pic1", "");
                        data.put("pic2", "");
                        data.put("pic3", "");
                        data.put("latitude",0.0);//need to be fixed
                        data.put("longtitude",0.0);//need to be fixed
                        data.put("lastUpdatedLocation", FieldValue.serverTimestamp());
                        //data.put("looking For Age","18-24"); need to be fixed

                        db.collection("userProfileData").document(email).set(data).addOnSuccessListener(new OnSuccessListener<Void>() {
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
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        db.collection("userProfileData").document(email).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){ ;
                    User.getInstance().userId = firebaseAuth.getUid();
                    User.getInstance().email = email;
                    User.getInstance().username = (String) task.getResult().get("username");
                    User.getInstance().city= (String) task.getResult().get("city");
                    User.getInstance().description = (String) task.getResult().get("description");
                    User.getInstance().gender = (String) task.getResult().get("gender");
                    User.getInstance().lookingForGender = (String) task.getResult().get("lookingForGender");
                    User.getInstance().dateOfBirth= (String) task.getResult().get("dateOfBirth");
                    User.getInstance().profileImageUrl = (String) task.getResult().get("profileImageUrl");
                    User.getInstance().pic1= (String) task.getResult().get("pic1");
                    User.getInstance().pic2= (String) task.getResult().get("pic2");
                    User.getInstance().pic3= (String) task.getResult().get("pic3");
                    User.getInstance().latitude= 0.0;
                    User.getInstance().longtitude= 0.0;

                    // User.getInstance().lookingForAge= (String) task.getResult().get("looking For Age");
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


    public static void updateLocation(Location location){
        DocumentReference washingtonRef = db.collection("userProfileData").document(User.getInstance().email);

        //update latitude
        washingtonRef
                .update("latitude",  location.getLatitude())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("location", "Location latitude successfully updated!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error updating document", e);
                    }
                });

        //update longtitude
        washingtonRef
                .update("longtitude",  location.getLongitude())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("location", "Location longtitude successfully updated!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error updating document", e);
                    }
                });
        //last Updated Location
        washingtonRef.update("lastUpdatedLocation",FieldValue.serverTimestamp());
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

    public static void uploadImageToFirebase(String picName, String url){
        DocumentReference washingtonRef = db.collection("userProfileData").document(User.getInstance().email);

        //update latitude
        washingtonRef
                .update(picName,url)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("picture", "picture"+picName+" successfully updated!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error updating document", e);
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
                    User.getInstance().setProfileImageUrl((String) document.getData().get("profileImageUrl"));
                        Log.d("fire photo","profile  "+User.getInstance().getProfileImageUrl());
                    }
                    if(picName.contains("pic1")){
                        User.getInstance().setPic1((String) document.getData().get("pic1"));
                    }
                    if(picName.contains("pic2")){
                        User.getInstance().setPic2((String) document.getData().get("pic2"));
                    }
                    if(picName.contains("pic3")){
                        User.getInstance().setPic3((String) document.getData().get("pic3"));
                    }
                }
            } else {
                Log.d("TAG", "Error getting documents: ", task.getException());
            }
        });
    }


    // need to be fixed (igal)
    public void deleteRecipeCollection(User user) {
        db.collection("Deleted Users")
                .document(user.email).set(user.toMap()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("TAG", "********* User removed Successfully ************");
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

//    public static void checkUserSex() {
//        db.collection("userProfileData").document().get
//    }

    public static void signOut(){
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();
    }

}
