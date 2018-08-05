package com.example.abhinav_rapidbox.childdaycare.service;

/**
 * Created by vikram jha on 7/11/2018.
 */

public interface EventListner {
    void onSuccessResponse(int reqType, Result data);

    void onFailureResponse(int reqType, Result data);

}
