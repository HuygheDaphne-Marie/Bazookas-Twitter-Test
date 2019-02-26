package com.example.bazookastwitter;

import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
        setupObservable(this);

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
    public void onTweetClick(int position) {
        showBottomSheetDialogFragment(activeTweets.get(position));
    }
    private void showBottomSheetDialogFragment(TweetViewModelInterface clickedTweet) {
        BottomSheetFragment bottomSheetFragment = new BottomSheetFragment();
        bottomSheetFragment.setTweet(clickedTweet);
        bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
    }

    private void setupObservable(Context context) {
        if(checkNetworkConnection(context)) {
            this.subject = new TwitterSubject("wearebazookas", "#wearebazookas");
            this.subject.attach(this);
        } else {
            Log.v(LOG_TAG, "No internet connection :^( ");
        }
    }
    private boolean checkNetworkConnection(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

    @Override
    public void update(final String listName) {
        Log.v(LOG_TAG, "Got update");
        if(listName.equals("userTweets") && !timelineBtn.isEnabled()) {
            updateRecyclerData(subject.getUserTweets());
        }

        if(listName.equals("hashtagTweets") && !hashtagsBtn.isEnabled()) {
            updateRecyclerData(subject.getHashtagTweets());
        }
    }
    private void updateRecyclerData(final List<Status> tweets) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                activeTweets.clear();
                for(Status tweet : tweets) {
                    activeTweets.add(new TweetViewModel(tweet));
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
            enableButton(timelineBtn);
            disableButton(hashtagsBtn);
            updateRecyclerData(subject.getUserTweets());
        } else {
            enableButton(hashtagsBtn);
            disableButton(timelineBtn);
            updateRecyclerData(subject.getHashtagTweets());
        }
    }

    public void timelinePressed(View view) {
        handlePressed(true, (Button) view);
    }
    public void hashtagsPressed(View view) {
        handlePressed(false, (Button) view);
    }
}
