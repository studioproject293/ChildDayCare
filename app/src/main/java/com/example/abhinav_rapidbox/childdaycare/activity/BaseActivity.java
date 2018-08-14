package com.example.abhinav_rapidbox.childdaycare.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

import com.example.abhinav_rapidbox.childdaycare.cache.PrefManager;
import com.example.abhinav_rapidbox.childdaycare.utill.DialogUtil;

import java.util.Objects;


public class BaseActivity extends AppCompatActivity {
    public static final int SESSION_TIMEOUT = 3 * 60 * 1000;
    public static final String FINISH_ALL_ACTIVITIES_ACTIVITY_ACTION = "com.upi.FINISH_ALL_ACTIVITIES_ACTIVITY_ACTION";
    public static final IntentFilter INTENT_FILTER = createIntentFilter();
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    protected static long appBackgroundTime;
    protected static boolean mPaused;
    protected static boolean mSensitive;

    static {
        mPaused = false;
        mSensitive = true;
    }

    int screenName = 0;
    PrefManager prefManager;
    boolean simState = false;
    private BaseActivityReceiver baseActivityReceiver = new BaseActivityReceiver();

    public BaseActivity() {
        String ACTION = "android.net.conn.CONNECTIVITY_CHANGE";
    }

    /*private void updateFCMId() {
        PrefManager pref = new PrefManager(this);
        String gcmId = pref.getGCMId();
        if (TextUtils.isEmpty(gcmId)) {
            String systemId = FirebaseInstanceId.getInstance().getToken();
            if (!TextUtils.isEmpty(systemId)) {
                pref.setGcmId(systemId);
                pref.setDeviceUpdate("U");
            }
        }
    }*/

    /*private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.i("", "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }*/

    private static IntentFilter createIntentFilter() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(FINISH_ALL_ACTIVITIES_ACTIVITY_ACTION);
        return filter;
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //Fabric.with(this, new Crashlytics());
//       Thread.setDefaultUncaughtExceptionHandler(new C01641());
        simState = false;
        IntentFilter filter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
//        this.mReceiver = new InternetConnectionReceiver(this);
      /*  if (checkPlayServices()) {*/
        // Start IntentService to register this application with GCM.
//            Intent intent = new Intent(this, RegistrationIntentService.class);
//            startService(intent);

        //GCMManager gcmManager = new GCMManager((UPIApplication) getApplication());
        //gcmManager.manage();
    /*}*/

        //Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        registerBaseActivityReceiver();
      /*  updateFCMId();*/
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    protected void onDestroy() {
        super.onDestroy();
        unRegisterBaseActivityReceiver();
        DialogUtil.stopProgressDisplay();
    }

    public void onBackPressed() {
        super.onBackPressed();
    }

    protected void onPause() {
        super.onPause();
        mPaused = true;
    }

    protected void onStop() {
        super.onStop();
        mPaused = false;
        appBackgroundTime = System.currentTimeMillis();

    }

    protected void onResume() {
        super.onResume();

        prefManager = PrefManager.getInstance();
        //appBackgroundTime = prefManager.getBackgroundTime();

//        registerReceiver(messageReceiver, new IntentFilter("android.intent.action.SIM_STATE_CHANGED"));

        if (!mSensitive) {
            mSensitive = true;
            mPaused = false;
        } else if (mPaused) {
            mPaused = false;
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //QLog.m11v("Class:" + getClass().getSimpleName());
        super.onActivityResult(requestCode, resultCode, data);
    }

    protected void registerBaseActivityReceiver() {
        registerReceiver(baseActivityReceiver, INTENT_FILTER);
    }

    protected void unRegisterBaseActivityReceiver() {
        unregisterReceiver(baseActivityReceiver);
    }

    protected void closeAllActivities() {
//        Log.d("", "closeAllActivities: called");
        sendBroadcast(new Intent(FINISH_ALL_ACTIVITIES_ACTIVITY_ACTION));
    }

    class androidDefaultUEH implements Thread.UncaughtExceptionHandler {
        androidDefaultUEH() {
        }

        public void uncaughtException(Thread paramThread, Throwable paramThrowable) {
            paramThrowable.getMessage();
            System.exit(0);
        }
    }

    public class BaseActivityReceiver extends BroadcastReceiver {
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public void onReceive(Context context, Intent intent) {
            if (Objects.equals(intent.getAction(), FINISH_ALL_ACTIVITIES_ACTIVITY_ACTION)) {
//                Log.d("BaseActivity ", "onReceive: finishing.....");
                finish();
            }
        }
    }

}
