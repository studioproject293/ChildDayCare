package com.example.abhinav_rapidbox.childdaycare.fragment;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.abhinav_rapidbox.childdaycare.R;
import com.example.abhinav_rapidbox.childdaycare.cache.PrefManager;
import com.example.abhinav_rapidbox.childdaycare.listner.OnFragmentListItemSelectListener;
import com.example.abhinav_rapidbox.childdaycare.pojo.UserSignUpModel;
import com.example.abhinav_rapidbox.childdaycare.service.ApiServices;
import com.example.abhinav_rapidbox.childdaycare.service.EventListner;
import com.example.abhinav_rapidbox.childdaycare.service.Result;
import com.example.abhinav_rapidbox.childdaycare.service.TransportManager;
import com.example.abhinav_rapidbox.childdaycare.utill.DialogUtil;
import com.example.abhinav_rapidbox.childdaycare.utill.imagepicker.ImageCropActivity;
import com.example.abhinav_rapidbox.childdaycare.utill.imagepicker.ImagePickerManager;

import java.io.ByteArrayOutputStream;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;


public class UserProfileFragment extends BaseFragment implements OnFragmentListItemSelectListener, EventListner {
    private static final int PERMISSION_REQUEST_CAMERA = 100;
    public static boolean isFlagattachment = false;
    private static String TAG = "Profile";
    Spinner city;
    String imageLinkrec;
    String vName, vAddress, vPhone;
    String cityName;
    CircleImageView profile;
    UserSignUpModel userSignUpModelService;
    private Context context;
    private View rootView;
    private EditText editText_vendor_name, editText_phoneNo, editText_address, editText_email,
            editText_road, editText_facalityName, editText_pincode;
    private ProgressDialog mProgress;
    private Button button_update;
    private PrefManager prefManager;


    public UserProfileFragment() {
        // Required empty public constructor
    }

    public static UserProfileFragment newInstance() {
        return new UserProfileFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        //MainActivity.setToolbarTitle("User Profile");
        DialogUtil.displayProgress(getActivity());
        TransportManager.getInstance(this).getUserInfoService(getActivity(), prefManager.getUserId());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_vendor_profile, container, false);

        context = getActivity();
        prefManager = PrefManager.getInstance();
        mProgress = new ProgressDialog(context);


        editText_vendor_name = rootView.findViewById(R.id.editText_vendor_name);
        editText_phoneNo = rootView.findViewById(R.id.editText_phoneNo);
        editText_address = rootView.findViewById(R.id.editText_address);
        editText_email = rootView.findViewById(R.id.editText_email);
        profile = rootView.findViewById(R.id.imare_user);
        button_update = rootView.findViewById(R.id.button_update);
        if (prefManager.getProfileImageLink() != null) {
            Bitmap selectedImage = BitmapFactory.decodeFile(prefManager.getProfileImageLink());
            profile.setImageBitmap(selectedImage);
        }
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attchmemntPopup(getActivity());
            }
        });
        button_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profileUpdate();
            }
        });
      /*  if (prefManager.getPrefImageLink() != null) {
            imageLoader.displayImage(Constants.IMAGE_BASE_URL + prefManager.getPrefImageLink(), profile, options);
        } else {

        }*/
        return rootView;
    }

    public void profileUpdate() {


        vName = editText_vendor_name.getText().toString().trim();
        vPhone = editText_phoneNo.getText().toString().trim();

        if (vName.isEmpty()) {
            editText_vendor_name.setError("Please enter user name");
            editText_vendor_name.requestFocus();
            showError(editText_vendor_name);
        } else if (!isValidMobile(vPhone)) {
            editText_phoneNo.setError("Please enter valid mobile number");
            editText_phoneNo.requestFocus();
            showError(editText_phoneNo);
        } else {
            DialogUtil.displayProgress(getActivity());
            UserSignUpModel userSignUpModel = new UserSignUpModel();
            userSignUpModel.setContact_no(editText_phoneNo.getText().toString());
            userSignUpModel.setUser_name(editText_vendor_name.getText().toString());
            userSignUpModel.setEmail_id(editText_email.getText().toString());
            userSignUpModel.setPassword(userSignUpModelService.getPassword());
            userSignUpModel.setUser_id(String.valueOf(prefManager.getUserId()));
            userSignUpModel.setOtp(userSignUpModelService.getOtp());
            TransportManager.getInstance(UserProfileFragment.this).saveUser(getActivity(), userSignUpModel);
        }

    }


    private boolean isValidMobile(String phone) {
        return phone.length() == 10 && android.util.Patterns.PHONE.matcher(phone).matches();
    }

    private void showError(EditText editText) {
        Animation shake = AnimationUtils.loadAnimation(context, R.anim.shake);
        editText.startAnimation(shake);
    }

    @Override
    public void onSuccessResponse(int reqType, Result data) {
        switch (reqType) {
            case ApiServices.REQUEST_USER_INFO:
                userSignUpModelService = (UserSignUpModel) data.getData();
                editText_email.setText(userSignUpModelService.getEmail_id());
                editText_phoneNo.setText(userSignUpModelService.getContact_no());
                editText_vendor_name.setText(userSignUpModelService.getUser_name());
                break;
            case ApiServices.REQUEST_USER_SIGINUP:
                Toast.makeText(context, data.getMessage(), Toast.LENGTH_SHORT).show();
                UserSignUpModel userSignUpModel1 = (UserSignUpModel) data.getData();
                prefManager.setUsername(userSignUpModel1.getUser_name());
                prefManager.setUserId(userSignUpModel1.getUser_id());
                break;
        }

        DialogUtil.stopProgressDisplay();
    }

    @Override
    public void onFailureResponse(int reqType, Result data) {
        DialogUtil.stopProgressDisplay();
        switch (reqType) {
            case ApiServices.REQUEST_USER_INFO:
                Toast.makeText(context, data.getMessage(), Toast.LENGTH_SHORT).show();
                break;
            case ApiServices.REQUEST_USER_SIGINUP:
                Toast.makeText(context, data.getMessage(), Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onListItemSelected(int itemId, Object data) {

    }

    @Override
    public void onListItemLongClicked(int itemId, Object data) {

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CAMERA: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    attchmemntPopup(getActivity());

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }

                break;


            }
        }
        return;
        // other 'case' lines to check for other
        // permissions this app might request
    }

    private void attchmemntPopup(final Activity context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert inflater != null;
        View layout = inflater.inflate(R.layout.attachment_popup, null);
        final android.app.AlertDialog alertD = new android.app.AlertDialog.Builder(context).create();
        alertD.setCancelable(true);
        //Making alert as bottom
        Window window = alertD.getWindow();
        assert window != null;
        WindowManager.LayoutParams wlp = window.getAttributes();

        wlp.gravity = Gravity.CENTER;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);

        LinearLayout camera = layout.findViewById(R.id.camera);
        LinearLayout gallery = layout.findViewById(R.id.gallery);

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertD.dismiss();
                boolean result = DialogUtil.checkPermission(getActivity());
               /* if (result) {*/
                isFlagattachment = false;
                Intent intent = new Intent(context, ImageCropActivity.class);
                startActivityForResult(intent, 100);
                // }

            }
        });

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertD.dismiss();
                boolean result = DialogUtil.checkPermission(getActivity());
                if (result) {
                    isFlagattachment = true;
                    Intent intent = new Intent(context, ImageCropActivity.class);
                    startActivityForResult(intent, 100);
                }
            }
        });
        alertD.setView(layout);
        alertD.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PERMISSION_REQUEST_CAMERA && resultCode == RESULT_OK) {

            String path = data.getStringExtra(ImagePickerManager.IntentExtras.IMAGE_PATH);
            Bitmap selectedImage = BitmapFactory.decodeFile(path);
            profile.setImageBitmap(selectedImage);
            prefManager.setProfileImageLink(path);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            selectedImage.compress(Bitmap.CompressFormat.JPEG, 15, byteArrayOutputStream);
            byte[] imagedata = byteArrayOutputStream.toByteArray();
//            Log.d(TAG, "data=======" + Arrays.toString(imagedata));
            String encodedImage = Base64.encodeToString(imagedata, Base64.DEFAULT);
//            Log.d(TAG, "data=======byteArray" + encodedImage);

        }
    }
}


