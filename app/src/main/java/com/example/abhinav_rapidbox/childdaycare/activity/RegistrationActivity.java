package com.example.abhinav_rapidbox.childdaycare.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.abhinav_rapidbox.childdaycare.R;
import com.example.abhinav_rapidbox.childdaycare.fragment.OtpFragment;
import com.example.abhinav_rapidbox.childdaycare.pojo.UserSignUpModel;
import com.google.gson.Gson;


public class RegistrationActivity extends BaseActivity {

    Button button_register;
    String[] cityList = {"Select City*", "Bangalore"};
    private Context context;
    private EditText edit_text_name, edit_text_mobile, edit_text_email, edit_text_password, editTextConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_registration);

        context = RegistrationActivity.this;
       

/*

        TelephonyManager tm = (TelephonyManager) getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            tm.getDeviceId(0);
        }
*/
        button_register = findViewById(R.id.button_register);
        edit_text_name = findViewById(R.id.editText_userName);
        edit_text_mobile = findViewById(R.id.editText_phoneNo);
        edit_text_email = findViewById(R.id.editText_email);
        edit_text_password = findViewById(R.id.editText_password);
        editTextConfirmPassword = findViewById(R.id.confirmPassword);
        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edit_text_name.getText().toString().trim().isEmpty()) {
                    edit_text_name.setError("Please enter user name");
                    edit_text_name.requestFocus();
                    showError(edit_text_name);
                } else if (!isValidMobile(edit_text_mobile.getText().toString().trim())) {
                    edit_text_mobile.setError("Please enter mobile number");
                    edit_text_mobile.requestFocus();
                    showError(edit_text_mobile);
                } else if (!isValidEmail(edit_text_email.getText().toString().trim())) {
                    edit_text_email.setError("Please enter email id");
                    edit_text_email.requestFocus();
                    showError(edit_text_email);
                } else if (edit_text_password.getText().toString().trim().isEmpty()) {
                    edit_text_password.setError("Please enter password");
                    edit_text_password.requestFocus();
                    showError(edit_text_password);
                } else if (edit_text_password.getText().toString().length() < 6) {
                    edit_text_password.setError("Plese enter valid password");
                } else if (editTextConfirmPassword.getText().toString().trim().isEmpty()) {
                    editTextConfirmPassword.setError("Please enter confirm password");
                    editTextConfirmPassword.requestFocus();
                    showError(editTextConfirmPassword);
                } else if (!edit_text_password.getText().toString().equals(editTextConfirmPassword.getText().toString())) {
                    Toast.makeText(context, "Password and confirm doesn't match", Toast.LENGTH_SHORT).show();
                } else {
                    UserSignUpModel userSignUpModel = new UserSignUpModel();
                    userSignUpModel.setContact_no(edit_text_mobile.getText().toString());
                    userSignUpModel.setUser_name(edit_text_name.getText().toString());
                    userSignUpModel.setEmail_id(edit_text_email.getText().toString());
                    userSignUpModel.setPassword(editTextConfirmPassword.getText().toString());
                    userSignUpModel.setUser_id(edit_text_email.getText().toString());
                    usersTable(userSignUpModel);
                }
            }
        });
    }

    private boolean isValidMobile(String phone) {
        return phone.length() == 10 && android.util.Patterns.PHONE.matcher(phone).matches();
    }

    private void usersTable(UserSignUpModel userSignUpModel) {
        Intent intent = new Intent(RegistrationActivity.this, OtpFragment.class);
        Gson gson = new Gson();
        String studentDataObjectAsAString = gson.toJson(userSignUpModel);
        intent.putExtra("MyStudentObjectAsString", studentDataObjectAsAString);
        startActivity(intent);
    }


    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    public boolean isValidEmail(String target) {
        return target != null && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    private void showError(EditText editText) {
        Animation shake = AnimationUtils.loadAnimation(context, R.anim.shake);
        editText.startAnimation(shake);
    }
}
   