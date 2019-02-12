package com.example.bazookastwitter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.bzaookastwitter.tweeter.*;

public class MainActivity extends AppCompatActivity {

    Tweeter tweeter = new TwitterApi();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
