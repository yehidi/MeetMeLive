package com.example.meetmelive.model;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.meetmelive.MyApplication;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
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

import java.io.ByteArrayOutputStream;
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

    public static void registerUserAccount(final String name, String password, final String email,
                                           final String gender, final String lookingForGender,final String dateB,
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

                    uploadUserData(name, email, gender, lookingForGender,dateB,currentLocation,description,city,profileImage,image1,image2,image3);
                    setUserAppData(email);
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

    private static void uploadUserData(final String username, final String email, final String gender, final String lookingForGender,final String dateB,final String currentLocation,final String description ,final String city, Uri profileImage,String image1,String image2,String image3){

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
                        data.put("birthDate",dateB);
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
                    User.getInstance().birthday= (String) task.getResult().get("birthDate");
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
//                                if(document.getData().get("profilePic")!=null){
//                                    String url= (String) document.getData().get("profilePic");
//                                    user.profilePic=url;
//                                }
                                Log.d("save: ","got here");
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

}
