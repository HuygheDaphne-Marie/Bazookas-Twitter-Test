package com.example.bazookastwitter.tweeter;

import java.util.List;
import twitter4j.Status;

public interface TwitterSubjectInterface {
    List<Status> getUserTweets();
    List<Status> getHashtagTweets();
    void setUserTweets(List<Status> tweets);
    void setHashtagTweets(List<Status> tweets);
    void attach(TwitterObserverInterface observer);
    void notifyAllObservers(String nameOfListThatGotUpdated);
}
