package com.example.abhinav_rapidbox.childdaycare.service;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.View;

import com.example.abhinav_rapidbox.childdaycare.pojo.ChildSignUp;
import com.example.abhinav_rapidbox.childdaycare.pojo.DayCareListModel;
import com.example.abhinav_rapidbox.childdaycare.pojo.SiginInModel;
import com.example.abhinav_rapidbox.childdaycare.pojo.UserSignUpModel;
import com.example.abhinav_rapidbox.childdaycare.utill.Constants;
import com.example.abhinav_rapidbox.childdaycare.utill.DialogUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by vikram jha on 7/11/2018.
 */

public class TransportManager {
    private static ApiServices apiServices;
    EventListner listener;
    private static TransportManager manager;
    private static final int CODE_UNAUTHORIZED = 401;

    public static TransportManager getInstance(EventListner conlistener) {
        if (manager == null) manager = new TransportManager();
        manager.setListener(conlistener);
        return manager;
    }

    public void setListener(EventListner listener) {
        this.listener = listener;
    }

    public static ApiServices getAPIService() {
        Gson gson = new GsonBuilder().setLenient().create();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //comment in live build and uncomment in uat
        builder.interceptors().add(interceptor);

        builder.connectTimeout(30, TimeUnit.SECONDS);
        builder.readTimeout(90, TimeUnit.SECONDS);

        OkHttpClient client = builder.build();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.API_BASE_URL).addConverterFactory(GsonConverterFactory.create(gson)).client(client).build();
        apiServices = retrofit.create(ApiServices.class);
        return apiServices;
    }

    public void filterData(int type, Result result) {
        if (result.getCode().equalsIgnoreCase("200")) {
            listener.onSuccessResponse(type, result);
        } else {
            listener.onFailureResponse(type, result);
        }
        //listener.onSuccessResponse(type, result);
    }

    public boolean isConnectionAvailable(Context context) {
        if (context == null) return false;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        assert cm != null;
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    public void processResponse(Response res, int reqType) {
        Log.d("TM", "processResponse: reqType " + reqType + " res " + res + " res.code() " + res.code());
        DialogUtil.stopProgressDisplay();
        Log.d("Service", "processResponse: " + res.body());
        if (res != null) {
            Result result = new Result();
            result.setCode(Constants.ERROR_SERVER);
            result.setMessage(res.message());
            listener.onFailureResponse(reqType, result);
        } else {
            Result result = new Result();
            result.setCode(Constants.ERROR_PARSING);
            result.setMessage(res.message());
            listener.onFailureResponse(reqType, result);
        }
    }

    public void saveUser(Context context, UserSignUpModel userSignUpModel) {
        if (isConnectionAvailable(context)) {
            getAPIService().signUpUrl(userSignUpModel).enqueue(new Callback<Result<UserSignUpModel>>() {
                @Override
                public void onResponse(Call<Result<UserSignUpModel>> call, Response<Result<UserSignUpModel>> res) {
                    if (res.isSuccessful()) {
//                        listener.onSuccessResponse(ApiServices.REQUEST_PRODUCTS, res.body());
                        filterData(ApiServices.REQUEST_USER_SIGINUP, res.body());
                    } else {
                        processResponse(res, ApiServices.REQUEST_USER_SIGINUP);
                    }
                }

                @Override
                public void onFailure(Call<Result<UserSignUpModel>> call, Throwable arg0) {
                    //arg0.printStackTrace();
                    processResponse(arg0.getLocalizedMessage(), ApiServices.REQUEST_USER_SIGINUP);
                }
            });
        } else {
            processResponse(Constants.NO_INTERNET, ApiServices.REQUEST_USER_SIGINUP);
        }
    }

    public void saveChildData(Context context, ChildSignUp childSignUp) {
        if (isConnectionAvailable(context)) {
            getAPIService().childSignUpUrl(childSignUp).enqueue(new Callback<Result<ChildSignUp>>() {
                @Override
                public void onResponse(Call<Result<ChildSignUp>> call, Response<Result<ChildSignUp>> res) {
                    if (res.isSuccessful()) {
//                        listener.onSuccessResponse(ApiServices.REQUEST_PRODUCTS, res.body());
                        filterData(ApiServices.REQUEST_CHILD_SIGINUP, res.body());
                    } else {
                        processResponse(res, ApiServices.REQUEST_CHILD_SIGINUP);
                    }
                }

                @Override
                public void onFailure(Call<Result<ChildSignUp>> call, Throwable arg0) {
                    //arg0.printStackTrace();
                    processResponse(arg0.getLocalizedMessage(), ApiServices.REQUEST_CHILD_SIGINUP);
                }
            });
        } else {
            processResponse(Constants.NO_INTERNET, ApiServices.REQUEST_CHILD_SIGINUP);
        }
    }

    public void signInService(Context context, SiginInModel siginInModel) {
        if (isConnectionAvailable(context)) {
            getAPIService().loginUrl(siginInModel).enqueue(new Callback<Result<UserSignUpModel>>() {
                @Override
                public void onResponse(Call<Result<UserSignUpModel>> call, Response<Result<UserSignUpModel>> res) {
                    if (res.isSuccessful()) {
//                        listener.onSuccessResponse(ApiServices.REQUEST_PRODUCTS, res.body());
                        filterData(ApiServices.REQUEST_SIGININ_USER, res.body());
                    } else {
                        processResponse(res, ApiServices.REQUEST_SIGININ_USER);
                    }
                }

                @Override
                public void onFailure(Call<Result<UserSignUpModel>> call, Throwable arg0) {
                    //arg0.printStackTrace();
                    processResponse(arg0.getLocalizedMessage(), ApiServices.REQUEST_SIGININ_USER);
                }
            });
        } else {
            processResponse(Constants.NO_INTERNET, ApiServices.REQUEST_SIGININ_USER);
        }
    }

    public void getDayCareList(Context context) {
        if (isConnectionAvailable(context)) {
            getAPIService().getCategories().enqueue(new Callback<Result<ArrayList<DayCareListModel>>>() {
                @Override
                public void onResponse(Call<Result<ArrayList<DayCareListModel>>> call, Response<Result<ArrayList<DayCareListModel>>> res) {
                    if (res.isSuccessful()) {
//                        listener.onSuccessResponse(ApiServices.REQUEST_CATEGORIES, res.body());
                        filterData(ApiServices.REQUEST_DAYCARE, res.body());
                    } else {
                        processResponse(res, ApiServices.REQUEST_DAYCARE);
                    }
                }

                @Override
                public void onFailure(Call<Result<ArrayList<DayCareListModel>>> call, Throwable arg0) {
                    //arg0.printStackTrace();
                    processResponse(arg0.getLocalizedMessage(), ApiServices.REQUEST_DAYCARE);
                }
            });
        } else {
            processResponse(Constants.NO_INTERNET, ApiServices.REQUEST_DAYCARE);
        }
    }

    public void processResponse(String res, int reqType) {
        DialogUtil.stopProgressDisplay();
        if (res != null) {
            Result result = new Result();
            result.setCode(Constants.ERROR_SERVER);
            if (res.contains("Unable to resolve host"))
                result.setMessage(Constants.NO_INTERNET);
            else
                result.setMessage(res);
            listener.onFailureResponse(reqType, result);
        } else {
            Result result = new Result();
            result.setCode(Constants.ERROR_PARSING);
            result.setMessage("Unable to process");
            listener.onFailureResponse(reqType, result);
        }
    }


}
