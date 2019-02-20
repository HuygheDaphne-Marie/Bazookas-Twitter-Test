package com.example.bzaookastwitter.tweeter;

import java.util.List;
import twitter4j.Status;

public interface TwitterSubjectInterface {
    public List<Status> getUserTweets();
    public List<Status> getHashtagTweets();
    public void setUserTweets(List<Status> tweets);
    public void setHashtagTweets(List<Status> tweets);
    public void attach(TwitterObserver observer);
    public void notifyAllObservers(String nameOfListThatGotUpdated);
}
