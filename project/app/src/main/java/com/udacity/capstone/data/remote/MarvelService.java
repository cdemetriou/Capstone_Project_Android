package com.udacity.capstone.data.remote;

import android.support.annotation.Nullable;

import com.udacity.capstone.data.model.Response;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;



public interface MarvelService {

    // Default lists

    @GET("characters")
    Call<Response> getDefaultCharacters(@SuppressWarnings("SameParameterValue") @Nullable @Query("orderBy") String modified, @QueryMap Map<String, String> queryMap,
                                        @Nullable @Query("offset") Integer offset, @SuppressWarnings("SameParameterValue") @Nullable @Query("limit") Integer limit);
    @GET("comics")
    Call<Response> getDefaultComics(@SuppressWarnings("SameParameterValue") @Nullable @Query("orderBy") String modified, @QueryMap Map<String, String> queryMap,
                                    @Nullable @Query("offset") Integer offset, @SuppressWarnings("SameParameterValue") @Nullable @Query("limit") Integer limit);
    @GET("series")
    Call<Response> getDefaultSeries(@SuppressWarnings("SameParameterValue") @Nullable @Query("orderBy") String modified, @QueryMap Map<String, String> queryMap,
                                    @Nullable @Query("offset") Integer offset, @SuppressWarnings("SameParameterValue") @Nullable @Query("limit") Integer limit);
    @GET("events")
    Call<Response> getDefaultEvents(@SuppressWarnings("SameParameterValue") @Nullable @Query("orderBy") String modified, @QueryMap Map<String, String> queryMap,
                                    @Nullable @Query("offset") Integer offset, @SuppressWarnings("SameParameterValue") @Nullable @Query("limit") Integer limit);


    // Search by name/title

    @GET("characters")
    Call<Response> searchCharactersByName(@Nullable @Query("nameStartsWith") String nameStartsWith, @QueryMap Map<String, String> queryMap,
                                          @Nullable @Query("offset") Integer offset, @SuppressWarnings("SameParameterValue") @Nullable @Query("limit") Integer limit);
    @GET("comics")
    Call<Response> searchComicsByName(@Nullable @Query("titleStartsWith") String titleStartsWith, @QueryMap Map<String, String> queryMap,
                                      @Nullable @Query("offset") Integer offset, @SuppressWarnings("SameParameterValue") @Nullable @Query("limit") Integer limit);
    @GET("series")
    Call<Response> searchSeriesByTitle(@Nullable @Query("titleStartsWith") String titleStartsWith, @QueryMap Map<String, String> queryMap,
                                       @Nullable @Query("offset") Integer offset, @SuppressWarnings("SameParameterValue") @Nullable @Query("limit") Integer limit);
    @GET("events")
    Call<Response> searchEventsByName(@Nullable @Query("nameStartsWith") String nameStartsWith, @QueryMap Map<String, String> queryMap,
                                      @Nullable @Query("offset") Integer offset, @SuppressWarnings("SameParameterValue") @Nullable @Query("limit") Integer limit);



    // Get list by characterid

    @GET("characters/{characterId}/comics")
    Call<Response> getComicsByCharacterId(@Path("characterId") Integer characterId, @QueryMap Map<String, String> queryMap);

    @GET("characters/{characterId}/series")
    Call<Response> getSeriesByCharacterId(@Path("characterId") Integer characterId, @QueryMap Map<String, String> queryMap);

    @GET("characters/{characterId}/events")
    Call<Response> getEventsByCharacterId(@Path("characterId") Integer characterId, @QueryMap Map<String, String> queryMap);


    // Get list by comicid

    @GET("comics/{comicId}/characters")
    Call<Response> getCharactersByComicId(@Path("comicId") Integer comicId, @QueryMap Map<String, String> queryMap);

    @GET("comics/{comicId}/series")
    Call<Response> getSeriesByComicId(@Path("comicId") Integer comicId, @QueryMap Map<String, String> queryMap);

    @GET("comics/{comicId}/events")
    Call<Response> getEventsByComicId(@Path("comicId") Integer comicId, @QueryMap Map<String, String> queryMap);


    // Get list by seriesid

    @GET("series/{seriesId}/characters")
    Call<Response> getCharactersBySeriesId(@Path("seriesId") Integer seriesId, @QueryMap Map<String, String> queryMap);

    @GET("series/{seriesId}/comics")
    Call<Response> getComicsBySeriesId(@Path("seriesId") Integer comicId, @QueryMap Map<String, String> queryMap);

    @GET("series/{seriesId}/events")
    Call<Response> getEventsBySeriesId(@Path("seriesId") Integer comicId, @QueryMap Map<String, String> queryMap);


    // Get list by eventid

    @GET("events/{eventId}/characters")
    Call<Response> getCharactersByEventId(@Path("eventId") Integer seriesId, @QueryMap Map<String, String> queryMap);

    @GET("events/{eventId}/comics")
    Call<Response> getComicsByEventId(@Path("eventId") Integer comicId, @QueryMap Map<String, String> queryMap);

    @GET("events/{eventId}/series")
    Call<Response> getSeriesByEventId(@Path("eventId") Integer comicId, @QueryMap Map<String, String> queryMap);


}
