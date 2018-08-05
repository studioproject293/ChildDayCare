package com.example.abhinav_rapidbox.childdaycare.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.abhinav_rapidbox.childdaycare.R;


public class ProductViewPagerAdapter extends PagerAdapter {

    LayoutInflater layoutInflater;
    Context context;
    int[] imgArra;

    public ProductViewPagerAdapter(Context context, int[] imgArra) {
        this.context = context;
        this.imgArra = imgArra;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return imgArra.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }


    public Object instantiateItem(ViewGroup container, final int position) {
        final View itemView = layoutInflater.inflate(R.layout.row_productimage, container, false);
        final ImageView productimage = (ImageView) itemView.findViewById(R.id.img_product);
        productimage.setImageResource(imgArra[position]);
        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

}
