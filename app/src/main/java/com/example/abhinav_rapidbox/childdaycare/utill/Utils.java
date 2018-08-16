package com.example.abhinav_rapidbox.childdaycare.utill;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.example.abhinav_rapidbox.childdaycare.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by vikram jha on 8/16/2018.
 */

public class Utils {
    public static void displayImage(Context context, String url, ImageView view) {
        if (TextUtils.isEmpty(url)) {
            view.setImageResource(R.drawable.img_placeholder_square);
            return;
        }
        ImageLoader imageLoader = ImageLoader.getInstance();
        DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true)
                .cacheOnDisk(true).resetViewBeforeLoading(true)
                .showImageForEmptyUri(R.drawable.img_placeholder_square)
                .showImageOnFail(R.drawable.img_placeholder_square)
                .showImageOnLoading(R.drawable.img_placeholder_square)
                .build();

        imageLoader.displayImage(url, view, options);
    }
}
