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

import com.example.bazookastwitter.displayTweet.OnTweetListener;
import com.example.bazookastwitter.displayTweet.TweetAdapter;
import com.example.bazookastwitter.displayTweet.TweetViewModel;
import com.example.bazookastwitter.displayTweet.TweetViewModelInterface;
import com.example.bazookastwitter.tweeter.TwitterObserverInterface;
import com.example.bazookastwitter.tweeter.TwitterSubject;
import com.example.bazookastwitter.tweeter.TwitterSubjectInterface;

import java.util.ArrayList;
import java.util.List;

import twitter4j.Status;

public class MainActivity extends AppCompatActivity implements TwitterObserverInterface, OnTweetListener {
    private final String LOG_TAG = "myapp:mainActivity";
    private TwitterSubjectInterface subject;
    private List<TweetViewModelInterface> activeTweets = new ArrayList<>();
    private List<TweetViewModelInterface> userTweets = new ArrayList<>(); // TODO *yuck*
    private List<TweetViewModelInterface> hashtagTweets = new ArrayList<>();

    // Views //
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private Button timelineBtn;
    private Button hashtagsBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.v(LOG_TAG, "App started!");

        // Create & attach to observable
        this.subject = TwitterSubject.getInstance();
        this.subject.attach(this);

        // Setup View
        timelineBtn = findViewById(R.id.toolbar_timeline);
        hashtagsBtn = findViewById(R.id.toolbar_hashtag);
        Toolbar toolbar = findViewById(R.id.toolbar);
        if(toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        recyclerView = findViewById(R.id.tweetRecycler);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new TweetAdapter(this.activeTweets, this);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public void update(final String listName) {
        Log.v(LOG_TAG, "Got update");
        if(listName.equals("userTweets")) {
            updateTweetModelList(userTweets, subject.getUserTweets());
            if(!timelineBtn.isEnabled()) {
                updateRecyclerData(userTweets);
            }
        }

        if(listName.equals("hashtagTweets")) {
            updateTweetModelList(hashtagTweets, subject.getHashtagTweets());
            if(!hashtagsBtn.isEnabled()) {
                updateRecyclerData(hashtagTweets);
            }
        }
    }

    private void updateTweetModelList(List<TweetViewModelInterface> list, List<Status> tweetList) {
        if(list.size() != 0) {
            appendToTweetModels(list, tweetList);
        } else {
            setTweetModels(list, tweetList);
        }
    } // TODO this is a lot of functions all related to eachtoher, class maybe?
    private void setTweetModels(List<TweetViewModelInterface> tweetModels, List<Status> tweets) {
        for(Status tweet :  tweets) {
            tweetModels.add(new TweetViewModel(tweet));
        }
    }
    private void appendToTweetModels(List<TweetViewModelInterface> oldTweets, List<Status> newTweets) {
        boolean gotToOldTweet = false;
        int idx = 0;
        while (!gotToOldTweet) {
            TweetViewModelInterface newTweetModel = new TweetViewModel(newTweets.get(idx));
            if(!newTweetModel.equals(oldTweets.get(idx))) {
                oldTweets.add(0, newTweetModel);
            } else {
                gotToOldTweet = true;
            }
            idx++;
        }
    }
    private void updateRecyclerData(final List<TweetViewModelInterface> newTweetsModels) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                activeTweets.clear();
                activeTweets.addAll(newTweetsModels);
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
            enableButton(timelineBtn);
            disableButton(hashtagsBtn);
            updateRecyclerData(userTweets);
        } else {
            enableButton(hashtagsBtn);
            disableButton(timelineBtn);
            updateRecyclerData(hashtagTweets);
        }
    }

    public void timelinePressed(View view) {
        handlePressed(true, (Button) view);
    }
    public void hashtagsPressed(View view) {
        handlePressed(false, (Button) view);
    }

    @Override
    public void onTweetClick(int position) {
        Log.d(LOG_TAG, activeTweets.get(position).toString());
        showBottomSheetDialogFragment(activeTweets.get(position));
        // get tweet data
        // pass tweet data to modal
        // open modal with tweet data
    }

    public void showBottomSheetDialogFragment(TweetViewModelInterface clickedTweet) {

        BottomSheetFragment bottomSheetFragment = new BottomSheetFragment();
        bottomSheetFragment.setTweet(clickedTweet);
        bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
    }
}
