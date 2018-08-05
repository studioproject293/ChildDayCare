package com.example.abhinav_rapidbox.childdaycare.utill;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by vikram jha on 7/11/2018.
 */

public class DialogUtil {
    static AlertDialog currentDialog;
    static ProgressDialog m_cProgressBar;

    public static void hideKeyboard(View view, Context ctx) {
        if (view != null && ctx != null) {
            InputMethodManager imm = (InputMethodManager)ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
            assert imm != null;
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


    public static void displayProgress(Activity pContext) {
        displayProgress(pContext, "Please wait..");
    }

    public static void displayProgress(Activity pContext, String message) {

        if (null == m_cProgressBar) {
            if (pContext == null) return;
            if (pContext.isFinishing())
                return;
            m_cProgressBar = new ProgressDialog(pContext);
            m_cProgressBar.setCancelable(false);
            m_cProgressBar.setMessage(message);
            m_cProgressBar.show();

        } else {
            m_cProgressBar.setMessage(message);
        }
    }

    public static void stopProgressDisplay() {
        if (null != m_cProgressBar) {
            try {
                m_cProgressBar.dismiss();
            } catch (Exception ignored) {

            }
        }
        m_cProgressBar = null;
    }

}
