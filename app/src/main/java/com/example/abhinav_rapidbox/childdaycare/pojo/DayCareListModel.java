package com.example.abhinav_rapidbox.childdaycare.pojo;

import android.support.annotation.NonNull;

/**
 * Created by vikram jha on 7/11/2018.
 */

public class DayCareListModel implements Comparable<DayCareListModel> {
    private Integer id;
    private String code;
    private String name;
    private Integer rating;
    private Double latitude;
    private Double longitude;
    private Integer fee;
    String homeImage;
    String feeOrNot;

    public String getFeeOrNot() {
        return feeOrNot;
    }

    public void setFeeOrNot(String feeOrNot) {
        this.feeOrNot = feeOrNot;
    }

    public String getHomeImage() {
        return homeImage;
    }

    public void setHomeImage(String homeImage) {
        this.homeImage = homeImage;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Integer getFee() {
        return fee;
    }

    public void setFee(Integer fee) {
        this.fee = fee;
    }



    @Override
    public int compareTo( DayCareListModel dayCareListModel) {
        int compareRating = ((DayCareListModel) dayCareListModel).getRating();
        int compareFee = ((DayCareListModel) dayCareListModel).getFee();
        //ascending order
        if (this.feeOrNot.equals("yes"))
            return this.rating - compareRating;
        else
            return this.fee - compareFee;
    }
}
