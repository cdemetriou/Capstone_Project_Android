package com.udacity.capstone.modules.onboarding;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.udacity.capstone.R;

import static com.udacity.capstone.data.Constants.SPLASH_TIME;


public class LaunchActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        new BackgroundTask().execute();
    }


    private class BackgroundTask extends AsyncTask {

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

            startActivity(new Intent(LaunchActivity.this, OnboardingActivity.class));
            finish();
        }
    }
}
