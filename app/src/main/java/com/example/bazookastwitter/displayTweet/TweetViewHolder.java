package com.example.bazookastwitter.displayTweet;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bazookastwitter.R;
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
        if(imageView != null && viewModel.getImg() != null) { // TODO second clause might be redundant..
            imageView.setImageBitmap(viewModel.getImg()); // FIXME any way of doing this async and updating tweet once the image is loaded
            // TODO Ion images kept disappearing randomly, probably because Ion couldn't get them quick enough
//            Ion.with(imageView)
////                    .placeholder(R.drawable.placeholder_image)
////                    .error(R.drawable.error_image) // TODO https://github.com/koush/ion#load-an-image-into-an-imageview
////                    .animateLoad(spinAnimation)
////                    .animateIn(fadeInAnimation)
//                    .load(viewModel.getImgUrl());
        }
        bodyTextView.setText(viewModel.getBodyText());
        dateTextView.setText(viewModel.getDateText());
    }

    @Override
    public void onClick(View v) {
        onTweetListener.onTweetClick(getAdapterPosition());
    }
}
