package com.udacity.capstone;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.udacity.capstone.firebase.FirebaseAnalyticsManager;

import javax.inject.Inject;

import icepick.Icepick;



@SuppressWarnings("WeakerAccess")
public abstract class BaseFragment extends Fragment {

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Icepick.restoreInstanceState(this, savedInstanceState);
    }

    @Override public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Icepick.saveInstanceState(this, outState);
    }

    @Inject
    FirebaseAnalyticsManager analyticsManager;

    @Override
    public void onStart() {
        super.onStart();
        trackScreenView();
    }

    protected void trackScreenView() {
        String screenName = getScreenNameForAnalytics();
        analyticsManager.logScreenLaunch(screenName);
    }

    public abstract String getScreenNameForAnalytics();

}
