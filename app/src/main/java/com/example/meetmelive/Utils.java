package com.example.meetmelive;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.MediaStore;
import android.widget.ImageView;
import android.widget.Toast;

public class Utils {

    static int REQUEST_CODE = 1;

    public static void animateBackground(ImageView background, long duration){
        background.animate().scaleX((float) 1.5).scaleY((float) 1.5).setDuration(duration).start();
    }

    public static void chooseImageFromGallery(Activity activity) {
        try {
            Intent openGalleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
            openGalleryIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");

            activity.startActivityForResult(openGalleryIntent, REQUEST_CODE);
        } catch (Exception e) {
            Toast.makeText(MyApplication.context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
