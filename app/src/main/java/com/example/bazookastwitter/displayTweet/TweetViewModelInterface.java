package com.example.bazookastwitter.displayTweet;

import android.support.annotation.NonNull;

import twitter4j.Status;

public interface TweetViewModelInterface {
    @NonNull
    public String getHeaderText();
    public void setHeaderText(@NonNull final String headerText);
    @NonNull
    public String getBodyText();
    public void setBodyText(@NonNull final String bodyText);
    @NonNull
    public String getDateText();
    public void setDateText(@NonNull final String dateText);
}
