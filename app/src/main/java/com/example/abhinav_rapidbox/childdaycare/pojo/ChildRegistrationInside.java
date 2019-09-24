package com.example.abhinav_rapidbox.childdaycare.pojo;

import java.util.ArrayList;

public class ChildRegistrationInside {
    private int userID;
    private String userName;
    private String email_id;
    private String father_name;
    private String mother_name;
    private String contact_no;
    ArrayList<ChildData>childs;

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail_id() {
        return email_id;
    }

    public void setEmail_id(String email_id) {
        this.email_id = email_id;
    }

    public String getFather_name() {
        return father_name;
    }

    public void setFather_name(String father_name) {
        this.father_name = father_name;
    }

    public String getMother_name() {
        return mother_name;
    }

    public void setMother_name(String mother_name) {
        this.mother_name = mother_name;
    }

    public String getContact_no() {
        return contact_no;
    }

    public void setContact_no(String contact_no) {
        this.contact_no = contact_no;
    }

    public ArrayList<ChildData> getChilds() {
        return childs;
    }

    public void setChilds(ArrayList<ChildData> childs) {
        this.childs = childs;
    }
}
