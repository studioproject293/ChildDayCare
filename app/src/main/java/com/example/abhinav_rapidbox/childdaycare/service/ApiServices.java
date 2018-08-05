package com.example.abhinav_rapidbox.childdaycare.service;


import com.example.abhinav_rapidbox.childdaycare.pojo.DayCareListModel;

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

}
