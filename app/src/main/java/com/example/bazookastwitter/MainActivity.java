package com.example.bazookastwitter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import com.example.bzaookastwitter.tweeter.*;

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

//    private LinearLayout makeTweet(Status status, Context context) {
//        LinearLayout tweet = createTweetBody(context);
//
//        tweet.addView(tweetHeader(status));
//        tweet.addView(tweetText(status));
//
//        return tweet;
//    }
//
//    private LinearLayout createTweetBody( Context context) {
//        LinearLayout tweet = (LinearLayout) getLayoutInflater().inflate(R.layout.tweet_body, null);
//
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//        float d = context.getResources().getDisplayMetrics().density;
//        int margin = (int)(8*d);
//
//        params.setMargins(0, margin, 0, margin);
//        tweet.setLayoutParams(params);
//        return tweet;
//    }
//
//    private TextView tweetHeader(Status status) {
//        TextView textView = (TextView) getLayoutInflater().inflate(R.layout.tweet_header, null);
//        String text = "@"+status.getUser().getScreenName().toUpperCase();
//        textView.setText(text);
//        return textView;
//    }
//
//    private TextView tweetText(Status status) {
//        TextView textView = (TextView) getLayoutInflater().inflate(R.layout.tweet_text, null);
//        String tweetText = status.getText().replaceAll("\\n", "");
//        textView.setText(tweetText);
//        return textView;
//    }
}
