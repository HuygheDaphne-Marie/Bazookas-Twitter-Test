package com.example.bzaookastwitter.tweeter;

import android.util.Log;
import android.widget.LinearLayout;
import com.example.bazookastwitter.TweetDisplayActivity;
import java.util.List;
import twitter4j.Status;

public class TwitterObserver implements TwitterObserverInterface {
    private TwitterSubjectInterface subject;
    private TweetDisplayActivity activity;

    public TwitterObserver(TwitterSubjectInterface subject) {
        this.subject = subject;
        this.subject.attach(this);
//        this.activity = activity;
    }

    public List<Status> getUserTweets() {
        return subject.getUserTweets();
    }

    @Override
    public void update(String listName) {
        Log.v("myapp", "Update!");
        if(listName.equals("userTweets")) {
//            activity.AddTweetsToView(subject.getUserTweets());
//            for(Status status : subject.getUserTweets()) {
//                Log.d("myapp:usertweet", status.getText());
//            }
        }
//
        if(listName.equals("hashtagTweets")) {
//            activity.AddTweetsToView(subject.getHashtagTweets());
//            for(Status status : subject.getHashtagTweets()) {
//                Log.d("myapp:hashtag", status.getText());
//            }
        }
    }
}
