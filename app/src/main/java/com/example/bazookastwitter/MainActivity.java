package com.example.bazookastwitter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.bzaookastwitter.tweeter.*;

import java.util.List;

import twitter4j.Status;

public class MainActivity extends AppCompatActivity implements TweetDisplayActivity {

    private final String LOG_TAG = "myapp:mainActivity";
    LinearLayout ll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ll = findViewById(R.id.tweetContainer);
        Log.v(LOG_TAG, "App started!");

        // Doing the thing
        TwitterSubject subject = new TwitterSubject();
        TwitterObserver observer = new TwitterObserver(subject,this);
    }

    @Override
    public void AddTweetsToView(final List<Status> statuses) {
        final Context context = this;
        runOnUiThread(new Runnable() {

            @Override
            public void run() {

                for(Status status : statuses) {
                    Log.d(LOG_TAG, status.getText());
                    ll.addView(makeTweet(status, context));
                }
            }
        });
    }

    private LinearLayout makeTweet(Status status, Context context) {
        LinearLayout tweet = createTweetBody(context);

        tweet.addView(tweetHeader(status));
        tweet.addView(tweetText(status));

        return tweet;
    }

    private LinearLayout createTweetBody( Context context) {
        LinearLayout tweet = (LinearLayout) getLayoutInflater().inflate(R.layout.tweet_body, null);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        float d = context.getResources().getDisplayMetrics().density;
        int margin = (int)(8*d);

        params.setMargins(0, margin, 0, margin);
        tweet.setLayoutParams(params);
        return tweet;
    }

    private TextView tweetHeader(Status status) {
        TextView textView = (TextView) getLayoutInflater().inflate(R.layout.tweet_header, null);
        String text = "@"+status.getUser().getScreenName().toUpperCase();
        textView.setText(text);
        return textView;
    }

    private TextView tweetText(Status status) {
        TextView textView = (TextView) getLayoutInflater().inflate(R.layout.tweet_text, null);
        String tweetText = status.getText().replaceAll("\\n", "");
        textView.setText(tweetText);
        return textView;
    }
}
