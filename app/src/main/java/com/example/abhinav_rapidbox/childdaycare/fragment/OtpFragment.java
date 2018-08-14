package com.example.abhinav_rapidbox.childdaycare.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abhinav_rapidbox.childdaycare.R;
import com.example.abhinav_rapidbox.childdaycare.activity.BaseActivity;
import com.example.abhinav_rapidbox.childdaycare.activity.DemoLoginActivity;
import com.example.abhinav_rapidbox.childdaycare.pojo.UserSignUpModel;
import com.example.abhinav_rapidbox.childdaycare.service.ApiServices;
import com.example.abhinav_rapidbox.childdaycare.service.EventListner;
import com.example.abhinav_rapidbox.childdaycare.service.Result;
import com.example.abhinav_rapidbox.childdaycare.service.TransportManager;
import com.example.abhinav_rapidbox.childdaycare.utill.DialogUtil;
import com.example.abhinav_rapidbox.childdaycare.utill.PinEntryView;
import com.google.gson.Gson;


public class OtpFragment extends BaseActivity implements EventListner {

    static UserSignUpModel userSignUpModelData;
    PinEntryView otp;
    TextView mobileno;
    Button confirm;
    LinearLayout lineer;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_otp);
        activity = OtpFragment.this;
        initView();
        Intent i = getIntent();
        Bundle bundle = i.getExtras();
        Gson gson = new Gson();
        String studentDataObjectAsAString = bundle.getString("MyStudentObjectAsString");
        userSignUpModelData = gson.fromJson(studentDataObjectAsAString, UserSignUpModel.class);
        System.out.println("Data..." + new Gson().toJson(userSignUpModelData));
        mobileno.setText(getString(R.string.sent_to, userSignUpModelData.getContact_no()));
        otp.requestFocus();
        lineer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtil.hideKeyboard(lineer, activity);
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (otp.getText().toString().trim().isEmpty()) {
                    Toast.makeText(activity, getString(R.string.please_enter_otp), Toast.LENGTH_SHORT).show();
                } else {
                    DialogUtil.hideKeyboard(confirm, activity);
                    DialogUtil.displayProgress(activity);
                    userSignUpModelData.setOtp("123456");
                    TransportManager.getInstance(OtpFragment.this).saveUser(activity, userSignUpModelData);

                }
            }
        });

    }

    private void initView() {
        otp = findViewById(R.id.edittext_otp);
        confirm = findViewById(R.id.otp_confirm);
        mobileno = findViewById(R.id.mobileno);
        // mobileno.setText(getString(R.string.sent_to, userSignUpModelData.getContact_no()));
        lineer = findViewById(R.id.lineer);
    }

    @Override
    public void onSuccessResponse(int reqType, Result data) {
        switch (reqType) {
            case ApiServices.REQUEST_USER_SIGINUP:
                Toast.makeText(activity, data.getMessage(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(OtpFragment.this, DemoLoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

        }
        DialogUtil.stopProgressDisplay();
    }

    @Override
    public void onFailureResponse(int reqType, Result data) {
        DialogUtil.stopProgressDisplay();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onResume() {
        super.onResume();

    }


}
