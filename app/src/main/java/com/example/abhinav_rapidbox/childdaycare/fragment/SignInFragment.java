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
import android.widget.TextView;
import android.widget.Toast;

import com.example.abhinav_rapidbox.childdaycare.R;
import com.example.abhinav_rapidbox.childdaycare.pojo.SiginInModel;
import com.example.abhinav_rapidbox.childdaycare.pojo.UserSignUpModel;
import com.example.abhinav_rapidbox.childdaycare.service.ApiServices;
import com.example.abhinav_rapidbox.childdaycare.service.EventListner;
import com.example.abhinav_rapidbox.childdaycare.service.Result;
import com.example.abhinav_rapidbox.childdaycare.service.TransportManager;
import com.example.abhinav_rapidbox.childdaycare.utill.AppConstant;
import com.example.abhinav_rapidbox.childdaycare.utill.DialogUtil;
import com.example.abhinav_rapidbox.childdaycare.utill.PrefManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class SignInFragment extends BaseFragment implements EventListner{

    private View root_view;
    private EditText editText_password, editText_emailID;
    Button button_register;
    TextView signUp;
    private FirebaseAuth auth;
    PrefManager prefManager;

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
                    siginInModel.setUser_id("1");
                    siginInModel.setPassword(password);
                    TransportManager.getInstance(SignInFragment.this).signInService(getActivity(),siginInModel);
                }

               /* auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                *//*progressBar.setVisibility(View.GONE);*//*
                                if (!task.isSuccessful()) {
                                    // there was an error
                                    if (password.length() < 6) {
                                        editText_password.setError(getString(R.string.minimum_password));
                                    } else {
                                        Toast.makeText(getActivity(), getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    *//*Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();*//*
                                    //where should go

                                }
                            }
                        });*/
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
                userSignUpModel.setUser_name(userSignUpModel.getUser_name());
                break;
        }
        DialogUtil.stopProgressDisplay();
    }

    @Override
    public void onFailureResponse(int reqType, Result data) {

    }
}
