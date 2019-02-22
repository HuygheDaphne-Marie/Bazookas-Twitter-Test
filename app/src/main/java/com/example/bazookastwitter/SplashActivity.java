package com.example.bazookastwitter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class SplashActivity extends Activity {

    private final String LOG_TAG = "myapp:splashActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.v(LOG_TAG, "Splash here!");

        // TODO start subject here and pass it with the intent to mainActivity? might fix image issue..
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
