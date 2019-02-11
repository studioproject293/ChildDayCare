package com.example.abhinav_rapidbox.childdaycare.pojo;

import java.util.ArrayList;

/**
 * Created by vikram jha on 8/12/2018.
 */

public class ChildSignUp {
    private String userid;
    private String fathers_name;
    private String mothers_name;
    private String contact_no;
    private String address;
    private String email_id;

    private ArrayList<ChildData>arrayListChild;

    public ArrayList<ChildData> getArrayListChild() {
        return arrayListChild;
    }

    public void setArrayListChild(ArrayList<ChildData> arrayListChild) {
        this.arrayListChild = arrayListChild;
    }



    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getFathers_name() {
        return fathers_name;
    }

    public void setFathers_name(String fathers_name) {
        this.fathers_name = fathers_name;
    }

    public String getMothers_name() {
        return mothers_name;
    }

    public void setMothers_name(String mothers_name) {
        this.mothers_name = mothers_name;
    }

    public String getContact_no() {
        return contact_no;
    }

    public void setContact_no(String contact_no) {
        this.contact_no = contact_no;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail_id() {
        return email_id;
    }

    public void setEmail_id(String email_id) {
        this.email_id = email_id;
    }


}
