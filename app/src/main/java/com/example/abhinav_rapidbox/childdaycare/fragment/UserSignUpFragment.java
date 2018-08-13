package com.example.abhinav_rapidbox.childdaycare.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.abhinav_rapidbox.childdaycare.R;
import com.example.abhinav_rapidbox.childdaycare.pojo.UserSignUpModel;
import com.example.abhinav_rapidbox.childdaycare.utill.AppConstant;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class UserSignUpFragment extends BaseFragment {

    private View root_view;
    private EditText edit_text_name, edit_text_mobile, edit_text_email, edit_text_password, editTextConfirmPassword;
    Button button_register;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root_view = inflater.inflate(R.layout.fragment_registration, container, false);
        setId();
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
                    Toast.makeText(getActivity(), "Password and confirm doesn't match", Toast.LENGTH_SHORT).show();
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

        return root_view;
    }

    private void usersTable(UserSignUpModel userSignUpModel) {
        mListener.onFragmentInteraction(AppConstant.FRAGMENT_OTP, userSignUpModel);
    }

    private void setId() {
        button_register = root_view.findViewById(R.id.button_register);
        edit_text_name = root_view.findViewById(R.id.editText_userName);
        edit_text_mobile = root_view.findViewById(R.id.editText_phoneNo);
        edit_text_email = root_view.findViewById(R.id.editText_email);
        edit_text_password = root_view.findViewById(R.id.editText_password);
        editTextConfirmPassword = root_view.findViewById(R.id.confirmPassword);
    }


    @Override
    public void onResume() {
        super.onResume();
    }


    private boolean isValidMobile(String phone) {
        return phone.length() == 10 && android.util.Patterns.PHONE.matcher(phone).matches();
    }

    public static UserSignUpFragment newInstance() {
        return new UserSignUpFragment();
    }

    private void showError(EditText editText) {
        Animation shake = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
        editText.startAnimation(shake);
    }
}
