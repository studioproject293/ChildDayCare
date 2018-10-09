package com.example.abhinav_rapidbox.childdaycare.pojo;

import java.util.List;

/**
 * Created by vikram jha on 9/24/2018.
 */

public class DirectionResults {
    private List<GeocodedWaypoint> geocodedWaypoints = null;
    private List<Route> routes = null;
    private String status;

    public List<GeocodedWaypoint> getGeocodedWaypoints() {
        return geocodedWaypoints;
    }

    public void setGeocodedWaypoints(List<GeocodedWaypoint> geocodedWaypoints) {
        this.geocodedWaypoints = geocodedWaypoints;
    }

    public List<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(List<Route> routes) {
        this.routes = routes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
