package com.udacity.capstone.injection;

import android.content.Context;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Logger;
import com.udacity.capstone.BuildConfig;
import com.udacity.capstone.data.remote.MarvelService;
import com.udacity.capstone.data.remote.Repository;
import com.udacity.capstone.firebase.FirebaseAnalyticsManager;
import com.udacity.capstone.firebase.FirebaseDatabaseManager;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



@SuppressWarnings("CanBeFinal")
@Module
public class NetModule {

    private Context context;
    private String mBaseUrl;

    public NetModule(Context context) {
        this.context = context;
        this.mBaseUrl = com.udacity.capstone.data.Constants.MARVEL_BASE_URL;
    }

    @Provides
    @Singleton
    Context provideContext() {
        return context;
    }

    @Provides
    @Singleton
    FirebaseAuth getFirebaseAuth() {
        return FirebaseAuth.getInstance();
    }

    @Provides
    @Singleton
    FirebaseAnalytics provideFirebaseAnalytics() {
        return FirebaseAnalytics.getInstance(context);
    }

    @Provides
    @Singleton
    FirebaseAnalyticsManager provideFirebaseAnalyticsManager(FirebaseAnalytics analytics, FirebaseAuth auth) {
        return new FirebaseAnalyticsManager(analytics, auth);
    }

    @Provides
    @Singleton
    FirebaseDatabase provideFirebaseDatabase() {
        return FirebaseDatabase.getInstance();
    }

    @Provides
    @Singleton
    FirebaseDatabaseManager provideFirebaseDatabaseManager(FirebaseAuth auth, FirebaseDatabase database, FirebaseAnalyticsManager analyticsManager) {
        database.setPersistenceEnabled(true);
        database.setLogLevel(BuildConfig.DEBUG ? Logger.Level.DEBUG : Logger.Level.NONE);
        return new FirebaseDatabaseManager(auth, database, analyticsManager);
    }

    @Provides
    @Singleton
    HttpLoggingInterceptor provideInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
        return interceptor;
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(HttpLoggingInterceptor interceptor) {
        return new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(10000, TimeUnit.SECONDS)
                .writeTimeout(10000, TimeUnit.SECONDS)
                .readTimeout(30000, TimeUnit.SECONDS)
                .build();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(OkHttpClient client) {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(mBaseUrl)
                .client(client)
                .build();
    }

    @Provides
    @Singleton
    MarvelService provideDataService(Retrofit retrofit) {
        return retrofit.create(MarvelService.class);
    }

    @Provides
    @Singleton
    Repository provideRepository(MarvelService dataService, FirebaseAnalyticsManager analyticsManager) {
        return new Repository(dataService, analyticsManager);
    }



}