package com.example.abhinav_rapidbox.childdaycare.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.abhinav_rapidbox.childdaycare.R;
import com.example.abhinav_rapidbox.childdaycare.pojo.HeaderData;
import com.example.abhinav_rapidbox.childdaycare.utill.AppConstant;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddReviewFragment extends BaseFragment {
    RatingBar ratingBar;
    EditText editTextReview, reviewHeading;
    Button save;
    private View view;

    public AddReviewFragment() {
        // Required empty public constructor
    }

    public static AddReviewFragment newInstance() {

        return new AddReviewFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        mListener.onFragmentUpdate(AppConstant.UPDATE_TOOLBAR, new HeaderData("Add Review"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_review_layout, container, false);
        ratingBar = view.findViewById(R.id.ratingBar);
        reviewHeading = view.findViewById(R.id.reviewHeading);
        editTextReview = view.findViewById(R.id.editTextCancel);
        save = view.findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ratingBar.getRating() == 0.0) {
                    Toast.makeText(mActivity, "Please give rating", Toast.LENGTH_SHORT).show();
                } else {


                }
            }
        });
        return view;
    }

}
