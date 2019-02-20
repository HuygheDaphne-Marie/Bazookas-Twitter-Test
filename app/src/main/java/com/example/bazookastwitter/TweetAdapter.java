package com.example.bazookastwitter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bzaookastwitter.tweeter.TwitterObserverInterface;
import com.example.bzaookastwitter.tweeter.TwitterSubjectInterface;

import java.util.List;

import twitter4j.Status;

public class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.MyViewHolder> implements TwitterObserverInterface {
    private List<Status> tweets;
    private TwitterSubjectInterface subject;

    // Provide a suitable constructor (depends on the kind of dataset)
    public TweetAdapter(TwitterSubjectInterface subject) {
        this.subject = subject;
        this.subject.attach(this);
        this.tweets = this.subject.getUserTweets();
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView textView;
        public MyViewHolder(TextView v) {
            super(v);
            textView = v;
        }
    }

    @Override
    public void update(String listName) {
        if(listName.equals("userTweets")) {
            this.tweets = subject.getUserTweets();
        }

    }

    // Create new views (invoked by the layout manager)
    @Override
    public TweetAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                        int viewType) {
        // create a new view
        TextView v = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tweet_text, parent, false);
        // ...
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.textView.setText(tweets.get(position).getText());

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return tweets.size();
    }
}
