package com.example.meetmelive.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.meetmelive.MainActivity;
import com.example.meetmelive.R;
import com.example.meetmelive.model.ModelFirebase;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static com.example.meetmelive.model.ModelFirebase.firebaseAuth;

public class login extends AppCompatActivity {

    EditText email, password;
    Button btnRegister, btnLogin;
    CallbackManager mCallbackManager;
    LoginButton loginButton;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.login_activity_email);
        password = findViewById(R.id.login_activity_password);
        btnRegister = findViewById(R.id.login_activity_register_btn);
        btnLogin = findViewById(R.id.login_activity_login_btn);

        firebaseAuth = FirebaseAuth.getInstance();

        //sets all the parameters of the logged on user
        if (firebaseAuth.getCurrentUser() != null) {
            ModelFirebase.setUserAppData(firebaseAuth.getCurrentUser().getEmail());
            startActivity(new Intent(login.this, MainActivity.class));
            finish();
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ModelFirebase.loginUser(email.getText().toString(), password.getText().toString(), new ModelFirebase.Listener<Boolean>() {
                    @Override
                    public void onComplete() {
                        startActivity(new Intent(login.this, MainActivity.class));
                        finish();
                    }
                    @Override
                    public void onFail() {
                        Toast.makeText(login.this, "Failed to login", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(login.this, register.class));
                finish();
            }
        });
    }
}