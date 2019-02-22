package com.example.bazookastwitter;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import com.example.bazookastwitter.displayTweet.TweetAdapter;
import com.example.bazookastwitter.displayTweet.TweetViewModel;
import com.example.bazookastwitter.displayTweet.TweetViewModelInterface;
import com.example.bazookastwitter.tweeter.TwitterObserverInterface;
import com.example.bazookastwitter.tweeter.TwitterSubject;
import com.example.bazookastwitter.tweeter.TwitterSubjectInterface;

import java.util.ArrayList;
import java.util.List;

import twitter4j.MediaEntity;
import twitter4j.Status;
import twitter4j.URLEntity;

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
        Toolbar toolbar = findViewById(R.id.toolbar);
        if(toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        recyclerView = findViewById(R.id.tweetRecycler);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new TweetAdapter(this.tweets);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public void update(final String listName) {
        Log.v(LOG_TAG, "Got update");
        if(listName.equals("userTweets")) {
            Log.v(LOG_TAG, "Setting user tweets");
            updateRecyclerData(subject.getUserTweets());
        } else {
            Log.v(LOG_TAG, "Setting hashtag tweets");
            updateRecyclerData(subject.getHashtagTweets());
        }

    }

    private void updateRecyclerData(final List<Status> newTweets) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tweets.clear();
                for(Status tweet : newTweets) {
                    tweets.add(new TweetViewModel(tweet));
                }
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    private void enableButton(Button view) {
        view.setEnabled(false);
        view.setTextColor(Color.WHITE);
        view.setBackground(getDrawable(R.drawable.active_btn_rounded));
    }
    private void disableButton(Button view) {
        view.setEnabled(true);
        view.setTextColor(ContextCompat.getColor(this, R.color.bazookasPrimary));
        view.setBackground(getDrawable(R.drawable.inactive_btn_rounded));
    }

    private void handlePressed(boolean isTimeline, Button view) {
        if (isTimeline) {
            enableButton((Button) findViewById(R.id.toolbar_timeline));
            disableButton((Button) findViewById(R.id.toolbar_hashtag));
            updateRecyclerData(subject.getUserTweets());
        } else {
            enableButton((Button) findViewById(R.id.toolbar_hashtag));
            disableButton((Button) findViewById(R.id.toolbar_timeline));
            updateRecyclerData(subject.getHashtagTweets());
        }
    }

    public void timelinePressed(View view) {
        Log.d(LOG_TAG, "timelinePressed");
        handlePressed(true, (Button) view);
    }

    public void hashtagsPressed(View view) {
        Log.d(LOG_TAG, "hashtagsPressed");
        handlePressed(false, (Button) view);
    }
}
