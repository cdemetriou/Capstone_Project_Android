package com.udacity.capstone.firebase;

import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


import javax.inject.Inject;



@SuppressWarnings("CanBeFinal")
public class FirebaseAnalyticsManager implements AnalyticsInterface {

    private  FirebaseAnalytics firebaseAnalytics;
    private FirebaseUser user;

    @Inject
    public FirebaseAnalyticsManager(FirebaseAnalytics firebaseAnalytics, FirebaseAuth firebaseUser) {
        this.firebaseAnalytics = firebaseAnalytics;
        this.user = firebaseUser.getCurrentUser();
    }


    public void logScreenLaunch(String screenName) {
        String eventName = SCREEN_PROPERTY + screenName;
        firebaseAnalytics.logEvent(eventName, null);
    }

    public void logSetUser() {
        firebaseAnalytics.setUserId(user.getUid());
        firebaseAnalytics.setUserProperty(USER_PROPERTY, user.getEmail());
    }

    public void logAddFavoriteSuccessEvent(String id, String name) {
        Bundle params = new Bundle();
        params.putString(FAV_ID_PROPERTY, id);
        params.putString(FAV_NAME_PROPERTY, name);
        firebaseAnalytics.logEvent(ADD_FAV_SUCCESS_EVENT, params);
    }

    public void logRemoveFavoriteSuccessEvent(String id, String name) {
        Bundle params = new Bundle();
        params.putString(FAV_ID_PROPERTY, id);
        params.putString(FAV_NAME_PROPERTY, name);
        firebaseAnalytics.logEvent(REMOVE_FAV_SUCCESS_EVENT, params);
    }

    public void logAddFavoriteFailureEvent(String exception) {
        Bundle params = new Bundle();
        params.putString(EXCEPTION_PROPERTY, exception);
        firebaseAnalytics.logEvent(ADD_FAV_FAILURE_EVENT, params);
    }

    public void logRemoveFavoriteFailureEvent(String exception) {
        Bundle params = new Bundle();
        params.putString(EXCEPTION_PROPERTY, exception);
        firebaseAnalytics.logEvent(REMOVE_FAV_FAILURE_EVENT, params);
    }

    public void logDataRequestSuccessEvent(String request) {
        Bundle params = new Bundle();
        params.putString(REQUEST_PROPERTY, request);
        firebaseAnalytics.logEvent(REQUEST_SUCCESS_EVENT, params);
    }

    public void logDataRequestFailureEvent(String request, String exception) {
        Bundle params = new Bundle();
        params.putString(REQUEST_PROPERTY, request);
        params.putString(EXCEPTION_PROPERTY, exception);
        firebaseAnalytics.logEvent(REQUEST_FAILURE_EVENT, params);
    }

}
