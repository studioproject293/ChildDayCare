package com.example.abhinav_rapidbox.childdaycare.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.abhinav_rapidbox.childdaycare.listner.OnFragmentInteractionListener;

import static android.content.ContentValues.TAG;


public abstract class BaseFragment extends Fragment {
    public final int PERMISSION_REQUEST = 100;
    public final int MAX_COUNTER = 2;
    public OnFragmentInteractionListener mListener;
    protected AppCompatActivity mActivity;
    int permissionMode;
    int permissionCounter;

    public static boolean isValidEmail(CharSequence email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        // QLog.m11v("Class:" + getClass().getSimpleName());
        super.onViewCreated(view, savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //      // QLog.m11v("Class:" + getClass().getSimpleName());
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void onAttach(Activity activity) {
        //    // QLog.m11v("Class:" + getClass().getSimpleName());
        super.onAttach(activity);
        mActivity = (AppCompatActivity) activity;
    }

    public void onCreate(Bundle savedInstanceState) {
        //  // QLog.m11v("Class:" + getClass().getSimpleName());
        super.onCreate(savedInstanceState);
    }

    public void onInflate(Activity activity, AttributeSet attrs, Bundle savedInstanceState) {
        //// // QLog.m11v("Class:" + getClass().getSimpleName());
        super.onInflate(activity, attrs, savedInstanceState);
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        // // QLog.m11v("Class:" + getClass().getSimpleName());
        super.onActivityCreated(savedInstanceState);
    }

    public void onStart() {
        // // QLog.m11v("Class:" + getClass().getSimpleName());
        super.onStart();
    }

    public void onResume() {
        // QLog.m11v("Class:" + getClass().getSimpleName());
        super.onResume();
    }

    public void onPause() {
        // QLog.m11v("Class:" + getClass().getSimpleName());
        super.onPause();
    }

    public void onStop() {
        // QLog.m11v("Class:" + getClass().getSimpleName());
        super.onStop();
    }

    public void onDestroyView() {
        // QLog.m11v("Class:" + getClass().getSimpleName());
        super.onDestroyView();
    }

    public void onDestroy() {
        // QLog.m11v("Class:" + getClass().getSimpleName());
        super.onDestroy();
    }

    public void onLowMemory() {
        // QLog.m11v("Class:" + getClass().getSimpleName());
        super.onLowMemory();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            //if(com.olive.upi.transport.TransportConstants.appRelease) Log.d("", "onAttach: mListener is not OnFragmentInteractionListener "+mListener);
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        mActivity = null;
    }


//    public void setUpToolBar(String title) {
//        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
//        TextView screenTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
//        screenTitle.setText(title);
//    }


//    public void setmScreenTitle(String title){
//        this.title = title;
//    }

    public AppCompatActivity getmActivity() {
        return mActivity;
    }

    public void permissionGranted(int mode) {

    }

    public boolean checkForPermission(String permission, int mode) {
        try {
            this.permissionMode = mode;
            permissionCounter++;
            int permissionCheck = ContextCompat.checkSelfPermission(getActivity(), permission);
            Log.d(TAG, "checkForPermission: permissionCheck " + permissionCheck);
            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), permission)) {
                    // Show an expanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.
//                    Log.d(TAG, "checkForPermission: shouldShowRequestPermissionRationale ");
//                    if (PrefManager.getInstance(getContext()).getKeyPermissionDenied()) {
//                        permissionDenied(mode);
//                    }
                } else {
                    // No explanation needed, we can request the permission.
                    requestPermissions(new String[]{permission}, PERMISSION_REQUEST);
                }
                return false;
            } else {
                permissionDenied(mode);
                return true;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public void requestAgain(String permissions) {
        permissionCounter++;
        requestPermissions(new String[]{permissions}, PERMISSION_REQUEST);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        Log.d("Permission", "PermisionCode" + requestCode);
        switch (requestCode) {
            case PERMISSION_REQUEST: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    permissionGranted(permissionMode);
//                    PrefManager.getInstance(getContext()).setPermissionDenied(false);
                } else {
                    // functionality depends on this permission.
                    Log.d("Permission", "Permision" + permissionCounter);
                    if (permissionCounter < MAX_COUNTER) {
                        permissionCounter++;
                        requestPermissions(permissions, PERMISSION_REQUEST);
                    } else {
//                        PrefManager.getInstance(getContext()).setPermissionDenied(true);
//                        Toast.makeText(getActivity(), "Required Permissions to be Granted to access this feature !!!", Toast.LENGTH_LONG).show();
                    }
                }
                return;
            }
        }
    }


    public void permissionDenied(int mode) {

    }
}
