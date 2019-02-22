package com.example.bazookastwitter.displayTweet;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import twitter4j.MediaEntity;
import twitter4j.Status;
import twitter4j.URLEntity;

public class TweetViewModel implements TweetViewModelInterface {
    private final String LOG_TAG = "myapp:TweetViewModel";
    private String headerText;
    private String imgUrl;
    private Bitmap img = null;
    private String bodyText;
    private String dateText;

    public TweetViewModel(@NonNull final Status tweet) {
        setHeaderText("@"+tweet.getUser().getScreenName());
        handleMedia(tweet);
        setBodyText(cleanupBodyText(tweet));
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

    public Bitmap getImg() {
        return img;
    }
    public void setImg(@NonNull Bitmap img) {
        this.img = img;
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
        return tweetText.split(" https://t.co")[0];
    }
    private void handleMedia(@NonNull Status tweet) {
        MediaEntity[] media = tweet.getMediaEntities();
        if (media.length > 0) {
            setImgUrl(media[0].getMediaURL());
        }
    }
}
