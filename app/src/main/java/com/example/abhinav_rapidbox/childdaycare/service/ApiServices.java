package com.example.abhinav_rapidbox.childdaycare.service;


import com.example.abhinav_rapidbox.childdaycare.pojo.ChildRegistrationInside;
import com.example.abhinav_rapidbox.childdaycare.pojo.ChildSignUp;
import com.example.abhinav_rapidbox.childdaycare.pojo.DayCareDetailsModel;
import com.example.abhinav_rapidbox.childdaycare.pojo.DayCareListModel;
import com.example.abhinav_rapidbox.childdaycare.pojo.SiginInModel;
import com.example.abhinav_rapidbox.childdaycare.pojo.UserSignUpModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;


/**
 * Created by vikram jha on 7/11/2018.
 */

public interface ApiServices {
    int REQUEST_DAYCARE = 1;
    int REQUEST_SIGININ_USER = 2;
    int REQUEST_USER_SIGINUP = 3;
    int REQUEST_CHILD_SIGINUP = 4;
    int REQUEST_DAYCARE_DETAILS = 5;
    int REQUEST_USER_INFO = 6;

    @POST("DayCareOverview/getCGPSData")
    Call<Result<ArrayList<DayCareListModel>>> getCategories(@Body DayCareListModel dayCareListModel);

    @POST("signup/login")
    Call<Result<UserSignUpModel>>loginUrl(@Body SiginInModel siginInModel);

    @POST("signup/saveuser")
    Call<Result<UserSignUpModel>>signUpUrl(@Body UserSignUpModel userSignUpModel);

    @POST("registration/saveData")
    Call<Result<ChildRegistrationInside>>childSignUpUrl(@Body ChildRegistrationInside childSignUp);

    @GET("DayCareRegistration/getDataById/{id}")
    Call<Result<DayCareDetailsModel>> getDayCareDetailsUrl(@Path("id") int id);

    @GET("signup/getSignupData/{userId}")
    Call<Result<UserSignUpModel>> getUserInfoUrl(@Path("userId") String id);
}
