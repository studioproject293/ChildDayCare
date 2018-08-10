package com.example.abhinav_rapidbox.childdaycare.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.example.abhinav_rapidbox.childdaycare.R;

public class SplashScreenActivity extends AppCompatActivity {
    private LinearLayout imageSplash;
    private boolean timerStarted;
    private Animation animator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        setUpXMLVariables();
    }
    public void setUpXMLVariables() {

        imageSplash =  findViewById(R.id.imageSplash);

        animator = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        animator.setAnimationListener(new Animation.AnimationListener() {
                                          @Override
                                          public void onAnimationStart(Animation animation) {
                                              timerStarted = true;


                                          }

                                          @Override
                                          public void onAnimationEnd(Animation animation) {
                                              timerStarted = false;
                                              Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                                              startActivity(intent);
                                              finish();

                                          }

                                          @Override
                                          public void onAnimationRepeat(Animation animation) {
                                          }
                                      }

        );
        imageSplash.startAnimation(animator);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timerStarted) {
            animator.cancel();
        }
    }
}
