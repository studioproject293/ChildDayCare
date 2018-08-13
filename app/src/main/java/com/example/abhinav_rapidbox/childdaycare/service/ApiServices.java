package com.example.abhinav_rapidbox.childdaycare.service;


import com.example.abhinav_rapidbox.childdaycare.pojo.ChildSignUp;
import com.example.abhinav_rapidbox.childdaycare.pojo.DayCareListModel;
import com.example.abhinav_rapidbox.childdaycare.pojo.SiginInModel;
import com.example.abhinav_rapidbox.childdaycare.pojo.UserSignUpModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;


/**
 * Created by vikram jha on 7/11/2018.
 */

public interface ApiServices {
    int REQUEST_DAYCARE = 1;
    @GET("daycare")
    Call<Result<ArrayList<DayCareListModel>>> getCategories();
    int REQUEST_SIGININ_USER =2;
    @POST("signup/login")
    Call<Result<UserSignUpModel>>loginUrl(@Body SiginInModel siginInModel);
    int REQUEST_USER_SIGINUP =3;
    @POST("signup/saveuser")
    Call<Result<UserSignUpModel>>signUpUrl(@Body UserSignUpModel userSignUpModel);
    int REQUEST_CHILD_SIGINUP = 4;
    @POST("registration/saveData")
    Call<Result<ChildSignUp>>childSignUpUrl(@Body ChildSignUp childSignUp);

}
