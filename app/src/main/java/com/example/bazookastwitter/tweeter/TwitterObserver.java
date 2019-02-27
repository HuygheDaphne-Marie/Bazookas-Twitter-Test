package com.example.bazookastwitter.tweeter;

import android.util.Log;

import java.util.List;
import twitter4j.Status;

public class TwitterObserver implements TwitterObserverInterface {
    private TwitterSubjectInterface subject;

    public TwitterObserver(TwitterSubjectInterface subject) {
        this.subject = subject;
        this.subject.attach(this);
    }

    public List<Status> getUserTweets() {
        return subject.getUserTweets();
    }
    public List<Status> getHashtagTweets() {
        return subject.getHashtagTweets();
    }

    @Override
    public void twitterFeedUpdated(String listName) {
        Log.v("myapp", "Update!");
        if(listName.equals("userTweets")) {
            for(Status status : subject.getUserTweets()) {
                Log.d("myapp:usertweet", status.getText());
            }
        }

        if(listName.equals("hashtagTweets")) {
            for(Status status : subject.getHashtagTweets()) {
                Log.d("myapp:hashtag", status.getText());
            }
        }
    }
}
