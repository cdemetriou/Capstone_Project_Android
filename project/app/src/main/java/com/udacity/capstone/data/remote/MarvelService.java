package com.udacity.capstone.data.remote;

import android.support.annotation.Nullable;

import com.udacity.capstone.data.model.Response;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by christosdemetriou on 20/04/2018.
 */

public interface MarvelService {


    @GET("characters")
    Call<Response> searchCharactersByName(@Nullable @Query("nameStartsWith") String startWith, @QueryMap Map<String, String> queryMap,
                                          @Nullable @Query("offset") Integer offset, @Nullable @Query("limit") Integer limit);

    @GET("comics")
    Call<Response> searchComicsByTitle(@Nullable @Query("titleStartsWith") String titleStartsWith, @QueryMap Map<String, String> queryMap,
                                @Nullable @Query("offset") Integer offset, @Nullable @Query("limit") Integer limit);

    @GET("series")
    Call<Response> searchSeriesByTitle(@Nullable @Query("titleStartsWith") String titleStartsWith, @Query("apikey") String publicKey, @Query("hash") String md5Digest, @Query("ts") long timestamp,
                                @Nullable @Query("offset") Integer offset, @Nullable @Query("limit") Integer limit);

    @GET("stories")
    Call<Response> searchEventsByTitle(@Nullable @Query("titleStartsWith") String titleStartsWith, @Query("apikey") String publicKey, @Query("hash") String md5Digest, @Query("ts") long timestamp,
                                @Nullable @Query("offset") Integer offset, @Nullable @Query("limit") Integer limit);

    @GET("characters/{characterId}")
    Call<Response> getCharacterById(@Path("characterId") Integer characterId, @Query("apikey") String publicKey, @Query("hash") String md5Digest, @Query("ts") long timestamp);

    @GET("characters/{characterId}/comics")
    Call<Response> getCharacterComicsById(@Path("characterId") Integer characterId, @Query("apikey") String publicKey, @Query("hash") String md5Digest, @Query("ts") long timestamp);

    @GET("characters/{characterId}/series")
    Call<Response> getCharacterSeriesById(@Path("characterId") Integer characterId, @Query("apikey") String publicKey, @Query("hash") String md5Digest, @Query("ts") long timestamp);

    @GET("characters/{characterId}/events")
    Call<Response> getCharacterEventsById(@Path("characterId") Integer characterId, @Query("apikey") String publicKey, @Query("hash") String md5Digest, @Query("ts") long timestamp);

    @GET("comics/{comicId}")
    Call<Response> getComicById(@Path("comicId") Integer comicId, @Query("apikey") String publicKey, @Query("hash") String md5Digest, @Query("ts") long timestamp);

    @GET("series/{seriesId}")
    Call<Response> getSeriesById(@Path("seriesId") Integer comicId, @Query("apikey") String publicKey, @Query("hash") String md5Digest, @Query("ts") long timestamp);

    @GET("events/{eventId}")
    Call<Response> getEventById(@Path("eventId") Integer comicId, @Query("apikey") String publicKey, @Query("hash") String md5Digest, @Query("ts") long timestamp);

    @GET("comics/{comicId}/characters")
    Call<Response> getComicCharactersById(@Path("comicId") Integer comicId, @Query("apikey") String publicKey, @Query("hash") String md5Digest, @Query("ts") long timestamp);

    @GET("series/{seriesId}/characters")
    Call<Response> getSeriesCharactersById(@Path("seriesId") Integer comicId, @Query("apikey") String publicKey, @Query("hash") String md5Digest, @Query("ts") long timestamp);

    @GET("events/{eventId}/characters")
    Call<Response> getEventCharactersById(@Path("eventId") Integer comicId, @Query("apikey") String publicKey, @Query("hash") String md5Digest, @Query("ts") long timestamp);

}
