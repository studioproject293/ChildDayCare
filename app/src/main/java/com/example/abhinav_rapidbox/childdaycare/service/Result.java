package com.example.abhinav_rapidbox.childdaycare.service;

/**
 * Created by vikram jha on 7/11/2018.
 */

public class Result<T> {
    public String code;
    public String message;
    public T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


}
