package com.example.abhinav_rapidbox.childdaycare.fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abhinav_rapidbox.childdaycare.R;
import com.example.abhinav_rapidbox.childdaycare.activity.PaymentActivity;
import com.example.abhinav_rapidbox.childdaycare.cache.AppCache;
import com.example.abhinav_rapidbox.childdaycare.pojo.ChildData;
import com.example.abhinav_rapidbox.childdaycare.pojo.ChildSignUp;
import com.example.abhinav_rapidbox.childdaycare.pojo.HeaderData;
import com.example.abhinav_rapidbox.childdaycare.service.ApiServices;
import com.example.abhinav_rapidbox.childdaycare.service.EventListner;
import com.example.abhinav_rapidbox.childdaycare.service.Result;
import com.example.abhinav_rapidbox.childdaycare.service.TransportManager;
import com.example.abhinav_rapidbox.childdaycare.utill.AppConstant;
import com.example.abhinav_rapidbox.childdaycare.utill.Constants;
import com.example.abhinav_rapidbox.childdaycare.utill.DialogUtil;
import com.example.abhinav_rapidbox.childdaycare.utill.imagepicker.ImageCropActivity;
import com.example.abhinav_rapidbox.childdaycare.utill.imagepicker.ImagePickerManager;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;


public class SignupFragmentChild extends BaseFragment implements AdapterView.OnItemSelectedListener, EventListner {

    private static final int PERMISSION_REQUEST_CAMERA = 100;
    public static boolean isFlagattachment = false;
    static ChildSignUp userRecive;
    TextView button_register, adonechild;
    Spinner sppiner, spinnerGender;
    String valueBloodGroup;
    Integer yearValue;
    Date date = null;
    String[] sppinerData = {"Select Blood Group *", "O+", "O-", "AB+", "AB-", "B-", "B+", "A+", "A-"};
    String[] sppinerDataGender = {"Select Gender *", "Male", "Female", "Others"};
    int d, m, y;
    ProgressDialog progressBar;
    String dateSelected;
    android.app.AlertDialog alertD;
    View view;
    CircleImageView profile;
    String encodedImage, gender;
    private View root_view;
    private EditText editTextChildName, editTextAge;
    private TextView dateOfbirth;
    ArrayList<ChildData> arrayList = new ArrayList<>();

    public static SignupFragmentChild newInstance(ChildSignUp user1) {
        userRecive = user1;
        return new SignupFragmentChild();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root_view = inflater.inflate(R.layout.fragment_signup_child, container, false);
        setId();

        sppiner.setOnItemSelectedListener(this);
        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, sppinerData);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        sppiner.setAdapter(aa);
        ArrayAdapter aa1 = new ArrayAdapter(getActivity(), android.R.layout.simple_expandable_list_item_1, sppinerDataGender);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spinnerGender.setAdapter(aa1);
        spinnerGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gender = sppinerDataGender[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attchmemntPopup(Objects.requireNonNull(getActivity()));
            }
        });
        dateOfbirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerStrt();
            }
        });
        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validDataEntered()) {
                   if (AppCache.getInstance().getChildDataArrayList()!=null && AppCache.getInstance().getChildDataArrayList().size()>0)
                    userRecive.setArrayListChild(AppCache.getInstance().getChildDataArrayList());
                   else {
                       ChildData childData = new ChildData();
                       if (editTextAge.getText().toString().contains("Days")) {
                           String ageValue = editTextAge.getText().toString();
                           String newAge = ageValue.replace(" Days", "");
                           childData.setAge(Integer.parseInt(newAge));
                       } else {
                           String ageValue = editTextAge.getText().toString();
                           String newAge = ageValue.replace(" Years", "");
                           childData.setAge(Integer.parseInt(newAge));
                       }
                       childData.setDate_of_birth(Constants.dateConversion(dateOfbirth.getText().toString()));
                       childData.setImagefile(encodedImage);
                       childData.setChild_name(editTextChildName.getText().toString());
                       childData.setBlood_group(valueBloodGroup);
                       childData.setChild_gender(gender);

                       arrayList.add(childData);
                       userRecive.setArrayListChild(arrayList);
                       Log.d("uhgerjshjg", "fgjfdk" + new Gson().toJson(userRecive));
                       arrayList.clear();
                   }
                   // DialogUtil.displayProgress(getActivity());
                    Log.d("uhgerjshjg", "fgjfdk" + new Gson().toJson(userRecive));
                    //TransportManager.getInstance(SignupFragmentChild.this).saveChildData(getActivity(), userRecive);

                }
            }
        });
        adonechild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validDataEntered()) {
                    ChildData childData = new ChildData();
                    if (editTextAge.getText().toString().contains("Days")) {
                        String ageValue = editTextAge.getText().toString();
                        String newAge = ageValue.replace(" Days", "");
                        childData.setAge(Integer.parseInt(newAge));
                    } else {
                        String ageValue = editTextAge.getText().toString();
                        String newAge = ageValue.replace(" Years", "");
                        childData.setAge(Integer.parseInt(newAge));
                    }
                    childData.setDate_of_birth(Constants.dateConversion(dateOfbirth.getText().toString()));
                    childData.setImagefile(encodedImage);
                    childData.setChild_name(editTextChildName.getText().toString());
                    childData.setBlood_group(valueBloodGroup);
                    childData.setChild_gender(gender);

                    arrayList.add(childData);
                    Log.d("dhfdhjb", "before clear" + new Gson().toJson(arrayList));
                    AppCache.getInstance().setChildDataArrayList(arrayList);

                    Log.d("dhfdhjb", "fhdhdgfvhd" + new Gson().toJson(AppCache.getInstance().getChildDataArrayList()));
                    //arrayList.clear();
                    Log.d("dhfdhjb", "after clear" + new Gson().toJson(arrayList));
                    editTextAge.setText("");
                    editTextChildName.setText("");
                    spinnerGender.setSelection(0);
                    sppiner.setSelection(0);
                    dateOfbirth.setText("");
                    encodedImage = null;

                }
            }
        });

        return root_view;
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
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            selectedImage.compress(Bitmap.CompressFormat.JPEG, 15, byteArrayOutputStream);
            byte[] imagedata = byteArrayOutputStream.toByteArray();
//            Log.d(TAG, "data=======" + Arrays.toString(imagedata));
            encodedImage = Base64.encodeToString(imagedata, Base64.DEFAULT);
            Log.d("kgyd", "data=======byteArray" + encodedImage);

        }
    }

    private void setId() {
        editTextChildName = root_view.findViewById(R.id.editText_childName);
        adonechild = root_view.findViewById(R.id.adonechild);
        editTextAge = root_view.findViewById(R.id.editText_age);
        dateOfbirth = root_view.findViewById(R.id.dateofbirth);
        sppiner = root_view.findViewById(R.id.sppiner);
        spinnerGender = root_view.findViewById(R.id.sppiner_gender);
        button_register = root_view.findViewById(R.id.button_register);
        profile = root_view.findViewById(R.id.imare_user);

    }

    @Override
    public void onResume() {
        super.onResume();
        mListener.onFragmentUpdate(AppConstant.UPDATE_TOOLBAR, new HeaderData("Child Sign Up"));

    }

    private boolean isValidMobile(String phone) {
        return phone.length() == 10 && android.util.Patterns.PHONE.matcher(phone).matches();
    }

    private void showError(EditText editText) {
        // Animation shake = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
        //editText.startAnimation(shake);
    }

    private boolean validDataEntered() {
        if (TextUtils.isEmpty(editTextChildName.getText())) {
            editTextChildName.setError("Enter Child Name");
            editTextChildName.requestFocus();
            showError(editTextChildName);
            //Toast.makeText(getActivity(), "Enter Child Name", Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(editTextAge.getText())) {
            editTextAge.setError("Enter Age");
            editTextAge.requestFocus();
            showError(editTextAge);
            //Toast.makeText(getActivity(), "Enter Age", Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(dateOfbirth.getText())) {
            Toast.makeText(getActivity(), "Enter Date of birth", Toast.LENGTH_SHORT).show();
            return false;
        } else if (sppiner.getSelectedItem().equals("Select Blood Group *")) {
            Toast.makeText(getActivity(), "Please select blood group", Toast.LENGTH_SHORT).show();
            return false;
        } else if (spinnerGender.getSelectedItem().equals("Select Gender *")) {
            Toast.makeText(getActivity(), "Please select gender", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void showProgressBar() {
        if (progressBar == null)
            progressBar = new ProgressDialog(getActivity());

        progressBar.setMessage("Please wait ...");
        progressBar.setCancelable(false);
        progressBar.show();
    }

    private void hideProgressBar() {
        progressBar.hide();
    }

    public void datePickerStrt() {

        Calendar c = Calendar.getInstance(Locale.ENGLISH);
        Integer ALyear = c.get(Calendar.YEAR);
        Integer ALmonthOfYear = c.get(Calendar.MONTH);
        Integer ALdayOfMonth = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dpd = new DatePickerDialog(mActivity, R.style.datepicker, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                dateSelected = (getProperFormat(dayOfMonth) + "-" + getProperFormat(monthOfYear + 1) + "-" + year);
                dateOfbirth.setText(dateSelected);
                ageCaluate();
            }
        }, ALyear, ALmonthOfYear, ALdayOfMonth);


        dpd.getDatePicker().setMaxDate(c.getTimeInMillis());
        dpd.show();

    }

    private String getProperFormat(int hhORmm) {
        String temp = hhORmm + "";
        if (temp.length() == 1) {
            temp = "0" + temp;
        }
        return temp;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        valueBloodGroup = sppinerData[position];
        //Toast.makeText(getApplicationContext(),country[position] ,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void ageCaluate() {
        if (!dateOfbirth.getText().toString().trim().isEmpty()) {
            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

            try {
                date = (Date) dateFormat.parse(dateOfbirth.getText().toString());
            } catch (ParseException e) {

                e.printStackTrace();
            }


            String stringDate = String.valueOf(date.getTime());

            DateFormat sdf = new SimpleDateFormat("yyyy");
            SimpleDateFormat sdfDOY = new SimpleDateFormat("dd");
            SimpleDateFormat sdfDOM = new SimpleDateFormat("MM");

            Date netDate = (new Date(Long.parseLong(stringDate)));
            Date DOY = (new Date(Long.parseLong(stringDate)));
            Date DOM = (new Date(Long.parseLong(stringDate)));

            String date_DOY = sdfDOY.format(DOY);
            String date_result = sdf.format(netDate);
            String date_DOM = sdfDOM.format(DOM);

            Calendar today = Calendar.getInstance();

            d = Integer.parseInt(date_DOY);
            m = Integer.parseInt(date_DOM);
            y = Integer.parseInt(date_result);
            //getAge(y,m,d);
            yearValue = today.get(Calendar.YEAR) - y;
        } else {
            yearValue = 0;
        }


        if (yearValue >= 1) {
            editTextAge.setText(String.valueOf(yearValue) + " Years");
        } else {
            long diff = System.currentTimeMillis() - date.getTime();
            long seconds = diff / 1000;
            long minutes = seconds / 60;
            long hours = minutes / 60;
            long days = (hours / 24) + 1;
            editTextAge.setText(days + " Days");
        }
    }

    @Override
    public void onSuccessResponse(int reqType, Result data) {
        switch (reqType) {
            case ApiServices.REQUEST_CHILD_SIGINUP:
                CallLogoutPopup("Child registered successfully.");
                break;

        }
        DialogUtil.stopProgressDisplay();
    }

    private void CallLogoutPopup(String msg) {
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.dashboard_logout_popup, null);
        alertD = new android.app.AlertDialog.Builder(getActivity()).create();

        /*set alert at bottom*/
        Window window = alertD.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();

        wlp.gravity = Gravity.CENTER;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);
        TextView alertActionText = view.findViewById(R.id.message);
        alertActionText.setText(msg);
        TextView cancelAlert = view.findViewById(R.id.action_text);
        cancelAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PaymentActivity.class);
                intent.putExtra("email", userRecive.getEmail_id());
                intent.putExtra("phone", userRecive.getContact_no());
                startActivity(intent);
            }
        });
        alertD.setView(view);
        alertD.show();
    }

    @Override
    public void onFailureResponse(int reqType, Result data) {
        DialogUtil.stopProgressDisplay();
        Toast.makeText(getActivity(), data.getMessage(), Toast.LENGTH_SHORT).show();
    }
}
