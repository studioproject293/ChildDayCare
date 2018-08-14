package com.example.abhinav_rapidbox.childdaycare.cache;

import com.example.abhinav_rapidbox.childdaycare.pojo.DayCareListModel;

import java.util.ArrayList;


public class AppCache {
    static AppCache cache;

    private ArrayList<DayCareListModel> dayCareListModels;
    public static AppCache getInstance() {
        if (cache == null) {
            cache = new AppCache();
        }
        return cache;
    }

    public ArrayList<DayCareListModel> getDayCareListModels() {
        return dayCareListModels;
    }

    public void setDayCareListModels(ArrayList<DayCareListModel> dayCareListModels) {
        this.dayCareListModels = dayCareListModels;
    }
}