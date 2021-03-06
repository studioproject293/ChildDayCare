package com.example.abhinav_rapidbox.childdaycare.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.abhinav_rapidbox.childdaycare.R;
import com.example.abhinav_rapidbox.childdaycare.cache.PrefManager;

import java.util.ArrayList;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;

public class SplashScreenActivity extends AppCompatActivity {
    public static final int MULTIPLE_PERMISSIONS = 10;
    String[] permissions = new String[]
            {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
    PrefManager prefManager;
    private GifImageView imageSplash;
    private boolean timerStarted;
    private Animation animator;

    private boolean checkPermissions() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p : permissions) {
            result = ContextCompat.checkSelfPermission(getApplicationContext(), p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        prefManager = PrefManager.getInstance();
        setUpXMLVariables();
    }

    public void setUpXMLVariables() {

        imageSplash = findViewById(R.id.imageSplash);

        animator = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        animator.setAnimationListener(new Animation.AnimationListener() {
                                          @Override
                                          public void onAnimationStart(Animation animation) {
                                              timerStarted = true;


                                          }

                                          @Override
                                          public void onAnimationEnd(Animation animation) {
                                              timerStarted = false;
                                              if (checkPermissions()) {
                                                  if (prefManager.getUsername() != null) {
                                                      Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                                                      startActivity(intent);
                                                      finish();
                                                  } else {
                                                      Intent intent = new Intent(SplashScreenActivity.this, DemoLoginActivity.class);
                                                      startActivity(intent);
                                                      finish();
                                                  }
                                              } else {
                                                  Intent intent = new Intent(SplashScreenActivity.this, LocationActivity.class);
                                                  startActivity(intent);
                                                  finish();
                                              }


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

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MULTIPLE_PERMISSIONS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    //Toast.makeText(getApplicationContext(), "Permission granted", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    // Toast.makeText(getApplicationContext(), "Permission denied", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SplashScreenActivity.this, LocationActivity.class);
                    startActivity(intent);
                    finish();
                    //checkPermissions();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}
