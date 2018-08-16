package com.example.abhinav_rapidbox.childdaycare.utill;


import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;

import com.example.abhinav_rapidbox.childdaycare.R;

public class AppConstant {
    public static final int HOME_FRAGMENT = 1;
    public static final int UPDATE_TOOLBAR = 501;
    public static final int PRODUCT_DETAILS_FRAGMENT = 2;
    public static final int SignupFragment = 3;
    public static final int SignInFragment = 4;
    public static final int ENQUIRY_FRAGMENT = 5;
    public static final String TABLE_USER = "userDetailList";
    public static final int SIGNUP_FRAGMENT_CHILD = 6;
    public static final int FRAGMENT_USER_SIGNUP = 7;
    public static final int FRAGMENT_OTP = 8;
    public static final int USER_PROFILE = 9;
    public static final int IMAGEFULLSCREENFRAGMENT = 10;



    private void showError(EditText editText, Context context) {
        Animation shake = AnimationUtils.loadAnimation(context, R.anim.shake);
        editText.startAnimation(shake);
    }
}
