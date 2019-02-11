package com.example.abhinav_rapidbox.childdaycare.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.abhinav_rapidbox.childdaycare.R;
import com.example.abhinav_rapidbox.childdaycare.listner.OnFragmentListItemSelectListener;

import java.util.List;


/*
public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.MyOrderViewHolder> {
    private int count;
    private Context context;
    OnFragmentListItemSelectListener listener;
    private List<ReviewData> reviewDataArrayList;

    public void setListner(OnFragmentListItemSelectListener listner) {
        this.listener = listner;
    }

    public ReviewAdapter(Context context, List<ReviewData> reviewDataArrayList) {
        this.context = context;
        this.reviewDataArrayList = reviewDataArrayList;
    }

    public MyOrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_review_list_new, parent, false);
        MyOrderViewHolder myOrderViewHolder = new MyOrderViewHolder(view);
        return myOrderViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyOrderViewHolder holder, final int position) {
        final ReviewData reviewData = reviewDataArrayList.get(position);
        holder.description.setSelected(true);
        if (reviewData.getRatingStar() != 0.0) {
            double d = reviewData.getRatingStar();
            float f = (float) d;
            holder.ratingBar.setRating(f);
            holder.ratingBar.setActivated(false);
            holder.ratingBar.setClickable(false);
        }
        if (!TextUtils.isEmpty(reviewData.getHeadline()))
            holder.tv_descriptionTitle.setText(reviewData.getHeadline());
        else
            holder.tv_descriptionTitle.setVisibility(View.GONE);
        Utils.displayImage(context, reviewData.getReviewerIconURL(), holder.userImage);
        holder.tv_userName.setText(reviewData.getReviewerName());
        if (!TextUtils.isEmpty(reviewData.getReviewText())) {
              holder.layoutDescription.setVisibility(View.VISIBLE);
                holder.description.setText(reviewData.getReviewText());
        }

        else holder.layoutDescription.setVisibility(View.GONE);
        if (holder.description.getLineCount() >= 3)
            holder.tv_readMore.setVisibility(View.VISIBLE);
        else
            holder.tv_readMore.setVisibility(View.GONE);
        holder.tv_readMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (holder.description.getLineCount() == 3) {
                holder.tv_readMore.setVisibility(View.GONE);

                holder.description.setMaxLines(20);
//                }
            }
        });
        if (reviewData.getIsAllowedToEdit()) {
            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // listener.onListItemSelected(R.id.linearlayout, reviewData);
                }
            });
        }

        holder.ratingBar.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

    }

    public int getPosition() {
        return count;
    }

    @Override
    public int getItemCount() {
        return null != reviewDataArrayList ? reviewDataArrayList.size() : 0;
    }


    public class MyOrderViewHolder extends RecyclerView.ViewHolder {
        TextView description,
                tv_readMore, tv_descriptionTitle, tv_userName;
        LinearLayout layoutDescription, linearlayout;
        RatingBar ratingBar;
        private View view;
        CircleImageView userImage;


        MyOrderViewHolder(View itemView) {
            super(itemView);
            this.tv_descriptionTitle = itemView.findViewById(R.id.descriptionTitle);
            this.tv_readMore = itemView.findViewById(R.id.readMore);
            this.description = itemView.findViewById(R.id.description);
            this.layoutDescription = itemView.findViewById(R.id.descriptionLayout);
            this.ratingBar = itemView.findViewById(R.id.ratingBar);
            this.linearlayout = itemView.findViewById(R.id.linearlayout);
            this.userImage = itemView.findViewById(R.id.image);
            this.tv_userName = itemView.findViewById(R.id.userName);
            this.view = itemView;
        }
    }


}
*/
