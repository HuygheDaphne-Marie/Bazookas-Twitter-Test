package com.example.bazookastwitter;

import android.support.annotation.NonNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import twitter4j.Status;

public class TweetViewModel implements TweetViewModelInterface {
    private String headerText;
    private String bodyText;
    private String dateText;

    public TweetViewModel(@NonNull final Status tweet) {
        setHeaderText("@"+tweet.getUser().getScreenName());
        setBodyText(tweet.getText());
        DateFormat df = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);
        setDateText(df.format(tweet.getCreatedAt()));
    }

    @NonNull
    public String getHeaderText() {
        return headerText;
    }
    public void setHeaderText(@NonNull final String headerText) {
        this.headerText = headerText;
    }
    @NonNull
    public String getBodyText() {
        return bodyText;
    }
    public void setBodyText(@NonNull final String bodyText) {
        this.bodyText = bodyText;
    }
    @NonNull
    public String getDateText() {
        return dateText;
    }
    public void setDateText(@NonNull final String dateText) {
        this.dateText = dateText;
    }
}
