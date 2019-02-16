package com.example.abhinav_rapidbox.childdaycare.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.abhinav_rapidbox.childdaycare.R;
import com.example.abhinav_rapidbox.childdaycare.cache.PrefManager;
import com.example.abhinav_rapidbox.childdaycare.pojo.ChildSignUp;
import com.example.abhinav_rapidbox.childdaycare.pojo.HeaderData;
import com.example.abhinav_rapidbox.childdaycare.pojo.User;
import com.example.abhinav_rapidbox.childdaycare.utill.AppConstant;


public class SignupFragment extends BaseFragment  {

    User user;
    TextView nextScreen;
    private View root_view;
    PrefManager prefManager;
    private EditText editTextFatherName, editTextMotherName, editTextEmail, editTextContactNo,
            editTextAddress;

    public static boolean isValidEmail(CharSequence email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root_view = inflater.inflate(R.layout.fragment_signup, container, false);
        setId();
        prefManager = PrefManager.getInstance();
        user = new User();
        //editTextFatherName.setText(prefManager);
            nextScreen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (validDataEntered()) {
                        ChildSignUp user = new ChildSignUp();
                        user.setFathers_name(editTextFatherName.getText().toString());
                        user.setContact_no(editTextContactNo.getText().toString());
                        user.setAddress(editTextAddress.getText().toString());
                        user.setMothers_name(editTextMotherName.getText().toString());
                        user.setEmail_id(editTextEmail.getText().toString());
                        user.setUserid(editTextEmail.getText().toString());
                        mListener.onFragmentInteraction(AppConstant.SIGNUP_FRAGMENT_CHILD, user);
                    }
                }
            });
        return root_view;
    }

    private void setId() {
        editTextAddress = root_view.findViewById(R.id.editText_address);
        editTextFatherName = root_view.findViewById(R.id.editText_fatherName);
        editTextMotherName = root_view.findViewById(R.id.editText_mothername);
        editTextEmail = root_view.findViewById(R.id.editText_email);
        editTextContactNo = root_view.findViewById(R.id.editText_contactNo);
        nextScreen = root_view.findViewById(R.id.next);
    }

    @Override
    public void onResume() {
        super.onResume();
        mListener.onFragmentUpdate(AppConstant.UPDATE_TOOLBAR, new HeaderData("Parent Sign Up"));
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
        } else if (!isValidMobile(editTextContactNo.getText().toString())) {
            editTextContactNo.setError("Enter valid phone number");
            editTextContactNo.requestFocus();
            showError(editTextContactNo);
            // Toast.makeText(getActivity(), "Enter Phone number", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!isValidEmail(editTextEmail.getText())) {
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
        } /*else if (TextUtils.isEmpty(editTextPassword.getText())) {
            editTextPassword.setError("Enter Password");
            editTextPassword.requestFocus();
            showError(editTextPassword);
            // Toast.makeText(getActivity(), "Enter Password", Toast.LENGTH_SHORT).show();
            return false;
        }*/
        return true;
    }


}
