package com.example.abhinav_rapidbox.childdaycare.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.abhinav_rapidbox.childdaycare.R;
import com.example.abhinav_rapidbox.childdaycare.adapter.ProductDetailsViewPagerAdapter;
import com.example.abhinav_rapidbox.childdaycare.listner.OnFragmentListItemSelectListener;
import com.example.abhinav_rapidbox.childdaycare.pojo.DayCareDetailsModel;
import com.example.abhinav_rapidbox.childdaycare.pojo.DayCareListModel;
import com.example.abhinav_rapidbox.childdaycare.pojo.ZoomImage;
import com.example.abhinav_rapidbox.childdaycare.service.ApiServices;
import com.example.abhinav_rapidbox.childdaycare.service.EventListner;
import com.example.abhinav_rapidbox.childdaycare.service.Result;
import com.example.abhinav_rapidbox.childdaycare.service.TransportManager;
import com.example.abhinav_rapidbox.childdaycare.utill.AppConstant;
import com.example.abhinav_rapidbox.childdaycare.utill.DialogUtil;
import com.tbuonomo.viewpagerdotsindicator.SpringDotsIndicator;

import java.util.ArrayList;


public class ProductDetailsFragment extends BaseFragment implements View.OnClickListener, OnFragmentListItemSelectListener, EventListner {
    static DayCareListModel dayCareListModelData;
    LinearLayout enquiryLayout;
    TextView address, phoneNo, emailId, chatNo, price;
    SpringDotsIndicator springDotsIndicator;
    ViewPager product_viewPager;
    ProductDetailsViewPagerAdapter productDetailsViewPagerAdapter;
    private TextView button_register, dayCareName;
    private View rootView;

    public static ProductDetailsFragment newInstance(DayCareListModel dayCareListModel) {
        dayCareListModelData = dayCareListModel;
        return new ProductDetailsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_blank, container, false);
        setId();
        button_register.setOnClickListener(this);
        enquiryLayout.setOnClickListener(this);
        dayCareName.setText(dayCareListModelData.getName());

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
        dayCareName = rootView.findViewById(R.id.dayCareName);
        product_viewPager = rootView.findViewById(R.id.product_viewPager);
        springDotsIndicator = rootView.findViewById(R.id.dots_indicator);
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

    private void productImagedata(ArrayList<String> otherImages) {
        if (otherImages != null && otherImages.size() > 0) {
            productDetailsViewPagerAdapter = new ProductDetailsViewPagerAdapter(getActivity(), otherImages);
            product_viewPager.setAdapter(productDetailsViewPagerAdapter);
            productDetailsViewPagerAdapter.setListner(this);
            springDotsIndicator.setViewPager(product_viewPager);
            final int position = product_viewPager.getCurrentItem();

        }
    }

    @Override
    public void onListItemSelected(int itemId, Object data) {
        switch (itemId) {
            case R.id.img_product:
                // ArrayList<String> list = (ArrayList<String>) data;
                ZoomImage zoomImage = (ZoomImage) data;
                zoomImage.getImageList();
                zoomImage.getPosition();
                mListener.onFragmentInteraction(AppConstant.IMAGEFULLSCREENFRAGMENT, zoomImage);
                break;
        }
    }

    @Override
    public void onListItemLongClicked(int itemId, Object data) {

    }

    @Override
    public void onResume() {
        super.onResume();
        TransportManager.getInstance(this).getDayCareDetailsService(getActivity(), dayCareListModelData.getId());
    }

    @Override
    public void onSuccessResponse(int reqType, Result data) {
        switch (reqType) {
            case ApiServices.REQUEST_DAYCARE_DETAILS:
                DayCareDetailsModel dayCareDetailsModel = (DayCareDetailsModel) data.getData();
                productImagedata(dayCareDetailsModel.getImageUrlList());
                emailId.setText(dayCareDetailsModel.getEmailId());
                phoneNo.setText(dayCareDetailsModel.getPhoneNo());
                chatNo.setText(dayCareDetailsModel.getPhoneNo());
                price.setText(dayCareDetailsModel.getFeeStructure());
                address.setText(dayCareDetailsModel.getStreet() + " ," + dayCareDetailsModel.getLine1() + " ," + dayCareDetailsModel.getLine2() + ",Pincode :" + dayCareDetailsModel.getZipCode());
                break;
        }
        DialogUtil.stopProgressDisplay();
    }

    @Override
    public void onFailureResponse(int reqType, Result data) {
        DialogUtil.stopProgressDisplay();
    }
}
