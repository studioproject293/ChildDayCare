package com.example.abhinav_rapidbox.childdaycare.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abhinav_rapidbox.childdaycare.R;
import com.example.abhinav_rapidbox.childdaycare.activity.MainActivity;
import com.example.abhinav_rapidbox.childdaycare.pojo.ChildSignUp;
import com.example.abhinav_rapidbox.childdaycare.pojo.User;
import com.example.abhinav_rapidbox.childdaycare.service.ApiServices;
import com.example.abhinav_rapidbox.childdaycare.service.EventListner;
import com.example.abhinav_rapidbox.childdaycare.service.Result;
import com.example.abhinav_rapidbox.childdaycare.service.TransportManager;
import com.example.abhinav_rapidbox.childdaycare.utill.Constants;
import com.example.abhinav_rapidbox.childdaycare.utill.DialogUtil;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import java.util.Date;
import java.util.Locale;


public class SignupFragmentChild extends BaseFragment implements AdapterView.OnItemSelectedListener, EventListner {

    private View root_view;
    private FirebaseStorage mFirebaseStorage;
    private StorageReference mStorageReference;
    private EditText editTextChildName, editTextAge;
    private TextView dateOfbirth;
    Button button_register;
    static ChildSignUp userRecive;

    Spinner sppiner;
    String valueBloodGroup;
    Integer yearValue;
    Date date = null;
    String[] sppinerData = {"Select Blood Group *", "O+", "O-", "AB+", "AB-", "B-", "B+", "A+", "A-"};
    int d, m, y;

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
        // Create an instance of Firebase Storage
        mFirebaseStorage = FirebaseStorage.getInstance();


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


                    userRecive.setChild_name(editTextChildName.getText().toString());
                    userRecive.setBlood_group(valueBloodGroup);
                    if (editTextAge.getText().toString().contains("Days")) {
                        String ageValue = editTextAge.getText().toString();
                        String newAge = ageValue.replace(" Days", "");
                        userRecive.setAge(Integer.parseInt(newAge));
                    } else {
                        String ageValue = editTextAge.getText().toString();
                        String newAge = ageValue.replace(" Years", "");
                        userRecive.setAge(Integer.parseInt(newAge));
                    }
                    //userRecive.setAge(Integer.parseInt(editTextAge.getText().toString()));
                    userRecive.setDate_of_birth(Constants.dateConversion(dateOfbirth.getText().toString()));
                    DialogUtil.displayProgress(getActivity());
                    TransportManager.getInstance(SignupFragmentChild.this).saveChildData(getActivity(), userRecive);

                }
            }
        });
        return root_view;
    }


    private void setId() {
        editTextChildName = root_view.findViewById(R.id.editText_childName);
        editTextAge = root_view.findViewById(R.id.editText_age);
        dateOfbirth = root_view.findViewById(R.id.dateofbirth);
        sppiner = root_view.findViewById(R.id.sppiner);
        button_register = root_view.findViewById(R.id.button_register);
    }


    @Override
    public void onResume() {
        super.onResume();
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
        }
        return true;
    }

   /* private void saveUser() {

        AsyncTask<Void, Void, Void> saveTask = new AsyncTask<Void, Void, Void>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                showProgressBar();
            }

            @Override
            protected Void doInBackground(Void... voids) {
                final FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference userRef = database.getReference(AppConstant.TABLE_USER);
                userRef.push().setValue(user);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                hideProgressBar();
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("User details saved.");
                builder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //clearFields();

                    }
                });
                builder.show();
            }
        }.execute();

//
//
        //finish();

    }*/

    ProgressDialog progressBar;

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

    String dateSelected;

    public Dialog datePickerStrt() {

        Calendar c = Calendar.getInstance(Locale.ENGLISH);
        Integer ALyear = c.get(Calendar.YEAR);
        Integer ALmonthOfYear = c.get(Calendar.MONTH);
        Integer ALdayOfMonth = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dpd = new DatePickerDialog(mActivity, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                dateSelected = (getProperFormat(dayOfMonth) + "-" + getProperFormat(monthOfYear + 1) + "-" + year);
                dateOfbirth.setText(dateSelected);
                ageCaluate();
            }
        }, ALyear, ALmonthOfYear, ALdayOfMonth);


        dpd.getDatePicker().setMaxDate(c.getTimeInMillis());
        dpd.show();
        return dpd;
    }

    private String getProperFormat(int hhORmm) {
        String temp = hhORmm + "";
        if (temp.length() == 1) {
            temp = "0" + temp;
        } else {

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

    public static SignupFragmentChild newInstance(ChildSignUp user1) {
        userRecive = user1;
        return new SignupFragmentChild();
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
                Toast.makeText(getActivity(), "Child registered successfully.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onFailureResponse(int reqType, Result data) {
        DialogUtil.stopProgressDisplay();
        Toast.makeText(getActivity(), data.getMessage(), Toast.LENGTH_SHORT).show();
    }
}
