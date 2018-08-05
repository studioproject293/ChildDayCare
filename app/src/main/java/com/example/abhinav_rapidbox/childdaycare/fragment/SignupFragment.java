package com.example.abhinav_rapidbox.childdaycare.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
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
import com.example.abhinav_rapidbox.childdaycare.pojo.User;
import com.example.abhinav_rapidbox.childdaycare.utill.AppConstant;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Calendar;
import java.util.Locale;


public class SignupFragment extends BaseFragment implements AdapterView.OnItemSelectedListener {

    private View root_view;
    private FirebaseStorage mFirebaseStorage;
    private StorageReference mStorageReference;
    private EditText editTextFatherName, editTextMotherName, editTextEmail, editTextContactNo,
            editTextAddress, editTextPassword, editTextChildName, editTextAge;
    private TextView dateOfbirth;
    Button button_register;
    User user;
    Spinner sppiner;
    Button nextScreen;
    String valueBloodGroup;
    String[] sppinerData = {"Select Blood Group *", "O+", "O-", "AB+", "AB-", "B-", "B+", "A+", "A-"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root_view = inflater.inflate(R.layout.fragment_signup, container, false);
        setId();
        user = new User();
        /*sppiner.setOnItemSelectedListener(this);
        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, sppinerData);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        sppiner.setAdapter(aa);*/
        // Create an instance of Firebase Storage
        mFirebaseStorage = FirebaseStorage.getInstance();
       /* dateOfbirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerStrt();
            }
        });*/
        /*button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validDataEntered()) {
                    user.setFatherName(editTextFatherName.getText().toString());
                    user.setPhoneNo(editTextContactNo.getText().toString());
                    user.setAddress(editTextAddress.getText().toString());
                    user.setPassword(editTextPassword.getText().toString());
                    user.setMotherName(editTextMotherName.getText().toString());
                    user.setChildName(editTextChildName.getText().toString());
                    user.setBlirdGroup(valueBloodGroup);
                    user.setAge(editTextAge.getText().toString());
                    user.setEmailId(editTextEmail.getText().toString());


                }
            }
        });*/
        nextScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validDataEntered()) {
                    User user = new User();
                    user.setFatherName(editTextFatherName.getText().toString());
                    user.setPhoneNo(editTextContactNo.getText().toString());
                    user.setAddress(editTextAddress.getText().toString());
                    user.setPassword(editTextPassword.getText().toString());
                    user.setMotherName(editTextMotherName.getText().toString());
                    user.setEmailId(editTextEmail.getText().toString());
                    mListener.onFragmentInteraction(AppConstant.SIGNUP_FRAGMENT_CHILD, user);
                }
            }
        });
        return root_view;
    }

    private void setId() {
        editTextAddress = root_view.findViewById(R.id.editText_address);
        //editTextChildName = root_view.findViewById(R.id.editText_childName);
        editTextFatherName = root_view.findViewById(R.id.editText_fatherName);
        editTextMotherName = root_view.findViewById(R.id.editText_mothername);
        editTextPassword = root_view.findViewById(R.id.editText_password);
        //editTextAge = root_view.findViewById(R.id.editText_age);
        //dateOfbirth = root_view.findViewById(R.id.dateofbirth);
        editTextEmail = root_view.findViewById(R.id.editText_email);
        editTextContactNo = root_view.findViewById(R.id.editText_contactNo);
        /*sppiner = root_view.findViewById(R.id.sppiner);
        button_register = root_view.findViewById(R.id.button_register);*/
        nextScreen = root_view.findViewById(R.id.next);
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

        if (TextUtils.isEmpty(editTextFatherName.getText())) {
            editTextFatherName.setError("Enter Father Name");
            editTextFatherName.requestFocus();
            showError(editTextFatherName);
            // Toast.makeText(getActivity(), "Enter Father Name", Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(editTextMotherName.getText())) {
            editTextMotherName.setError("Enter Mother Name");
            editTextMotherName.requestFocus();
            showError(editTextMotherName);
            // Toast.makeText(getActivity(), "Enter Mother Name", Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(editTextContactNo.getText())) {
            editTextContactNo.setError("Enter Phone number");
            editTextContactNo.requestFocus();
            showError(editTextContactNo);
            // Toast.makeText(getActivity(), "Enter Phone number", Toast.LENGTH_SHORT).show();
            return false;
        } else if (editTextContactNo.getText().length() < 10) {
            editTextContactNo.setError("Enter Valid mobile number");
            editTextContactNo.requestFocus();
            showError(editTextContactNo);
            //Toast.makeText(getActivity(), "Enter Valid mobile number", Toast.LENGTH_SHORT).show();
            // enterPhone.getInputField().requestFocus();
            return false;
        } else if (TextUtils.isEmpty(editTextEmail.getText())) {
            editTextEmail.setError("Enter Valid EmailId");
            editTextEmail.requestFocus();
            showError(editTextEmail);
            //Toast.makeText(getActivity(), "Enter EmailId", Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(editTextAddress.getText())) {
            editTextAddress.setError("Enter Address");
            editTextAddress.requestFocus();
            showError(editTextAddress);
            //Toast.makeText(getActivity(),
            //Toast.makeText(getActivity(), "Enter Address", Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(editTextPassword.getText())) {
            editTextPassword.setError("Enter Password");
            editTextPassword.requestFocus();
            showError(editTextPassword);
            // Toast.makeText(getActivity(), "Enter Password", Toast.LENGTH_SHORT).show();
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
}
