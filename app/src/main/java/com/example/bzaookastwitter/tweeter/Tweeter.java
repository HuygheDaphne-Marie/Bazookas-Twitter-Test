package com.example.bzaookastwitter.tweeter;

import java.util.List;

import twitter4j.Status;

public interface Tweeter {
    public List<Status> getTweetsFromUser(String user);
    public List<Status> getTweetsWithHashtag(String hashtag);
}
