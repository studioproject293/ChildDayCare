package com.example.abhinav_rapidbox.childdaycare.cache;


import com.example.abhinav_rapidbox.childdaycare.pojo.ChildData;
import com.example.abhinav_rapidbox.childdaycare.pojo.DayCareListModel;

import java.util.ArrayList;


public class AppCache {
    public ArrayList<ChildData> getChildDataArrayList() {
        return childDataArrayList;
    }

    public void setChildDataArrayList(ArrayList<ChildData> childDataArrayList) {
        this.childDataArrayList = childDataArrayList;
        /*if (this.childDataArrayList == null) {
            this.childDataArrayList = childDataArrayList;
        } else {
            this.childDataArrayList.addAll(childDataArrayList);
        }*/
    }

    private static AppCache cache;

    private ArrayList<DayCareListModel> dayCareListModels;
    private ArrayList<ChildData> childDataArrayList;
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
