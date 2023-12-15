package com.guruji.app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;

import com.airbnb.lottie.LottieAnimationView;

public class fail extends AppCompatActivity {

    LottieAnimationView lottieAnimationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fail);
        lottieAnimationView = findViewById(R.id.lottieAnimationView);

        onStart();


    }

    @Override
    protected void onStart() {
        super.onStart();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {


                    lottieAnimationView.setAnimation(R.raw.fail);
                    finish();
                }
            },4000);




    }
}