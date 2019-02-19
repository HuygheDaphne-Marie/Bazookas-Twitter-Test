package com.example.bzaookastwitter.tweeter;

import android.util.Log;

import com.example.bazookastwitter.BuildConfig;

import java.util.ArrayList;
import java.util.List;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterSubject implements TwitterSubjectInterface {
    private final String LOG_TAG = "twitterSubject";
    private Twitter twitter;
    private List<TwitterObserver> observers = new ArrayList<>();
    private volatile List<Status> userTweets = new ArrayList<>();
    private volatile List<Status> hashtagTweets = new ArrayList<>();

    public TwitterSubject() {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(BuildConfig.API_CONSUMERKEY)
                .setOAuthConsumerSecret(BuildConfig.API_CONSUMERSECRET)
                .setOAuthAccessToken(BuildConfig.API_ACCESSTOKEN)
                .setOAuthAccessTokenSecret(BuildConfig.API_ACCESSTOKENSECRET);
        TwitterFactory tf = new TwitterFactory(cb.build());
        twitter = tf.getInstance();

        // TODO does this fit well here??
        fetchHashtagTweets();
        fetchUserTweets();
    }

    @Override
    public List<Status> getUserTweets() {
        return new ArrayList<>(userTweets);
    }

    @Override
    public List<Status> getHashtagTweets() {
        return new ArrayList<>(hashtagTweets);
    }

    @Override
    public void setUserTweets(List<Status> tweets) {
        userTweets = new ArrayList<>(tweets);
        notifyAllObservers("userTweets");
    }

    @Override
    public void setHashtagTweets(List<Status> tweets) {
        hashtagTweets = new ArrayList<>(tweets);
        notifyAllObservers("hashtagTweets");
    }

    @Override
    public void attach(TwitterObserver observer) {
        observers.add(observer);
    }

    @Override
    public void notifyAllObservers(String nameOfListThatGotUpdated) {
        for(TwitterObserver observer : observers) {
            observer.update(nameOfListThatGotUpdated);
        }
    }

    private void fetchUserTweets() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    List<Status> userTweets = new ArrayList<>();
                    for(Status status : twitter.getUserTimeline("wearebazookas")) {
                        if(!status.isRetweet()) {
                            userTweets.add(status);
                        }
                    }
                    setUserTweets(userTweets);
                } catch (TwitterException ex) {
                    ex.printStackTrace();
                    Log.e(LOG_TAG, ex.getErrorMessage());
                }
            }
        }).start();
    }

    private void fetchHashtagTweets() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Query query = new Query("#wearebazookas");
                try {
                    QueryResult result = twitter.search(query);
                    setHashtagTweets(result.getTweets());
                } catch (TwitterException ex) {
                    ex.printStackTrace();
                    Log.e(LOG_TAG, ex.getErrorMessage());

                }
            }
        }).start();
    }
}
