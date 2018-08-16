package com.example.abhinav_rapidbox.childdaycare.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.abhinav_rapidbox.childdaycare.R;
import com.example.abhinav_rapidbox.childdaycare.utill.Utils;
import com.example.abhinav_rapidbox.childdaycare.zoomimage.TouchImageView;

import java.util.List;


public class FullIImageAdapter extends PagerAdapter {
    Context context;
    LayoutInflater layoutInflater;
    List<String> imageUrls;


    public FullIImageAdapter(Context context, List<String> imageUrls) {
        this.context = context;
        this.imageUrls = imageUrls;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return imageUrls.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    public Object instantiateItem(ViewGroup container, final int position) {
        View itemView = layoutInflater.inflate(R.layout.row_imagefullview, container, false);
        TouchImageView imageView = itemView.findViewById(R.id.imageViewfull);
        Utils.displayImage(this.context, imageUrls.get(position), imageView);
        container.addView(itemView);
        return itemView;
    }

    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}
