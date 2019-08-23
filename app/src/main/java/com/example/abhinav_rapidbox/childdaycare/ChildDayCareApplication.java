package com.example.abhinav_rapidbox.childdaycare;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.support.multidex.MultiDex;

import com.example.abhinav_rapidbox.childdaycare.activity.MainActivity;
import com.example.abhinav_rapidbox.childdaycare.cache.PrefManager;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;


public class ChildDayCareApplication extends Application {

    Thread.UncaughtExceptionHandler defHandler;
    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath(getString(R.string.app_font))
                .setFontAttrId(R.attr.fontPath)
                .build());
        PrefManager.getInstance().init(this);
        defHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler());
    }





    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }


    class ExceptionHandler implements
            java.lang.Thread.UncaughtExceptionHandler {

        public ExceptionHandler() {
        }

        public void uncaughtException(Thread thread, Throwable exception) {
            defHandler.uncaughtException(thread, exception);
           //Crashlytics.logException(exception);

            exception.printStackTrace();
//            Log.d("TAG", "uncaughtException: ");
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                    | Intent.FLAG_ACTIVITY_CLEAR_TASK
                    | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
//            PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_ONE_SHOT);
//            AlarmManager mgr = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
//            mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 500, pendingIntent);
//            finish();
//            System.exit(2);
        }
    }
}
