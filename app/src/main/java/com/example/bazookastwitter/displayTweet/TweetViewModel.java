package com.example.bazookastwitter.displayTweet;

import android.support.annotation.NonNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import twitter4j.MediaEntity;
import twitter4j.Place;
import twitter4j.Status;
import twitter4j.URLEntity;

public class TweetViewModel implements TweetViewModelInterface {
    private String headerText, imgUrl, bodyText, dateText, placeName;
    private int favoritesCount, retweetCount;

    public TweetViewModel(@NonNull final Status tweet) {
        setHeaderText("@"+tweet.getUser().getScreenName());
        handleMedia(tweet);
        setBodyText(cleanupBodyText(tweet));
        DateFormat df = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);
        setDateText(df.format(tweet.getCreatedAt()));
        setFavoritesCount(tweet.getFavoriteCount());
        setRetweetCount(tweet.getRetweetCount());
        setPlaceName(tweet.getPlace());
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
    @NonNull
    public int getFavoritesCount() {
        return favoritesCount;
    }
    public void setFavoritesCount(@NonNull int favoritesCount) {
        this.favoritesCount = favoritesCount;
    }
    @NonNull
    public int getRetweetCount() {
        return retweetCount;
    }
    public void setRetweetCount(@NonNull int retweetCount) {
        this.retweetCount = retweetCount;
    }

    public String getPlaceName() {
        return placeName;
    }
    public void setPlaceName(Place place) {
        if(place != null) {
            this.placeName = place.getName();
        }
    }

    public String getImgUrl() {
        return imgUrl;
    }
    public void setImgUrl(@NonNull String imgUrl) {
        this.imgUrl = imgUrl;
    }

    private String cleanupBodyText(@NonNull Status tweet) {
        String tweetText = tweet.getText();
        for(URLEntity url : tweet.getURLEntities()) {
            tweetText = tweetText.replace(url.getURL(), url.getDisplayURL());
        }
        tweetText = tweetText.replaceAll("\\n", "");
        return tweetText.split(" https://t.co")[0];
    }
    private void handleMedia(@NonNull Status tweet) {
        MediaEntity[] media = tweet.getMediaEntities();
        if (media.length > 0) {
            setImgUrl(media[0].getMediaURL());
        }
    }
    @Override
    public boolean equals(Object other) {
        if(other.getClass() == this.getClass()) {
            TweetViewModel otherTweetViewModel = (TweetViewModel) other;
            return otherTweetViewModel.bodyText.equals(this.bodyText);
        } else {
            return false;
        }
    }
}
