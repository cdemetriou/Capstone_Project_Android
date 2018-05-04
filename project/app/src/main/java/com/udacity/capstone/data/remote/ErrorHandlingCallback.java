package com.udacity.capstone.data.remote;

import java.net.HttpURLConnection;

import javax.net.ssl.HttpsURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by christosdemetriou on 20/04/2018.
 */

public abstract class ErrorHandlingCallback<T> implements Callback<T> {

    public abstract void onSuccess(T response);
    public abstract void onFailed(Throwable throwable);

    @Override
    public final void onResponse(Call<T> call, Response<T> response) {
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
    public final void onFailure(Call<T> call, Throwable t) {
        onFailed(t);
    }
}