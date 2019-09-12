package com.example.abhinav_rapidbox.childdaycare.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.abhinav_rapidbox.childdaycare.R;
import com.example.abhinav_rapidbox.childdaycare.listner.OnFragmentListItemSelectListener;
import com.example.abhinav_rapidbox.childdaycare.pojo.ZoomImage;
import com.example.abhinav_rapidbox.childdaycare.utill.Utils;

import java.util.ArrayList;
import java.util.List;


public class ProductDetailsViewPagerAdapter extends PagerAdapter {

    LayoutInflater layoutInflater;
    Context context;
    List<String> OtherImages;
    OnFragmentListItemSelectListener listener;

    public ProductDetailsViewPagerAdapter(Context context, List<String> OtherImages) {
        this.context = context;
        this.OtherImages = OtherImages;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setListner(OnFragmentListItemSelectListener listner) {
        this.listener = listner;
    }

    @Override
    public int getCount() {
        return OtherImages.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }


    public Object instantiateItem(ViewGroup container, final int position) {
        final View itemView = layoutInflater.inflate(R.layout.row_productimage, container, false);
        final ImageView productimage = (ImageView) itemView.findViewById(R.id.img_product);
        Utils.displayImage(context,"http://"+ OtherImages.get(position), productimage);
        //Glide.with(context).load(OtherImages.get(position)).into(productimage);
        container.addView(itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  listener.onListItemSelected(R.id.img_product,OtherImages);
                ZoomImage zoomImage = new ZoomImage();
                zoomImage.setImageList((ArrayList<String>) OtherImages);
                zoomImage.setPosition(position);
                listener.onListItemSelected(R.id.img_product, zoomImage);
               /* Intent i = new Intent(context, ImagefullscreenActivity.class);
                i.putStringArrayListExtra("imageUrls",(ArrayList<String>) OtherImages);
                i.putExtra("position",OtherImages.get(position));
                context.startActivity(i);*/
            }
        });
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

}
