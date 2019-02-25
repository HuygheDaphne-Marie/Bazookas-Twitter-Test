package com.example.bazookastwitter.displayTweet;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.bazookastwitter.R;


public class TweetViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private TextView headerTextView, bodyTextView, dateTextView;
    private ImageView imageView;
    private OnTweetListener onTweetListener;

    public TweetViewHolder(@NonNull View itemView, @NonNull OnTweetListener onTweetListener) {
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
            Glide.with(imageView).load(viewModel.getImgUrl()).into(imageView);
        }
        bodyTextView.setText(viewModel.getBodyText());
        dateTextView.setText(viewModel.getDateText());
    }

    @Override
    public void onClick(View v) {
        onTweetListener.onTweetClick(getAdapterPosition());
    }
}
