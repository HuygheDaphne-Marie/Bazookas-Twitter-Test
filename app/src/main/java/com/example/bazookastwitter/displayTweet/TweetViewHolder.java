package com.example.bazookastwitter.displayTweet;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bazookastwitter.R;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.ImageViewBitmapInfo;
import com.koushikdutta.ion.Ion;

public class TweetViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private TextView headerTextView, bodyTextView, dateTextView;
    private ImageView imageView;
    private OnTweetListener onTweetListener;

    public TweetViewHolder(@NonNull View itemView, OnTweetListener onTweetListener) {
        super(itemView);
        headerTextView = itemView.findViewById(R.id.header_text);
        imageView = itemView.findViewById(R.id.content_image);
        bodyTextView = itemView.findViewById(R.id.body_text);
        dateTextView = itemView.findViewById(R.id.date_text);
        this.onTweetListener = onTweetListener;
        itemView.setOnClickListener(this);
    }

    public void bindData(final TweetViewModelInterface viewModel) {
        headerTextView.setText(viewModel.getHeaderText());
        if(imageView != null) {
            if(viewModel.getImg() != null) {
                imageView.setImageBitmap(viewModel.getImg());
            } else {
                Ion.with(imageView)// TODO glide
                        .load(viewModel.getImgUrl())
                        .withBitmapInfo()
                        .setCallback(new FutureCallback<ImageViewBitmapInfo>() {
                            @Override
                            public void onCompleted(Exception e, ImageViewBitmapInfo result) {
                                Bitmap b = result.getBitmapInfo().bitmap;
                                viewModel.setImg(b);
                            }
                        });
                }
        }
        bodyTextView.setText(viewModel.getBodyText());
        dateTextView.setText(viewModel.getDateText());
    }

    @Override
    public void onClick(View v) {
        onTweetListener.onTweetClick(getAdapterPosition());
    }
}
