package com.example.abhinav_rapidbox.childdaycare.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
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
    ImageView side_menu;
    android.app.AlertDialog alertD;
    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_otp);
        activity = OtpFragment.this;
        initView();
        side_menu = findViewById(R.id.side_menu);
        side_menu.setVisibility(View.GONE);
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
                CallLogoutPopup(data.getMessage());
                break;

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

    private void CallLogoutPopup(String msg) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.dashboard_logout_popup, null);
        alertD = new android.app.AlertDialog.Builder(this).create();

        /*set alert at bottom*/
        Window window = alertD.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();

        wlp.gravity = Gravity.CENTER;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);
        TextView alertActionText = view.findViewById(R.id.message);
        alertActionText.setText(msg);
        TextView cancelAlert = view.findViewById(R.id.action_text);
        cancelAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OtpFragment.this, DemoLoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        alertD.setView(view);
        alertD.show();
    }
    @Override
    public void onResume() {
        super.onResume();

    }


}
