package com.example.abhinav_rapidbox.childdaycare.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.abhinav_rapidbox.childdaycare.R;
import com.example.abhinav_rapidbox.childdaycare.pojo.DayCareListModel;
import com.example.abhinav_rapidbox.childdaycare.utill.AppConstant;


public class ProductDetailsFragment extends BaseFragment implements View.OnClickListener {
    private TextView button_register;
    LinearLayout enquiryLayout;
    private View rootView;
    TextView address, phoneNo, emailId, chatNo, price;
    static DayCareListModel dayCareListModelData;

    public static ProductDetailsFragment newInstance(DayCareListModel dayCareListModel) {
        dayCareListModelData = dayCareListModel;
        return new ProductDetailsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_blank, container, false);
        setId();
        button_register.setOnClickListener(this);
        enquiryLayout.setOnClickListener(this);
       // phoneNo.setText(dayCareListModelData.get);
        //emailId.setText(dayCareListModelData.getE);

        return rootView;
    }

    private void setId() {
        button_register = rootView.findViewById(R.id.button_register);
        enquiryLayout = rootView.findViewById(R.id.enquiryLayout);
        price = rootView.findViewById(R.id.price);
        chatNo = rootView.findViewById(R.id.chatNo);
        phoneNo = rootView.findViewById(R.id.phoneNo);
        chatNo = rootView.findViewById(R.id.chatNo);
        address = rootView.findViewById(R.id.address);
        emailId = rootView.findViewById(R.id.emailId);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_register:
                mListener.onFragmentInteraction(AppConstant.SignupFragment, null);
                break;
            case R.id.enquiryLayout:
                mListener.onFragmentInteraction(AppConstant.ENQUIRY_FRAGMENT, null);
                break;
        }
    }
}
