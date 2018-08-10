package com.example.abhinav_rapidbox.childdaycare.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abhinav_rapidbox.childdaycare.R;
import com.example.abhinav_rapidbox.childdaycare.pojo.UserSignUpModel;
import com.example.abhinav_rapidbox.childdaycare.service.EventListner;
import com.example.abhinav_rapidbox.childdaycare.service.Result;
import com.example.abhinav_rapidbox.childdaycare.utill.DialogUtil;
import com.example.abhinav_rapidbox.childdaycare.utill.PinEntryView;


public class OtpFragment extends BaseFragment implements EventListner {

    PinEntryView otp;
    TextView mobileno;
    Button confirm;
    private View root_view;
    LinearLayout lineer;
    Activity activity;
    static UserSignUpModel userSignUpModelData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root_view = inflater.inflate(R.layout.fragment_otp, container, false);
        activity = getActivity();
        initView();
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

                }
            }
        });


        return root_view;
    }

    private void initView() {
        otp = root_view.findViewById(R.id.edittext_otp);
        confirm = root_view.findViewById(R.id.otp_confirm);
        mobileno = root_view.findViewById(R.id.mobileno);
        mobileno.setText(getString(R.string.sent_to, ""));
        lineer = root_view.findViewById(R.id.lineer);
    }

    @Override
    public void onSuccessResponse(int reqType, Result data) {
        switch (reqType) {

        }
        DialogUtil.stopProgressDisplay();
    }

    @Override
    public void onFailureResponse(int reqType, Result data) {

    }


    public static Fragment newInstance(UserSignUpModel userSignUpModel) {
        userSignUpModelData = userSignUpModel;
        return new OtpFragment();
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
