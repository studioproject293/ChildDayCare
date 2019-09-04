package com.example.abhinav_rapidbox.childdaycare.pojo;

/**
 * Created by vikram jha on 7/11/2018.
 */

public class DayCareListModel {
    String feeOrNot;
    private String daycareId;
    private String daycareName;
    private String description;
    private Double latitude;
    private Double longitude;
    private Integer averageRating;
    private String image_url;
    private String imageName;
    private String feeStructures;
    private String city;
    private String distRadius;

    public String getDistRadius() {
        return distRadius;
    }

    public void setDistRadius(String distRadius) {
        this.distRadius = distRadius;
    }

    public String getFeeOrNot() {
        return feeOrNot;
    }

    public void setFeeOrNot(String feeOrNot) {
        this.feeOrNot = feeOrNot;
    }

    public String getDaycareId() {
        return daycareId;
    }

    public void setDaycareId(String daycareId) {
        this.daycareId = daycareId;
    }

    public String getDaycareName() {
        return daycareName;
    }

    public void setDaycareName(String daycareName) {
        this.daycareName = daycareName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Integer getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Integer averageRating) {
        this.averageRating = averageRating;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getFeeStructures() {
        return feeStructures;
    }

    public void setFeeStructures(String feeStructures) {
        this.feeStructures = feeStructures;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }


}
