package com.example.bazookastwitter.tweeter;

import android.util.Log;

import com.example.bazookastwitter.BuildConfig;

import java.util.ArrayList;
import java.util.List;

import twitter4j.FilterQuery;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterSubject implements TwitterSubjectInterface {
    private final String LOG_TAG = "myapp:twitterSubject";
    private Twitter twitter;
    private TwitterStream tStream;
    private List<TwitterObserverInterface> observers = new ArrayList<>();
    private volatile List<Status> userTweets = new ArrayList<>();
    private volatile List<Status> hashtagTweets = new ArrayList<>();

    private static final TwitterSubject instance = new TwitterSubject();

    private TwitterSubject() {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(BuildConfig.API_CONSUMERKEY)
                .setOAuthConsumerSecret(BuildConfig.API_CONSUMERSECRET)
                .setOAuthAccessToken(BuildConfig.API_ACCESSTOKEN)
                .setOAuthAccessTokenSecret(BuildConfig.API_ACCESSTOKENSECRET);
        Configuration conf = cb.build();
        twitter = new TwitterFactory(conf).getInstance();
        tStream = new TwitterStreamFactory(conf).getInstance();

        // TODO does this fit well here??
        fetchHashtagTweets("#wearebazookas"); // TODO make these class properties??
        fetchUserTweets("wearebazookas");
        startStream("#wearebazookas", "wearebazookas");
    }

    public static TwitterSubject getInstance() {
        return instance;
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
    public void attach(TwitterObserverInterface observer) {
        observers.add(observer);
    }
    @Override
    public void notifyAllObservers(String nameOfListThatGotUpdated) {
        for(TwitterObserverInterface observer : observers) {
            observer.update(nameOfListThatGotUpdated);
        }
    }

    private void appendUserTweets(Status status) {
        userTweets.add(0, status);
        notifyAllObservers("userTweets");

    }
    private void appendHashtagTweets(Status status) {
        hashtagTweets.add(0, status);
        notifyAllObservers("hashtagTweets");
    }

    private void fetchUserTweets(final String screenName) {
        Log.v(LOG_TAG, "Fetching user tweets");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    List<Status> userTweets = new ArrayList<>();
                    for(Status status : twitter.getUserTimeline(screenName)) {
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
    private void fetchHashtagTweets(final String tag) {
        Log.v(LOG_TAG, "Fetching hashtag tweets");
        new Thread(new Runnable() {
            @Override
            public void run() {
                Query query = new Query(tag);
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

    private void startStream(final String hashtag, final String screenName) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Long userId = null;
                    for(Status status : twitter.getUserTimeline(screenName)) {
                        if (userId == null && !status.isRetweet()) {
                            userId = status.getUser().getId();
                            startStream(hashtag, userId);
                        }
                    }
                } catch (TwitterException e) {
                    e.printStackTrace();
                    Log.e(LOG_TAG, "Something went wrong trying to get the provided screenName's ID");
                }
            }
        }).start();
    }
    private void startStream(final String hashtag, long userId) {
        Log.v(LOG_TAG, "Starting stream");
        StatusListener listener = new StatusListener(){
            public void onStatus(Status status) {
                if(status.getUser().getId() == userTweets.get(0).getUser().getId()) {
                    appendUserTweets(status);
                }
                if(status.getText().contains(hashtag)) {
                    appendHashtagTweets(status);
                }
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
        FilterQuery fq = new FilterQuery();
        String keywords[] = {hashtag};
        fq.track(keywords);
        fq.follow(userId);
        tStream.addListener(listener);
        tStream.filter(fq);
    }
}
