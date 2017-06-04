package com.example.sheliza.grid_nav;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;

public class SplashActivity extends AppCompatActivity {
    ImageView imageView;
    SharedPreferences sharedPreferences;
    String designation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        sharedPreferences = getSharedPreferences(Util.PREFS_NAME, MODE_PRIVATE);
        designation = sharedPreferences.getString(Util.COL_DESIGNATION, "");
        Log.i("desig",designation);
        boolean isLogin = sharedPreferences.contains(Util.COL_EMAIL) && sharedPreferences.contains(Util.COL_PASSWORD);
        if (isLogin) {
            if (designation.equals("Assistant Professor"))
                handler.sendEmptyMessageDelayed(102, 2500);
            else
                handler.sendEmptyMessageDelayed(103, 2500);
        } else {
            handler.sendEmptyMessageDelayed(101, 2500);
        }


    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 101) {
                Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            } else if (msg.what == 102) {
                Intent i = new Intent(SplashActivity.this, HomeActivityTeacher.class);
                startActivity(i);
                finish();
            } else if (msg.what == 103) {
                {
                    Intent i = new Intent(SplashActivity.this, MainActivityofHOD.class);
                    startActivity(i);
                    finish();
                }

            }
        }



    };
}


