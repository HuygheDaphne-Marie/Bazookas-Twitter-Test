package com.example.bazookastwitter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.bazookastwitter.displayTweet.TweetAdapter;
import com.example.bazookastwitter.displayTweet.TweetViewModel;
import com.example.bazookastwitter.displayTweet.TweetViewModelInterface;
import com.example.bazookastwitter.tweeter.TwitterObserverInterface;
import com.example.bazookastwitter.tweeter.TwitterSubject;
import com.example.bazookastwitter.tweeter.TwitterSubjectInterface;

import java.util.ArrayList;
import java.util.List;
import twitter4j.Status;

public class MainActivity extends AppCompatActivity implements TwitterObserverInterface {

    private final String LOG_TAG = "myapp:mainActivity";
    private TwitterSubjectInterface subject;
    private List<TweetViewModelInterface> tweets = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.v(LOG_TAG, "App started!");

        // Create & attach to observable
        this.subject = new TwitterSubject();
        this.subject.attach(this);

        // Setup View
        recyclerView = findViewById(R.id.tweetRecycler);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new TweetAdapter(this.tweets);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void update(final String listName) {
        Log.d(LOG_TAG, "Got update");

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(listName.equals("userTweets")) {
                    Log.d(LOG_TAG, "Setting user tweets");
                    tweets.clear();
                    for(Status tweet : subject.getUserTweets()) {
                        tweets.add(new TweetViewModel(tweet));
                    }

                }
                mAdapter.notifyDataSetChanged();
            }
        });

    }
}
