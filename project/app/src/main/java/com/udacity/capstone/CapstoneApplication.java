package com.udacity.capstone;

import android.app.Application;
import android.content.Context;

import com.udacity.capstone.data.Constants;
import com.udacity.capstone.injection.AppComponent;
import com.udacity.capstone.injection.AppModule;
import com.udacity.capstone.injection.DaggerAppComponent;
import com.udacity.capstone.injection.NetModule;

import timber.log.Timber;

/**
 * Created by christosdemetriou on 18/04/2018.
 */

public class CapstoneApplication extends Application {

    private AppComponent appComponent;
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
        appComponent = DaggerAppComponent.builder()
                // list of modules that are part of this component need to be created here too
                .appModule(new AppModule(this))
                .netModule(new NetModule(this, Constants.MARVEL_BASE_URL))
                .build();
    }

    public static CapstoneApplication get(Context context) {
        return (CapstoneApplication) context.getApplicationContext();
    }

    public static AppComponent getApplicationComponent(Context context) {
        return  get(context).appComponent;
    }

    public static Context getContext() {
        return mContext;
    }


}
