package com.example.abhinav_rapidbox.childdaycare.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.abhinav_rapidbox.childdaycare.R;
import com.example.abhinav_rapidbox.childdaycare.cache.PrefManager;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;


public class PaymentActivity extends AppCompatActivity implements PaymentResultListener {
    private static final String TAG = PaymentActivity.class.getSimpleName();
    PrefManager prefManager;
    private SharedPreferences mMemory;
    private SharedPreferences.Editor mEdt;
    private String discountPrice, r_OfferCode, totalAmount, r_SellerName;
    private RelativeLayout relSuccess, relFail;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.content_payment);
        prefManager = PrefManager.getInstance();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*
         To ensure faster loading of the Checkout form,
          call this method as early as possible in your checkout flow.
         */
        Checkout.preload(getApplicationContext());

        mMemory = getSharedPreferences("memory", Context.MODE_PRIVATE);

        discountPrice = "10";
        relSuccess = (RelativeLayout) findViewById(R.id.relSuccess);
        relFail = (RelativeLayout) findViewById(R.id.relFail);

        startPayment();
    }

    public void startPayment() {

        double pAmount = Double.valueOf(discountPrice);
        double payDouble = pAmount * 100;
        String paymentAmount = String.valueOf(payDouble);

        /*
          You need to pass current activity in order to let Razorpay create CheckoutActivity
         */
        final Activity activity = this;

        final Checkout co = new Checkout();

        co.setImage(R.mipmap.ic_launcher);

        try {
            JSONObject options = new JSONObject();
            options.put("name", "NiNOS");
            options.put("description", "PAYMENT ");
            //You can omit the image option to fetch the image from dashboard
            options.put("currency", "INR");
            options.put("amount", paymentAmount);

            JSONObject preFill = new JSONObject();
            preFill.put("email", prefManager.getEmail());
            preFill.put("contact", prefManager.getContact());

            options.put("prefill", preFill);

            JSONObject theme = new JSONObject();
            theme.put("color", "#5BC5A7");//For Example #F37254
            options.put("theme", theme);

            co.open(activity, options);
        } catch (Exception e) {
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT)
                    .show();
            e.printStackTrace();
        }
    }

    /**
     * The name of the function has to be
     * onPaymentSuccess
     * Wrap your code in try catch, as shown, to ensure that this method runs correctly
     */
    @SuppressWarnings("unused")
    @Override
    public void onPaymentSuccess(String razorpayPaymentID) {
        try {
            relSuccess.setVisibility(View.VISIBLE);
            final Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {

                    startActivity(intent);
                    finish();

                }
            }, 500);
//            Toast.makeText(this, "Payment Successful: " + razorpayPaymentID, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e(TAG, "Exception in onPaymentSuccess", e);
        }
    }

    /**
     * The name of the function has to be
     * onPaymentError
     * Wrap your code in try catch, as shown, to ensure that this method runs correctly
     */
    @SuppressWarnings("unused")
    @Override
    public void onPaymentError(int code, String response) {
        try {
            relFail.setVisibility(View.VISIBLE);
            final Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {

                    startActivity(intent);
                    finish();

                }
            }, 500);
//            Toast.makeText(this, "Payment failed: " + code + " " + response, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e(TAG, "Exception in onPaymentError", e);
        }
    }


}