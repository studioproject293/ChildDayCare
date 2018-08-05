package com.example.abhinav_rapidbox.childdaycare.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abhinav_rapidbox.childdaycare.R;


public class EnquiryFragment extends BaseFragment {

    private View root_view;
    private EditText editText_userName, editText_contactNo, editText_emailID,
            editText_comment;
    Button button_submit;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root_view = inflater.inflate(R.layout.fragment_enquiry, container, false);
        setId();


        button_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return root_view;
    }

    private void setId() {
        editText_userName = root_view.findViewById(R.id.editText_userName);
        editText_contactNo = root_view.findViewById(R.id.editText_contactNo);
        editText_emailID = root_view.findViewById(R.id.editText_emailID);
        editText_comment = root_view.findViewById(R.id.editText_comment);
        button_submit = root_view.findViewById(R.id.button_submit);

    }


    @Override
    public void onResume() {
        super.onResume();
    }


    private boolean isValidMobile(String phone) {
        return phone.length() == 10 && android.util.Patterns.PHONE.matcher(phone).matches();
    }

    private void showError(EditText editText) {
        // Animation shake = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
        //editText.startAnimation(shake);
    }
}
