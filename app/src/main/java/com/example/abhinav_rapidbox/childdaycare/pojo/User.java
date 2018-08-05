package com.example.abhinav_rapidbox.childdaycare.pojo;

import android.support.annotation.NonNull;

import com.google.gson.Gson;


public class User implements Comparable<User> {
    String fatherName;
    String motherName;
    String phoneNo;
    String address;
    String emailId;
    String password;
    String childName;
    String age;
    String dateofBirth;
    String blirdGroup;

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getMotherName() {
        return motherName;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getChildName() {
        return childName;
    }

    public void setChildName(String childName) {
        this.childName = childName;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getDateofBirth() {
        return dateofBirth;
    }

    public void setDateofBirth(String dateofBirth) {
        this.dateofBirth = dateofBirth;
    }

    public String getBlirdGroup() {
        return blirdGroup;
    }

    public void setBlirdGroup(String blirdGroup) {
        this.blirdGroup = blirdGroup;
    }

    @Override
    public int compareTo(@NonNull User o) {
        if(getEmailId() != null && o.getEmailId() != null)
            return getEmailId().compareTo(o.getEmailId());
        else
            return 0;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

}



