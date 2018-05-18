package com.udacity.capstone;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.udacity.capstone.firebase.FirebaseAnalyticsManager;

import javax.inject.Inject;

import icepick.Icepick;


@SuppressWarnings("WeakerAccess")
public abstract class BaseActivity extends AppCompatActivity {

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Icepick.restoreInstanceState(this, savedInstanceState);
    }

    @Override public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Icepick.saveInstanceState(this, outState);
    }

    @Inject
    FirebaseAnalyticsManager analyticsManager;

    @Override
    protected void onStart() {
        super.onStart();
        trackScreenView();
    }

    protected void trackScreenView() {
        String screenName = getScreenNameForAnalytics();
        analyticsManager.logScreenLaunch(screenName);
    }

    public abstract String getScreenNameForAnalytics();

}
