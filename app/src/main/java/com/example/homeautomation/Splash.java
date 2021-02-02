package com.example.homeautomation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class Splash extends AppCompatActivity {
private static int SPLASH_TIME_OUT=4000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                String Checkbox = preferences.getString("remember", "");
                if (Checkbox.equals("true")) {
                    startActivity(new Intent(Splash.this, Rooms.class));
                }else{
                    startActivity(new Intent(Splash.this,verification.class));
                }

                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}