package com.example.abhinav_rapidbox.childdaycare.utill;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by vikram jha on 7/11/2018.
 */

public class Constants {
    public static final String API_BASE_URL="http://ec2-18-222-198-193.us-east-2.compute.amazonaws.com:8080/";
    public static final String NO_INTERNET = "Unable to connect. Please check your Internet Connection.";
    public static final String ERROR_PARSING = "101";
    public static final String ERROR_SERVER = "102";
    public static String dateConversion(String dateInput){
        DateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy");
        DateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");

        String inputDateStr = dateInput;
        Date date = null;
        try {
            date = inputFormat.parse(inputDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String outputDateStr = outputFormat.format(date);
        return outputDateStr;
    }

}
