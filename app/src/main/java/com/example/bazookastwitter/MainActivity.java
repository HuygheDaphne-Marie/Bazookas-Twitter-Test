package com.example.bazookastwitter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.bzaookastwitter.tweeter.*;

public class MainActivity extends AppCompatActivity {

    private final String LOG_TAG = "myapp:mainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(LOG_TAG, "App started!");

        // Doing the thing
        TwitterSubject subject = new TwitterSubject();
        TwitterObserver observer = new TwitterObserver(subject);
    }
}
