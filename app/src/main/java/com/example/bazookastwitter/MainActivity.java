package com.example.bazookastwitter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.bzaookastwitter.tweeter.*;

import twitter4j.DirectMessage;
import twitter4j.FilterQuery;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.User;
import twitter4j.UserList;
import twitter4j.UserStreamListener;
import twitter4j.conf.ConfigurationBuilder;

public class MainActivity extends AppCompatActivity {

    private final String LOG_TAG = "myapp:mainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(LOG_TAG, "App started!");

        // Doing the thing
        TwitterSubject subject = new TwitterSubject();
        TwitterObserver observer = new TwitterObserver(subject);
        doThing();
    }

    private void doThing() {
        StatusListener listener = new StatusListener(){
            public void onStatus(Status status) {
                Log.d(LOG_TAG, status.getUser().getName() + " : " + status.getText());
            }
            public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {}
            public void onTrackLimitationNotice(int numberOfLimitedStatuses) {}

            @Override
            public void onScrubGeo(long l, long l1) {

            }

            @Override
            public void onStallWarning(StallWarning stallWarning) {

            }

            public void onException(Exception ex) {
                ex.printStackTrace();
            }
        };

        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(BuildConfig.API_CONSUMERKEY)
                .setOAuthConsumerSecret(BuildConfig.API_CONSUMERSECRET)
                .setOAuthAccessToken(BuildConfig.API_ACCESSTOKEN)
                .setOAuthAccessTokenSecret(BuildConfig.API_ACCESSTOKENSECRET);


        TwitterStream twitterStream = new TwitterStreamFactory(cb.build()).getInstance();

        FilterQuery fq = new FilterQuery();
        String keywords[] = {"#wearebazookas"};
        fq.track(keywords);
        fq.follow(1094953255879278592L);
        twitterStream.addListener(listener);
        twitterStream.filter(fq);

    }
}
