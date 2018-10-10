package com.example.abhinav_rapidbox.childdaycare.utill.imagepicker;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Toast;

import com.example.abhinav_rapidbox.childdaycare.R;
import com.example.abhinav_rapidbox.childdaycare.fragment.SignupFragmentChild;
import com.example.abhinav_rapidbox.childdaycare.fragment.UserProfileFragment;

import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import io.togoto.imagezoomcrop.cropoverlay.CropOverlayView;
import io.togoto.imagezoomcrop.cropoverlay.edge.Edge;
import io.togoto.imagezoomcrop.photoview.IGetImageBounds;
import io.togoto.imagezoomcrop.photoview.PhotoView;
import io.togoto.imagezoomcrop.photoview.RotationSeekBar;


/**
 * @author GT
 */
public class ImageCropActivity extends AppCompatActivity {

    public static final String TAG = "ImageCropActivity";
    public static final String TEMP_PHOTO_FILE_NAME = "temp_photo.jpg";
    public static final int REQUEST_CODE_PICK_GALLERY = 0x1;
    public static final int REQUEST_CODE_TAKE_PICTURE = 0x2;
    public static final int REQUEST_CODE_SAVE_PICTURE = 0x4;
    public static final int REQUEST_CODE_CROPPED_PICTURE = 0x3;
    public static final String ERROR_MSG = "error_msg";
    public static final String ERROR = "error";
    private static final int ANCHOR_CENTER_DELTA = 10;
    private static final int PERMISSION_REQUEST = 100;
    private final int IMAGE_MAX_SIZE = 1024;
    private final Bitmap.CompressFormat mOutputFormat = Bitmap.CompressFormat.JPEG;
    PhotoView mImageView;
    //Context context;
    CropOverlayView mCropOverlayView;
    Button btnRetakePic;
    Button btnFromGallery;
    Button btnDone;
    Button mBtnReset;
    View mMoveResizeText;
    RotationSeekBar mRotationBar;
    Button mBtnUndoRotation;
    private Uri imageUri;
    private ContentResolver mContentResolver;
    //Temp file to save cropped image
    private String mImagePath;
    private Uri mSaveUri = null;
    private Uri mImageUri = null;
    //File for capturing camera images
    private File mFileTemp;
    private View.OnClickListener btnDoneListerner = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            saveUploadCroppedImage();
        }
    };
    private View.OnClickListener btnResetListerner = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mRotationBar.reset();
            // init();
            mImageView.reset();
        }
    };
    private View.OnClickListener btnRetakeListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            takePic();
            //cameraIntent();


        }
    };
    private View.OnClickListener btnUndoRotationListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mImageView.setRotationBy(0, true);
            mRotationBar.reset();
        }
    };
    private View.OnClickListener btnFromGalleryListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (null == mFileTemp) {
                createTempFile();
            }
            pickImage();
        }
    };

    private static void copyStream(InputStream input, OutputStream output) throws IOException {
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = input.read(buffer)) != -1) {
            output.write(buffer, 0, bytesRead);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // context = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_crop);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("");

        mContentResolver = getContentResolver();
        mImageView = findViewById(R.id.iv_photo);
        mCropOverlayView = findViewById(R.id.crop_overlay);
        btnRetakePic = findViewById(R.id.btnRetakePic);
        btnFromGallery = findViewById(R.id.btnFromGallery);
        btnDone = findViewById(R.id.btn_done);
        mBtnReset = findViewById(R.id.btn_reset);
        mMoveResizeText = findViewById(R.id.tv_move_resize_txt);
        mRotationBar = findViewById(R.id.bar_rotation);
        mBtnUndoRotation = findViewById(R.id.btn_undo);

        btnRetakePic.setOnClickListener(btnRetakeListener);
        btnFromGallery.setOnClickListener(btnFromGalleryListener);
        btnDone.setOnClickListener(btnDoneListerner);
        mBtnReset.setOnClickListener(btnResetListerner);
        mBtnUndoRotation.setOnClickListener(btnUndoRotationListener);

        mImageView.setImageBoundsListener(new IGetImageBounds() {
            @Override
            public Rect getImageBounds() {
                return mCropOverlayView.getImageBounds();
            }
        });

        // initialize rotation seek bar
        mRotationBar.setOnSeekBarChangeListener(new RotationSeekBar.OnRotationSeekBarChangeListener(mRotationBar) {

            private float mLastAngle;

            @Override
            public void onRotationProgressChanged(@NonNull RotationSeekBar seekBar, float angle, float delta, boolean fromUser) {
                mLastAngle = angle;
                if (fromUser) {
                    mImageView.setRotationBy(delta, false);
                }
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                super.onStopTrackingTouch(seekBar);
                if (Math.abs(mLastAngle) < ANCHOR_CENTER_DELTA) {
                    mRotationBar.reset();
                    mImageView.setRotationBy(0, true);
                }
            }
        });


        createTempFile();
        if (savedInstanceState == null || !savedInstanceState.getBoolean("restoreState")) {
            String action = getIntent().getStringExtra("ACTION");
            if (null != action) {
                switch (action) {
                    case ImagePickerManager.IntentExtras.ACTION_CAMERA:
                        getIntent().removeExtra("ACTION");
                        takePic();
                        btnRetakePic.setVisibility(View.VISIBLE);
                        btnFromGallery.setVisibility(View.GONE);
                        //cameraIntent();
                        return;
                    case ImagePickerManager.IntentExtras.ACTION_GALLERY:
                        getIntent().removeExtra("ACTION");
                        pickImage();
                        btnRetakePic.setVisibility(View.GONE);
                        btnFromGallery.setVisibility(View.VISIBLE);
                        return;
                }
            }
        }
        mImagePath = mFileTemp.getPath();
        mSaveUri = ImagePickerManager.getImageUri(mImagePath);
        mImageUri = ImagePickerManager.getImageUri(mImagePath);
        init();

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
        if (UserProfileFragment.isFlagattachment || SignupFragmentChild.isFlagattachment) {
            getIntent().removeExtra("ACTION");
            pickImage();
            btnRetakePic.setVisibility(View.GONE);
            btnFromGallery.setVisibility(View.VISIBLE);
        } else {
            takePic();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void init() {
        Bitmap bitmap = getBitmap(mImageUri);
        Drawable drawable = new BitmapDrawable(getResources(), bitmap);

        float minScale = mImageView.setMinimumScaleToFit(drawable);
        mImageView.setMaximumScale(minScale * 3);
        mImageView.setMediumScale(minScale * 2);
        mImageView.setScale(minScale);
        mImageView.setImageDrawable(drawable);

        //Initialize the MoveResize text
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) mMoveResizeText.getLayoutParams();
        lp.setMargins(0, Math.round(Edge.BOTTOM.getCoordinate()) + 20, 0, 0);
        mMoveResizeText.setLayoutParams(lp);
    }

    private void saveUploadCroppedImage() {
        boolean saved = saveOutput();
        if (saved) {
            //USUALLY Upload image to server here
            Intent intent = new Intent();
            intent.putExtra(ImagePickerManager.IntentExtras.IMAGE_PATH, mImagePath);
            setResult(RESULT_OK, intent);
            finish();
        } else {
            Toast.makeText(this, "Unable to save Image into your device.", Toast.LENGTH_LONG).show();
        }
    }

    private void createTempFile() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            mFileTemp = new File(Environment.getExternalStorageDirectory(), TEMP_PHOTO_FILE_NAME);
        } else {
            mFileTemp = new File(getFilesDir(), TEMP_PHOTO_FILE_NAME);
        }
    }

    public boolean checkForPermission(String permission, int mode) {
        //this.permissionMode = mode;
        int permissionCheck = ContextCompat.checkSelfPermission(this, permission);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed, we can request the permission.
                requestPermissions(new String[]{permission}, PERMISSION_REQUEST);

            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    takePic();
                    // cameraIntent();

                } else {

                    // functionality depends on this permission.
                    /*if (permissionCounter < MAX_COUNTER) {
                        permissionCounter++;
                        requestPermissions(permissions, PERMISSION_REQUEST);
                    }
                    else {
                        Toast.makeText(getActivity(), "Required Permissions to be Granted to access this feature !!!", Toast.LENGTH_LONG).show();
                    }*/
                }
                return;
            }
        }
    }

   /* private void cameraIntent() {
        //Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //startActivityForResult(intent, REQUEST_CAMERA);

        int permissionCheck = ContextCompat.checkSelfPermission(context,  Manifest.permission.CAMERA);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{ Manifest.permission.CAMERA},
                    REQUEST_CODE_TAKE_PICTURE);
        } else {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            File photo = new File(Environment.getExternalStorageDirectory(), "picture.jpg");
            imageUri = Uri.fromFile(photo);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivityForResult(intent, REQUEST_CODE_TAKE_PICTURE);
        }
    }*/

    private void takePic() {
        if (!checkForPermission(Manifest.permission.CAMERA, REQUEST_CODE_TAKE_PICTURE)) {
            Log.d(TAG, "getMobileNumberRequest: sms not approved");
            return;
        }

        if (!checkForPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, REQUEST_CODE_SAVE_PICTURE)) {
            //Log.d(TAG, "getMobileNumberRequest: sms not approved");
            return;
        }


        if (null == mFileTemp) {
            createTempFile();
        }

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            Uri mImageCaptureUri;
            String state = Environment.getExternalStorageState();
            if (Environment.MEDIA_MOUNTED.equals(state)) {
                mImageCaptureUri = Uri.fromFile(mFileTemp);
            } else {
                /*
                 * The solution is taken from here: http://stackoverflow.com/questions/10042695/how-to-get-camera-result-as-a-uri-in-data-folder
	        	 */
                mImageCaptureUri = InternalStorageContentProvider.CONTENT_URI;
            }
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
            takePictureIntent.putExtra("return-data", true);
            takePictureIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivityForResult(takePictureIntent, REQUEST_CODE_TAKE_PICTURE);
        } catch (ActivityNotFoundException e) {
            Log.e(TAG, "Can't take picture", e);
            Toast.makeText(this, "Can't take picture", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "Can't take picture", e);
            Toast.makeText(this, "Can't take picture", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putBoolean("restoreState", true);
    }

    private void pickImage() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT).setType("image/*");
        try {
            startActivityForResult(intent, REQUEST_CODE_PICK_GALLERY);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "No image source available", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent result) {
        super.onActivityResult(requestCode, resultCode, result);
        createTempFile();
        if (requestCode == REQUEST_CODE_TAKE_PICTURE) {
            if (resultCode == RESULT_OK) {
                mImagePath = mFileTemp.getPath();
                mSaveUri = ImagePickerManager.getImageUri(mImagePath);
                mImageUri = ImagePickerManager.getImageUri(mImagePath);
                init();
            } else if (resultCode == RESULT_CANCELED) {
                userCancelled();
                return;
            } else {
                errored("Error while opening the image file. Please try again.");
                return;
            }

        } else if (requestCode == REQUEST_CODE_PICK_GALLERY) {
            if (resultCode == RESULT_CANCELED) {
                userCancelled();
                return;
            } else if (resultCode == RESULT_OK) {
                try {
                    InputStream inputStream = getContentResolver().openInputStream(result.getData()); // Got the bitmap .. Copy it to the temp file for cropping
                    FileOutputStream fileOutputStream = new FileOutputStream(mFileTemp);
                    copyStream(inputStream, fileOutputStream);
                    fileOutputStream.close();
                    assert inputStream != null;
                    inputStream.close();
                    mImagePath = mFileTemp.getPath();
                    mSaveUri = ImagePickerManager.getImageUri(mImagePath);
                    mImageUri = ImagePickerManager.getImageUri(mImagePath);
                    init();
                } catch (Exception e) {
                    errored("Error while opening the image file. Please try again.");
                    //L.e(e);
                    return;
                }
            } else {
                errored("Error while opening the image file. Please try again.");
                return;
            }

        }
    }


    private Bitmap getBitmap(Uri uri) {
        InputStream in;
        Bitmap returnedBitmap;
        try {
            in = mContentResolver.openInputStream(uri);
            //Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(in, null, o);
            assert in != null;
            in.close();
            int scale = 1;
            if (o.outHeight > IMAGE_MAX_SIZE || o.outWidth > IMAGE_MAX_SIZE) {
                scale = (int) Math.pow(2, (int) Math.round(Math.log(IMAGE_MAX_SIZE / (double) Math.max(o.outHeight, o.outWidth)) / Math.log(0.5)));
            }

            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            in = mContentResolver.openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(in, null, o2);
            assert in != null;
            in.close();

            //First check
            ExifInterface ei = new ExifInterface(uri.getPath());
            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    returnedBitmap = rotateImage(bitmap, 90);
                    //Free up the memory
                    bitmap.recycle();
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    returnedBitmap = rotateImage(bitmap, 180);
                    //Free up the memory
                    bitmap.recycle();
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    returnedBitmap = rotateImage(bitmap, 270);
                    //Free up the memory
                    bitmap.recycle();
                    break;
                default:
                    returnedBitmap = bitmap;
            }
            return returnedBitmap;
        } catch (FileNotFoundException e) {
//            L.e(e);
        } catch (IOException e) {
//            L.e(e);
        }
        return null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private boolean saveOutput() {
        Bitmap croppedImage = mImageView.getCroppedImage();
        if (mSaveUri != null) {
            OutputStream outputStream = null;
            try {
                outputStream = mContentResolver.openOutputStream(mSaveUri);
                if (outputStream != null) {
                    croppedImage.compress(mOutputFormat, 90, outputStream);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
                return false;
            } finally {
                closeSilently(outputStream);
            }
        } else {
            Log.e(TAG, "not defined image url");
            return false;
        }
        croppedImage.recycle();
        return true;
    }


    public void closeSilently(Closeable c) {
        if (c == null) return;
        try {
            c.close();
        } catch (Throwable t) {
            // do nothing
        }
    }


    private Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }


    public void userCancelled() {
        Intent intent = new Intent();
        setResult(RESULT_CANCELED, intent);
        finish();
    }

    public void errored(String msg) {
        Intent intent = new Intent();
        intent.putExtra(ERROR, true);
        if (msg != null) {
            intent.putExtra(ERROR_MSG, msg);
        }
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UserProfileFragment.isFlagattachment = false;
        SignupFragmentChild.isFlagattachment = false;

    }
}
