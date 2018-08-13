package com.example.abhinav_rapidbox.childdaycare.cache;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Atmaram on 08-06-2017.
 */

public class PrefManager {

    SharedPreferences pref;
    Context _context;
    SharedPreferences.Editor editor;

    int PRIVATE_MODE = 0;

    static PrefManager manager;

    static final String PREF_USER_NAME = "username";
    static final String PREF_USER_ID = "userId";
    static final String PREF_ADDRESS = "address";
    static final String PREF_EMAIL = "email";
    static final String PREF_CONTACT = "contact";
    static final String PREF_ROADNO ="roadNo";
    static final String PREF_CITY ="city";
    static final String PREF_APARTMENTNAME ="apartmentName";
    static final String PREF_PIN ="pin";
    static final String PREF_IMAGE_LINK ="imagelink";
    public static final String KEY_DEVICEID = "deviceId";
    public static final String KEY_GCM_TOKEN = "gcmId";
    private static final String KEY_REG_TOKEN = "regToken";
    private static final String KEY_DEVICE_UPDATE = "deviceupdate";
    private static final String KEY_SHOWCASE = "showcase";
    private static final String OrderNo ="OrderNo";
    private static final String PROFILE_IMAGE_LINK="profile";
    private static final String PREF_NAME = "consumer";

    public void init(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public static PrefManager getInstance(){
        if(manager == null)
        manager =  new PrefManager();
        return manager;
    }

    public String getGCMId() {
        return pref.getString(KEY_GCM_TOKEN, "");
    }

    public void setGcmId(String gcmid) {
        editor.putString(KEY_GCM_TOKEN, gcmid);
        editor.commit();
    }
    public String getOrderNo() {
        return pref.getString(OrderNo, null);
    }

    public void setOrderNo(String orderNo) {
        editor.putString(OrderNo, orderNo);
        editor.commit();
    }

    public void setDeviceUpdate(String deviceUpdate)
    {
        editor.putString(KEY_DEVICE_UPDATE, deviceUpdate);
        editor.commit();
    }

    public String getDeviceUpdate(){
        return pref.getString(KEY_DEVICE_UPDATE,"U");
    }



    public String getUsername() {
        return pref.getString(PREF_USER_NAME, null);
    }

    public void setProfileImageLink(String userName) {
        editor.putString(PROFILE_IMAGE_LINK, userName);
        editor.commit();
    }
    public String getProfileImageLink() {
        return pref.getString(PROFILE_IMAGE_LINK, null);
    }

    public void setUsername(String userName) {
        editor.putString(PREF_USER_NAME, userName);
        editor.commit();
    }
    public String getPrefImageLink() {
        return pref.getString(PREF_IMAGE_LINK, null);
    }

    public void setPrefImageLink(String imageLink) {
        editor.putString(PREF_IMAGE_LINK, imageLink);
        editor.commit();
    }
    public String getApartmentname() {
        return pref.getString(PREF_APARTMENTNAME, null);
    }

    public void setApartmentname(String userApartmnet) {
        editor.putString(PREF_APARTMENTNAME, userApartmnet);
        editor.commit();
    }
    public String getRoadno() {
        return pref.getString(PREF_ROADNO, null);
    }

    public void setRoadNo(String roadNo) {
        editor.putString(PREF_ROADNO, roadNo);
        editor.commit();
    }
    public String getPincode() {
        return pref.getString(PREF_PIN, null);
    }

    public void setPincode(String pincode) {
        editor.putString(PREF_PIN, pincode);
        editor.commit();
    }
    public String getCity() {
        return pref.getString(PREF_CITY, null);
    }

    public void setCity(String city) {
        editor.putString(PREF_CITY, city);
        editor.commit();
    }
    public int getUserId() {
        return pref.getInt(PREF_USER_ID, 0);
    }

    public void setUserId(int count) {
        editor.putInt(PREF_USER_ID, count);
        editor.commit();
    }

    public String getAddress() {
        return pref.getString(PREF_ADDRESS, null);
    }

    public void setAddress(String address) {
        editor.putString(PREF_ADDRESS, "" + address);
        editor.commit();
    }
  public String getContact() {
        return pref.getString(PREF_CONTACT, null);
    }

    public void setContact(String contact) {
        editor.putString(PREF_CONTACT, "" + contact);
        editor.commit();
    }
  public String getEmail() {
        return pref.getString(PREF_EMAIL, null);
    }

    public void setEmail(String email) {
        editor.putString(PREF_EMAIL, "" + email);
        editor.commit();
    }

    public void removeValue() {
        editor.remove(PREF_USER_NAME);
        editor.commit();
    }


    public void setShowCase(boolean shown) {
        editor.putBoolean(KEY_SHOWCASE, shown);
        editor.commit();
    }
    public boolean idShowCased() {
        return pref.getBoolean(KEY_SHOWCASE, false);
    }


}
