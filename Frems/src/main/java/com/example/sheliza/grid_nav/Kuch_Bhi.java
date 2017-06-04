package com.example.sheliza.grid_nav;

import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

public class Kuch_Bhi extends AppCompatActivity {
    private ImageView mScanner;
    private Animation mAnimation;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kuch__bhi);

        mScanner = (ImageView)findViewById(R.id.imageView2);






        Animation RightSwipe = AnimationUtils.loadAnimation(Kuch_Bhi.this, R.anim.right_swipe);
        mScanner.startAnimation(RightSwipe);
    }
}
