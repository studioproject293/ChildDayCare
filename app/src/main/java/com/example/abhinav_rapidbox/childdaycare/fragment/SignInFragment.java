package com.example.abhinav_rapidbox.childdaycare.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
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
import com.example.abhinav_rapidbox.childdaycare.utill.AppConstant;
import com.example.abhinav_rapidbox.childdaycare.utill.DialogUtil;


public class SignInFragment extends BaseFragment implements EventListner{

    Button button_register;
    TextView signUp;
    PrefManager prefManager;
    private View root_view;
    private EditText editText_password, editText_emailID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root_view = inflater.inflate(R.layout.fragment_login, container, false);
        prefManager = PrefManager.getInstance();
        setId();


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
                    DialogUtil.displayProgress(getActivity());
                    SiginInModel siginInModel=new SiginInModel();
                    siginInModel.setUser_id(editText_emailID.getText().toString());
                    siginInModel.setPassword(password);
                    TransportManager.getInstance(SignInFragment.this).signInService(getActivity(),siginInModel);
                }


            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onFragmentInteraction(AppConstant.FRAGMENT_USER_SIGNUP,null);
            }
        });
        return root_view;
    }

    private void setId() {
        editText_emailID = root_view.findViewById(R.id.editText_emailID);
        editText_password = root_view.findViewById(R.id.editText_password);
        button_register = root_view.findViewById(R.id.button_register);
        signUp = root_view.findViewById(R.id.signUp);
    }


    @Override
    public void onResume() {
        super.onResume();
    }


    private boolean isValidMobile(String phone) {
        return phone.length() == 10 && android.util.Patterns.PHONE.matcher(phone).matches();
    }

    private void showError(EditText editText) {
        Animation shake = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
        editText.startAnimation(shake);
    }

    @Override
    public void onSuccessResponse(int reqType, Result data) {
        switch (reqType){
            case ApiServices.REQUEST_SIGININ_USER :
                UserSignUpModel userSignUpModel= (UserSignUpModel) data.getData();
                prefManager.setUsername(userSignUpModel.getUser_name());
                break;
        }
        DialogUtil.stopProgressDisplay();
    }

    @Override
    public void onFailureResponse(int reqType, Result data) {
        DialogUtil.stopProgressDisplay();
        Toast.makeText(getActivity(), data.getMessage(), Toast.LENGTH_SHORT).show();
    }
}
