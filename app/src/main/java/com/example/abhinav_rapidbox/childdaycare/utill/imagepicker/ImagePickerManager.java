package com.example.abhinav_rapidbox.childdaycare.utill.imagepicker;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;


/**
 * @author GT
 */
public class ImagePickerManager implements PicModeSelectDialogFragment.IPicModeSelectListener {

    public static final String TAG = "ImageViewActivity";
    public static final String TEMP_PHOTO_FILE_NAME = "temp_photo.jpg";
    public static final int REQUEST_CODE_UPDATE_PIC = 0x1;
    public static final int REQUEST_CODE_TAKE_PIC = 100;

    private String imgUri;
    ImageView mImageView;
    ImageSelectionListener listener;

    Activity activity;
    Fragment fragment;

    public void showPicker(Activity activity, ImageView imageView, ImageSelectionListener listener) {

        // Log.d(TAG, "showPicker: ");
        this.activity = activity;
        this.mImageView = imageView;
        this.listener = listener;
        checkPermissions();

    }

    public void showPicker(Activity activity, Fragment fragment, ImageView imageView, ImageSelectionListener listener) {

        // Log.d(TAG, "showPicker: ");
        this.fragment = fragment;
        showPicker(activity, imageView, listener);

    }

    @SuppressLint("InlinedApi")
    private void checkPermissions() {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (fragment == null) {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_TAKE_PIC);
            } else {
                fragment.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_TAKE_PIC);
            }
        }
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if (fragment == null) {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA}, REQUEST_CODE_TAKE_PIC);
            } else {
                fragment.requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_CODE_TAKE_PIC);
            }
        }

        showAddProfilePicDialog();


    }


    public void onActivityResult(int requestCode, int resultCode, Intent result) {
        if (requestCode == REQUEST_CODE_UPDATE_PIC) {
            if (resultCode == Activity.RESULT_OK) {
                String imagePath = result.getStringExtra(IntentExtras.IMAGE_PATH);
                // Log.d(TAG, "onActivityResult: imagePath "+imagePath);
                showCroppedImage(imagePath);
                listener.imageSelected(imagePath);
            } else if (resultCode == Activity.RESULT_CANCELED) {
                //TODO : Handle case
            } else {
                String errorMsg = result.getStringExtra(ImageCropActivity.ERROR_MSG);
                Toast.makeText(activity, errorMsg, Toast.LENGTH_LONG).show();
            }
        } else if (requestCode == 1234) {
            showAddProfilePicDialog();
        }
    }

    private void showCroppedImage(String mImagePath) {
        if (mImagePath != null) {
            Bitmap myBitmap = BitmapFactory.decodeFile(mImagePath);
            mImageView.setImageBitmap(myBitmap);
        }
    }

    private void showAddProfilePicDialog() {
        PicModeSelectDialogFragment dialogFragment = new PicModeSelectDialogFragment();
        dialogFragment.setiPicModeSelectListener(this);
        dialogFragment.show(activity.getFragmentManager(), "picModeSelector");
    }

    private void actionProfilePic(String action) {
        Intent intent = new Intent(activity, ImageCropActivity.class);
        intent.putExtra("ACTION", action);
        if (fragment == null) {
            activity.startActivityForResult(intent, REQUEST_CODE_UPDATE_PIC);
        } else {
            fragment.startActivityForResult(intent, REQUEST_CODE_UPDATE_PIC);
        }
    }


    @Override
    public void onPicModeSelected(String mode) {
        String action = mode.equalsIgnoreCase(PicModes.CAMERA) ? IntentExtras.ACTION_CAMERA : IntentExtras.ACTION_GALLERY;
        actionProfilePic(action);
    }

    public static Uri getImageUri(String path) {
        return Uri.fromFile(new File(path));
    }

    public interface IntentExtras {
        String ACTION_CAMERA = "action-camera";
        String ACTION_GALLERY = "action-gallery";
        String IMAGE_PATH = "image-path";
    }

    public interface PicModes {
        String CAMERA = "Camera";
        String GALLERY = "Gallery";
    }
}
