package com.example.bazookastwitter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class TweetViewHolder extends RecyclerView.ViewHolder {
    private TextView headerTextView;
    private TextView bodyTextView;
    private TextView dateTextView;

    public TweetViewHolder(@NonNull View itemView) {
        super(itemView);
        headerTextView = itemView.findViewById(R.id.header_text);
        bodyTextView = itemView.findViewById(R.id.body_text);
        dateTextView = itemView.findViewById(R.id.date_text);
    }

    public void bindData(final TweetViewModelInterface viewModel) {
        headerTextView.setText(viewModel.getHeaderText());
        bodyTextView.setText(viewModel.getBodyText());
        dateTextView.setText(viewModel.getDateText());
    }
}
