package com.example.abhinav_rapidbox.childdaycare.pojo;

import java.util.List;

/**
 * Created by vikram jha on 9/24/2018.
 */

public class GeocodedWaypoint {
    private String geocoderStatus;
    private String placeId;
    private List<String> types = null;

    public String getGeocoderStatus() {
        return geocoderStatus;
    }

    public void setGeocoderStatus(String geocoderStatus) {
        this.geocoderStatus = geocoderStatus;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }
}
