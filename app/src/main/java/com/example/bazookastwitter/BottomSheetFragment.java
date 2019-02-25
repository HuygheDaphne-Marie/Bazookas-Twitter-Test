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

        Button closeBtn = view.findViewById(R.id.bottom_sheet_closer);
        closeBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        if (tweet.getImg() != null) {
            ImageView image = view.findViewById(R.id.bottom_sheet_image);
            image.setImageBitmap(tweet.getImg());
            image.setVisibility(View.VISIBLE);
        }

        TextView body = view.findViewById(R.id.bottom_sheet_body);
        TextView date = view.findViewById(R.id.bottom_sheet_date);
        body.setText(tweet.getBodyText());
        date.setText(tweet.getDateText());
        return view;
    }
}
