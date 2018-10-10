package com.example.abhinav_rapidbox.childdaycare.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abhinav_rapidbox.childdaycare.R;
import com.example.abhinav_rapidbox.childdaycare.adapter.ProductDetailsViewPagerAdapter;
import com.example.abhinav_rapidbox.childdaycare.listner.OnFragmentListItemSelectListener;
import com.example.abhinav_rapidbox.childdaycare.pojo.DayCareDetailsModel;
import com.example.abhinav_rapidbox.childdaycare.pojo.DayCareListModel;
import com.example.abhinav_rapidbox.childdaycare.pojo.HeaderData;
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
    private TextView button_register, dayCareName, facilities;
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
        phoneNo.setOnClickListener(this);
        emailId.setOnClickListener(this);

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
        facilities = rootView.findViewById(R.id.facilities);
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
            case R.id.phoneNo:
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + phoneNo.getText().toString()));
                startActivity(intent);
                break;
            case R.id.emailId:
               /* Intent intent1 = new Intent(Intent.ACTION_SEND);
                intent1.setType("text/html");
                intent1.putExtra(Intent.EXTRA_EMAIL, emailId.getText().toString());
                intent1.putExtra(Intent.EXTRA_SUBJECT, "Subject");
                intent1.putExtra(Intent.EXTRA_TEXT, "I'm email body.");
                startActivity(Intent.createChooser(intent1, "Send Email"));*/
                sendEmail();
                break;
        }
    }

    protected void sendEmail() {
        String[] TO = {""};
        String[] CC = {""};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, emailId.getText().toString());
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your subject");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message goes here");

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getActivity(), "There is no email client installed.", Toast.LENGTH_SHORT).show();
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
        TransportManager.getInstance(this).getDayCareDetailsService(getActivity(), dayCareListModelData.getDayCare_Id());
        mListener.onFragmentUpdate(AppConstant.UPDATE_TOOLBAR, new HeaderData(dayCareListModelData.getName()));
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
                facilities.setText(dayCareDetailsModel.getFacilities());
                address.setText(dayCareDetailsModel.getStreet() + "," + dayCareDetailsModel.getLine1() + "," + dayCareDetailsModel.getLine2() + ",Pincode :" + dayCareDetailsModel.getZipCode());
                break;
        }
        DialogUtil.stopProgressDisplay();
    }

    @Override
    public void onFailureResponse(int reqType, Result data) {
        DialogUtil.stopProgressDisplay();
    }
}
