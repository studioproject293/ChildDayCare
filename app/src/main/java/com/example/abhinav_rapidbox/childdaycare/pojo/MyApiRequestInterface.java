package com.example.abhinav_rapidbox.childdaycare.pojo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by vikram jha on 9/24/2018.
 */

public interface MyApiRequestInterface {
    @GET("/maps/api/directions/json")
    Call<DirectionResults> callback(@Query("origin") String origin, @Query("destination") String destination);
}

