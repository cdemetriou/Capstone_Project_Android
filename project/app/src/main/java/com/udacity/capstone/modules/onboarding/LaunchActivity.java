package com.udacity.capstone.modules.onboarding;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.udacity.capstone.R;
import com.udacity.capstone.utils.Utils;

/**
 * Created by christosdemetriou on 27/04/2018.
 */

public class LaunchActivity extends AppCompatActivity {

    private static final int SPLASH_TIME = 3000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        new BackgroundTask().execute();
    }


    private class BackgroundTask extends AsyncTask {

        // Do any required loading in here
        @Override
        protected Object doInBackground(Object[] params) {

            try {
                Thread.sleep(SPLASH_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);

            // Here check if user is logged in
            startActivity(new Intent(LaunchActivity.this, OnboardingActivity.class));
            finish();
        }
    }
}
