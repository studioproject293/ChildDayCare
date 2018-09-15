package com.example.abhinav_rapidbox.childdaycare.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abhinav_rapidbox.childdaycare.R;
import com.example.abhinav_rapidbox.childdaycare.cache.PrefManager;
import com.example.abhinav_rapidbox.childdaycare.pojo.SiginInModel;
import com.example.abhinav_rapidbox.childdaycare.pojo.UserSignUpModel;
import com.example.abhinav_rapidbox.childdaycare.service.ApiServices;
import com.example.abhinav_rapidbox.childdaycare.service.EventListner;
import com.example.abhinav_rapidbox.childdaycare.service.Result;
import com.example.abhinav_rapidbox.childdaycare.service.TransportManager;
import com.example.abhinav_rapidbox.childdaycare.utill.DialogUtil;

import java.util.ArrayList;
import java.util.List;


public class DemoLoginActivity extends BaseActivity implements EventListner {

    public static final int MULTIPLE_PERMISSIONS = 10;
    private static final String TAG = "DemoLoginActivity";
    String mTokenId;
    Button button_register;
    TextView signUp, signUpGuest;
    String[] permissions = new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
    ImageView side_menu;
    private Context context;
    private EditText editText_password, editText_emailID;
    private PrefManager prefManager;

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_login);
        side_menu = findViewById(R.id.side_menu);
        side_menu.setVisibility(View.GONE);
        context = DemoLoginActivity.this;
        prefManager = PrefManager.getInstance();
        checkPermissions();
        signUpGuest = findViewById(R.id.signUpGuest);
        editText_emailID = findViewById(R.id.editText_emailID);
        editText_password = findViewById(R.id.editText_password);
        button_register = findViewById(R.id.button_register);
        signUp = findViewById(R.id.signUp);
        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editText_emailID.getText().toString();
                final String password = editText_password.getText().toString();
                if (TextUtils.isEmpty(email)) {
                    editText_emailID.setError("Please enter email address!");
                    editText_emailID.requestFocus();
                    showError(editText_emailID);
                } else if (TextUtils.isEmpty(password)) {
                    editText_password.setError("Please enter password!");
                    editText_password.requestFocus();
                    showError(editText_password);
                } else {
                    DialogUtil.displayProgress(DemoLoginActivity.this);
                    SiginInModel siginInModel = new SiginInModel();
                    siginInModel.setUser_id(editText_emailID.getText().toString());
                    siginInModel.setPassword(password);
                    TransportManager.getInstance(DemoLoginActivity.this).signInService(DemoLoginActivity.this, siginInModel);
                }


            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DemoLoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });
        signUpGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DemoLoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private boolean checkPermissions() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p : permissions) {
            result = ContextCompat.checkSelfPermission(context, p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    private void showError(EditText editText) {
        Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
        editText.startAnimation(shake);
    }

    @Override
    public void onSuccessResponse(int reqType, Result data) {
        switch (reqType) {
            case ApiServices.REQUEST_SIGININ_USER:
                UserSignUpModel userSignUpModel = (UserSignUpModel) data.getData();
                prefManager.setUsername(userSignUpModel.getUser_name());
                prefManager.setUserId(userSignUpModel.getUser_id());
                Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                break;
        }
        DialogUtil.stopProgressDisplay();
    }

    @Override
    public void onFailureResponse(int reqType, Result data) {
        DialogUtil.stopProgressDisplay();
        Toast.makeText(DemoLoginActivity.this, data.getMessage(), Toast.LENGTH_SHORT).show();
    }
}