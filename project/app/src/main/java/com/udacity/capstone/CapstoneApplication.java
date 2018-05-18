package com.udacity.capstone;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.udacity.capstone.injection.AppComponent;
import com.udacity.capstone.injection.AppModule;
import com.udacity.capstone.injection.DaggerAppComponent;
import com.udacity.capstone.injection.NetModule;
import com.udacity.capstone.widget.WidgetManager;

import javax.inject.Inject;


@SuppressWarnings("WeakerAccess")
public class CapstoneApplication extends Application {

    private AppComponent appComponent;

    private static WidgetManager widgetManager;

    @Inject
    SharedPreferences sharedPreferences;

    @Inject
    FirebaseAnalytics firebaseAnalytics;

    @Override
    public void onCreate() {
        super.onCreate();

        widgetManager = new WidgetManager(this);

        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .netModule(new NetModule(this))
                .build();

        appComponent.inject(this);

        firebaseAnalytics.setAnalyticsCollectionEnabled(sharedPreferences.getBoolean(getString(R.string.pref_key_analytics_enabled), true));
    }

    public static CapstoneApplication get(Context context) {
        return (CapstoneApplication) context.getApplicationContext();
    }

    public static AppComponent getApplicationComponent(Context context) {
        return  get(context).appComponent;
    }

    public static WidgetManager getWidgetManager(){
        return widgetManager;
    }
}
