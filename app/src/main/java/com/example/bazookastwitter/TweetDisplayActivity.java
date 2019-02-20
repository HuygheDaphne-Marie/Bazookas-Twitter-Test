package com.example.bazookastwitter;

import java.util.List;

import twitter4j.Status;

public interface TweetDisplayActivity {
    void AddTweetsToView(final List<Status> statuses);
}
