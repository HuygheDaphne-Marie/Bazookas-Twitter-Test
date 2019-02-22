package com.example.bazookastwitter.displayTweet;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bazookastwitter.R;

import java.util.List;

public class TweetAdapter extends RecyclerView.Adapter<TweetViewHolder> {
    private List<TweetViewModelInterface> tweetsModels;
    private OnTweetListener onTweetListener;

    public TweetAdapter(List<TweetViewModelInterface> tweetsModels, OnTweetListener onTweetListener) {
        this.tweetsModels = tweetsModels;
        this.onTweetListener = onTweetListener;
    }

    @Override
    public TweetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new TweetViewHolder(view, onTweetListener);
    }

    @Override
    public void onBindViewHolder(TweetViewHolder holder, int position) {
        ((TweetViewHolder) holder).bindData(tweetsModels.get(position));
    }

    @Override
    public int getItemCount() {
        return tweetsModels.size();
    }

    @Override
    public int getItemViewType(final int position) {
        if(tweetsModels.get(position).getImgUrl() == null) {
            return R.layout.tweet;
        } else {
            return R.layout.tweet_with_image;
        }
    }
}
