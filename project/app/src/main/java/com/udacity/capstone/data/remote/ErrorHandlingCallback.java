package com.udacity.capstone.data.remote;

import android.support.annotation.NonNull;

import java.net.HttpURLConnection;

import javax.net.ssl.HttpsURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



abstract class ErrorHandlingCallback<T> implements Callback<T> {

    public abstract void onSuccess(T response);
    public abstract void onFailed(Throwable throwable);

    @Override
    public final void onResponse(@NonNull Call<T> call, @NonNull Response<T> response) {
        switch (response.code()) {
            case HttpsURLConnection.HTTP_OK:
            case HttpsURLConnection.HTTP_CREATED:
            case HttpsURLConnection.HTTP_ACCEPTED:
            case HttpsURLConnection.HTTP_NOT_AUTHORITATIVE:
                if (response.body() != null) onSuccess(response.body());
                break;
            case HttpURLConnection.HTTP_CONFLICT:
                onFailed(new Throwable("API " + response.code() + " " + response.errorBody()));
                break;

            default:
                onFailed(new Throwable("Default " + response.code() + " " + response.message()));
        }
    }

    @Override
    public final void onFailure(@NonNull Call<T> call, @NonNull Throwable t) {
        onFailed(t);
    }
}