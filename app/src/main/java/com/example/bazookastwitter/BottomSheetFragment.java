package com.example.bazookastwitter;


import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bazookastwitter.displayTweet.TweetViewModelInterface;

public class BottomSheetFragment extends BottomSheetDialogFragment {
    private TweetViewModelInterface tweet;

    public BottomSheetFragment() {
        // Required empty public constructor
    }

    public void setTweet(TweetViewModelInterface tweet) {
        this.tweet = tweet;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_bottom_sheet, container, false);
        setUpCloseButton(view);

        if (tweet.getImg() != null) {
            ImageView image = view.findViewById(R.id.bottom_sheet_image);
            image.setImageBitmap(tweet.getImg());
            image.setVisibility(View.VISIBLE);
        }

        if (tweet.getPlaceName() != null) {
            TextView place = view.findViewById(R.id.bottom_sheet_place);
            place.setText(tweet.getPlaceName());
            place.setVisibility(View.VISIBLE);
        }

        TextView body = view.findViewById(R.id.bottom_sheet_body);
        TextView date = view.findViewById(R.id.bottom_sheet_date);
        TextView favorites = view.findViewById(R.id.bottom_sheet_favorites_count);
        TextView retweets = view.findViewById(R.id.bottom_sheet_retweet_count);
        body.setText(tweet.getBodyText());
        date.setText(tweet.getDateText());
        favorites.setText(String.valueOf(tweet.getFavoritesCount()));
        retweets.setText(String.valueOf(tweet.getRetweetCount()));
        return view;
    }

    private void setUpCloseButton(View view) {
        Button closeBtn = view.findViewById(R.id.bottom_sheet_closer);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
