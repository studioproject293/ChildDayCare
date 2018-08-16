package com.example.abhinav_rapidbox.childdaycare.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.abhinav_rapidbox.childdaycare.R;
import com.example.abhinav_rapidbox.childdaycare.adapter.FullIImageAdapter;
import com.example.abhinav_rapidbox.childdaycare.pojo.ZoomImage;


public class ImageFullScreenFragment extends BaseFragment {

    static ZoomImage zoomImages;
    ViewPager viewPager;
    FullIImageAdapter fullIImageAdapter;
    ImageView img_cancel, img_previous_btn, img_next_btn;
    TextView txt_slide_no;
    Activity activity;
    private View root_view;

    public static Fragment newInstance(ZoomImage zoomImage) {
        zoomImages = zoomImage;
        return new ImageFullScreenFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        // mListener.onFragmentUpdate(AppConstants.UPDATE_TOOLBAR, new HeaderData(true, "", false, false, true));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root_view = inflater.inflate(R.layout.fragment_image_full_screen, container, false);
        activity = getActivity();
        initview();
        return root_view;

    }

    private void initview() {
        viewPager = root_view.findViewById(R.id.viewpager_image_fullscreen);
        if (zoomImages.getImageList() != null && zoomImages.getImageList().size() > 0) {
            fullIImageAdapter = new FullIImageAdapter(activity, zoomImages.getImageList());
            viewPager.setAdapter(fullIImageAdapter);
        }
        img_previous_btn = root_view.findViewById(R.id.img_previous_btn);
        img_next_btn = root_view.findViewById(R.id.img_next_btn);
        txt_slide_no = root_view.findViewById(R.id.txt_slide_no);
        viewPager.setCurrentItem(zoomImages.getPosition());
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                txt_slide_no.setText(String.valueOf(position + 1) + "/" + zoomImages.getImageList().size());
                img_previous_btn.setVisibility(View.VISIBLE);
                img_next_btn.setVisibility(View.VISIBLE);
                if (position == 0) {
                    img_previous_btn.setVisibility(View.INVISIBLE);
                }
                if ((position + 1) == zoomImages.getImageList().size()) {
                    img_next_btn.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        img_previous_btn = root_view.findViewById(R.id.img_previous_btn);
        img_previous_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int i = viewPager.getCurrentItem();
                viewPager.setCurrentItem(i - 1);
            }
        });

        img_next_btn = root_view.findViewById(R.id.img_next_btn);
        img_next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int i = viewPager.getCurrentItem();
                viewPager.setCurrentItem(i + 1);
            }
        });

        img_cancel = root_view.findViewById(R.id.img_cancel);
        img_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.onBackPressed();

            }
        });

    }

}
