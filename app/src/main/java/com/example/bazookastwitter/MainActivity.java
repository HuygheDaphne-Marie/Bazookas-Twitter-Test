package com.example.bazookastwitter;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
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

                    TextView textView = new TextView(context);
                    textView.setTextSize(20);
                    String tweetText = status.getUser().getScreenName()+ ": " + status.getText();
                    textView.setText(tweetText);
                    ll.addView(textView);
                }
                ll.invalidate();

            }
        });

    }
}
