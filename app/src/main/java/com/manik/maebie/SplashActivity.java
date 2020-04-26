package com.manik.maebie;

import android.content.Intent;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        firebaseAuth  =FirebaseAuth.getInstance();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

            }
        }, 3000);
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentuser = firebaseAuth.getCurrentUser();

        if(currentuser == null){
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    Intent registerintent = new Intent(SplashActivity.this, RegisterActivity.class);
                    startActivity(registerintent);
                    finish();
                }
            }, 3000);
        }else {
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    Intent mainintent = new Intent(SplashActivity.this, Main2Activity.class);
                    startActivity(mainintent);
                    finish();
                }
            }, 3000);
        }
    }

}
