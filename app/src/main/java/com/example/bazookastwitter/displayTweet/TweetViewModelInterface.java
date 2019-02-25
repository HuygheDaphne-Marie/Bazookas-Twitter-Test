package com.example.bazookastwitter.displayTweet;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import twitter4j.Place;

public interface TweetViewModelInterface {
    @NonNull
    String getHeaderText();
    void setHeaderText(@NonNull final String headerText);
    @NonNull
    String getBodyText();
    void setBodyText(@NonNull final String bodyText);
    @NonNull
    String getDateText();
    void setDateText(@NonNull final String dateText);

    String getImgUrl();
    void setImgUrl(@NonNull String imgUrl);

    @Override
    boolean equals(Object other);

    @NonNull
    int getFavoritesCount();
    void setFavoritesCount(@NonNull int favoritesCount);
    @NonNull
    int getRetweetCount();
    void setRetweetCount(@NonNull int retweetCount);

    String getPlaceName();
    void setPlaceName(Place place);
}
