package com.udacity.capstone.data.remote;

import android.arch.lifecycle.MutableLiveData;

import com.udacity.capstone.data.model.Item;
import com.udacity.capstone.data.model.ItemList;
import com.udacity.capstone.data.model.Response;
import com.udacity.capstone.firebase.FirebaseAnalyticsManager;
import com.udacity.capstone.utils.Utils;

import java.util.List;

import javax.inject.Inject;



@SuppressWarnings("CanBeFinal")
public class Repository {

    private MarvelService dataService;
    private FirebaseAnalyticsManager analyticsManager;

    @Inject
    public Repository(MarvelService dataService, FirebaseAnalyticsManager analyticsManager){
        this.dataService = dataService;
        this.analyticsManager = analyticsManager;
    }

    // Get default lists

    public MutableLiveData<ItemList> getDefaultCharacters(int offset){
        final MutableLiveData<ItemList> data = new MutableLiveData<>();

        dataService.getDefaultCharacters("-modified", Utils.getQueryMap(), offset, 10).enqueue(new ErrorHandlingCallback<Response>() {

            @Override
            public void onSuccess(Response response) {
                List<Item> characters = response.getData().getResults();
                ItemList list = new ItemList(characters, ItemList.Type.isCharacter);

                data.setValue(list);
                analyticsManager.logDataRequestSuccessEvent("getDefaultCharacters");
            }

            @Override
            public void onFailed(Throwable throwable) {
                analyticsManager.logDataRequestFailureEvent("getDefaultCharacters", throwable.getMessage());
            }
        });
        return data;
    }

    public MutableLiveData<ItemList> getDefaultComics(int offset){
        final MutableLiveData<ItemList> data = new MutableLiveData<>();

        dataService.getDefaultComics("-modified", Utils.getQueryMap(), offset, 10).enqueue(new ErrorHandlingCallback<Response>() {

            @Override
            public void onSuccess(Response response) {
                List<Item> characters = response.getData().getResults();
                ItemList list = new ItemList(characters, ItemList.Type.isComic);

                data.setValue(list);
                analyticsManager.logDataRequestSuccessEvent("getDefaultComics");

            }

            @Override
            public void onFailed(Throwable throwable) {
                analyticsManager.logDataRequestFailureEvent("getDefaultComics", throwable.getMessage());
            }
        });
        return data;
    }

    public MutableLiveData<ItemList> getDefaultSeries(int offset){
        final MutableLiveData<ItemList> data = new MutableLiveData<>();

        dataService.getDefaultSeries("-modified", Utils.getQueryMap(), offset, 10).enqueue(new ErrorHandlingCallback<Response>() {

            @Override
            public void onSuccess(Response response) {
                List<Item> series = response.getData().getResults();
                ItemList list = new ItemList(series, ItemList.Type.isSeries);

                data.setValue(list);
                analyticsManager.logDataRequestSuccessEvent("getDefaultSeries");

            }

            @Override
            public void onFailed(Throwable throwable) {
                analyticsManager.logDataRequestFailureEvent("getDefaultSeries", throwable.getMessage());
            }
        });
        return data;
    }

    public MutableLiveData<ItemList> getDefaultEvents(int offset){
        final MutableLiveData<ItemList> data = new MutableLiveData<>();

        dataService.getDefaultEvents("-modified", Utils.getQueryMap(), offset, 10).enqueue(new ErrorHandlingCallback<Response>() {

            @Override
            public void onSuccess(Response response) {
                List<Item> events = response.getData().getResults();
                ItemList list = new ItemList(events, ItemList.Type.isEvent);

                data.setValue(list);
                analyticsManager.logDataRequestSuccessEvent("getDefaultEvents");

            }

            @Override
            public void onFailed(Throwable throwable) {
                analyticsManager.logDataRequestFailureEvent("getDefaultEvents", throwable.getMessage());
            }
        });
        return data;
    }

    // Search by name/title

    public MutableLiveData<ItemList> searchCharactersByName(String nameStartsWith, int offset){
        final MutableLiveData<ItemList> data = new MutableLiveData<>();

        dataService.searchCharactersByName(nameStartsWith, Utils.getQueryMap(), offset, 10).enqueue(new ErrorHandlingCallback<Response>() {

            @Override
            public void onSuccess(Response response) {
                List<Item> characters = response.getData().getResults();
                ItemList list = new ItemList(characters, ItemList.Type.isCharacter);

                data.setValue(list);
                analyticsManager.logDataRequestSuccessEvent("searchCharactersByName_" + nameStartsWith);
            }

            @Override
            public void onFailed(Throwable throwable) {
                analyticsManager.logDataRequestFailureEvent("searchCharactersByName_" + nameStartsWith, throwable.getMessage());
            }
        });
        return data;
    }

    public MutableLiveData<ItemList> searchComicsByName(String titleStartsWith, int offset){
        final MutableLiveData<ItemList> data = new MutableLiveData<>();

        dataService.searchComicsByName(titleStartsWith, Utils.getQueryMap(), offset, 10).enqueue(new ErrorHandlingCallback<Response>() {

            @Override
            public void onSuccess(Response response) {
                List<Item> characters = response.getData().getResults();
                ItemList list = new ItemList(characters, ItemList.Type.isComic);

                data.setValue(list);
                analyticsManager.logDataRequestSuccessEvent("searchComicsByName_" + titleStartsWith);
            }

            @Override
            public void onFailed(Throwable throwable) {
                analyticsManager.logDataRequestFailureEvent("searchComicsByName_" + titleStartsWith, throwable.getMessage());
            }
        });
        return data;
    }

    public MutableLiveData<ItemList> searchSeriesByName(String titleStartsWith, int offset){
        final MutableLiveData<ItemList> data = new MutableLiveData<>();

        dataService.searchSeriesByTitle(titleStartsWith, Utils.getQueryMap(), offset, 10).enqueue(new ErrorHandlingCallback<Response>() {

            @Override
            public void onSuccess(Response response) {
                List<Item> series = response.getData().getResults();
                ItemList list = new ItemList(series, ItemList.Type.isSeries);

                data.setValue(list);
                analyticsManager.logDataRequestSuccessEvent("searchSeriesByName_" + titleStartsWith);
            }

            @Override
            public void onFailed(Throwable throwable) {
                analyticsManager.logDataRequestFailureEvent("searchSeriesByName_" + titleStartsWith, throwable.getMessage());
            }
        });
        return data;
    }

    public MutableLiveData<ItemList> searchEventsByName(String titleStartsWith, int offset){
        final MutableLiveData<ItemList> data = new MutableLiveData<>();

        dataService.searchEventsByName(titleStartsWith, Utils.getQueryMap(), offset, 10).enqueue(new ErrorHandlingCallback<Response>() {

            @Override
            public void onSuccess(Response response) {
                List<Item> events = response.getData().getResults();
                ItemList list = new ItemList(events, ItemList.Type.isEvent);

                data.setValue(list);
                analyticsManager.logDataRequestSuccessEvent("searchEventsByName_" + titleStartsWith);
            }

            @Override
            public void onFailed(Throwable throwable) {
                analyticsManager.logDataRequestFailureEvent("searchEventsByName_" + titleStartsWith, throwable.getMessage());
            }
        });
        return data;
    }



    public MutableLiveData<ItemList> getComicsByCharacterId(int characterId){
        final MutableLiveData<ItemList> data = new MutableLiveData<>();

        dataService.getComicsByCharacterId(characterId, Utils.getQueryMap()).enqueue(new ErrorHandlingCallback<Response>() {

            @Override
            public void onSuccess(Response response) {
                List<Item> comics = response.getData().getResults();
                ItemList list = new ItemList(comics, ItemList.Type.isComic);

                data.setValue(list);
                analyticsManager.logDataRequestSuccessEvent("getComicsByCharacterId_" + characterId);
            }

            @Override
            public void onFailed(Throwable throwable) {
                analyticsManager.logDataRequestFailureEvent("getComicsByCharacterId_" + characterId, throwable.getMessage());
            }
        });
        return data;
    }

    public MutableLiveData<ItemList> getSeriesByCharacterId(int characterId){
        final MutableLiveData<ItemList> data = new MutableLiveData<>();

        dataService.getSeriesByCharacterId(characterId, Utils.getQueryMap()).enqueue(new ErrorHandlingCallback<Response>() {

            @Override
            public void onSuccess(Response response) {
                List<Item> series = response.getData().getResults();
                ItemList list = new ItemList(series, ItemList.Type.isSeries);

                data.setValue(list);
                analyticsManager.logDataRequestSuccessEvent("getSeriesByCharacterId_" + characterId);
            }

            @Override
            public void onFailed(Throwable throwable) {
                analyticsManager.logDataRequestFailureEvent("getSeriesByCharacterId_" + characterId, throwable.getMessage());
            }
        });
        return data;
    }

    public MutableLiveData<ItemList> getEventsByCharacterId(int characterId){
        final MutableLiveData<ItemList> data = new MutableLiveData<>();

        dataService.getEventsByCharacterId(characterId, Utils.getQueryMap()).enqueue(new ErrorHandlingCallback<Response>() {

            @Override
            public void onSuccess(Response response) {
                List<Item> events = response.getData().getResults();
                ItemList list = new ItemList(events, ItemList.Type.isEvent);

                data.setValue(list);
                analyticsManager.logDataRequestSuccessEvent("getEventsByCharacterId_" + characterId);
            }

            @Override
            public void onFailed(Throwable throwable) {
                analyticsManager.logDataRequestFailureEvent("getEventsByCharacterId_" + characterId, throwable.getMessage());
            }
        });
        return data;
    }



    public MutableLiveData<ItemList> getCharactersByComicId(int comicId){
        final MutableLiveData<ItemList> data = new MutableLiveData<>();

        dataService.getCharactersByComicId(comicId, Utils.getQueryMap()).enqueue(new ErrorHandlingCallback<Response>() {

            @Override
            public void onSuccess(Response response) {
                List<Item> characters = response.getData().getResults();
                ItemList list = new ItemList(characters, ItemList.Type.isCharacter);

                data.setValue(list);
                analyticsManager.logDataRequestSuccessEvent("getCharactersByComicId_" + comicId);
            }

            @Override
            public void onFailed(Throwable throwable) {
                analyticsManager.logDataRequestFailureEvent("getCharactersByComicId_" + comicId, throwable.getMessage());
            }
        });
        return data;
    }

    public MutableLiveData<ItemList> getSeriesByComicId(int comicId){
        final MutableLiveData<ItemList> data = new MutableLiveData<>();

        dataService.getSeriesByComicId(comicId, Utils.getQueryMap()).enqueue(new ErrorHandlingCallback<Response>() {

            @Override
            public void onSuccess(Response response) {
                List<Item> series = response.getData().getResults();
                ItemList list = new ItemList(series, ItemList.Type.isSeries);

                data.setValue(list);
                analyticsManager.logDataRequestSuccessEvent("getSeriesByComicId_" + comicId);
            }

            @Override
            public void onFailed(Throwable throwable) {
                analyticsManager.logDataRequestFailureEvent("getSeriesByComicId_" + comicId, throwable.getMessage());
            }
        });
        return data;
    }

    public MutableLiveData<ItemList> getEventsByComicId(int comicId){
        final MutableLiveData<ItemList> data = new MutableLiveData<>();

        dataService.getEventsByComicId(comicId, Utils.getQueryMap()).enqueue(new ErrorHandlingCallback<Response>() {

            @Override
            public void onSuccess(Response response) {
                List<Item> events = response.getData().getResults();
                ItemList list = new ItemList(events, ItemList.Type.isEvent);

                data.setValue(list);
                analyticsManager.logDataRequestSuccessEvent("getEventsByComicId_" + comicId);
            }

            @Override
            public void onFailed(Throwable throwable) {
                analyticsManager.logDataRequestFailureEvent("getEventsByComicId_" + comicId, throwable.getMessage());
            }
        });
        return data;
    }



    public MutableLiveData<ItemList> getCharactersBySeriesId(int seriesId){
        final MutableLiveData<ItemList> data = new MutableLiveData<>();

        dataService.getCharactersBySeriesId(seriesId, Utils.getQueryMap()).enqueue(new ErrorHandlingCallback<Response>() {

            @Override
            public void onSuccess(Response response) {
                List<Item> characters = response.getData().getResults();
                ItemList list = new ItemList(characters, ItemList.Type.isCharacter);

                data.setValue(list);
                analyticsManager.logDataRequestSuccessEvent("getCharactersBySeriesId_" + seriesId);
            }

            @Override
            public void onFailed(Throwable throwable) {
                analyticsManager.logDataRequestFailureEvent("getCharactersBySeriesId_" + seriesId, throwable.getMessage());
            }
        });
        return data;
    }

    public MutableLiveData<ItemList> getComicsBySeriesId(int seriesId){
        final MutableLiveData<ItemList> data = new MutableLiveData<>();

        dataService.getComicsBySeriesId(seriesId, Utils.getQueryMap()).enqueue(new ErrorHandlingCallback<Response>() {

            @Override
            public void onSuccess(Response response) {
                List<Item> comics = response.getData().getResults();
                ItemList list = new ItemList(comics, ItemList.Type.isComic);

                data.setValue(list);
                analyticsManager.logDataRequestSuccessEvent("getComicsBySeriesId_" + seriesId);
            }

            @Override
            public void onFailed(Throwable throwable) {
                analyticsManager.logDataRequestFailureEvent("getComicsBySeriesId_" + seriesId, throwable.getMessage());
            }
        });
        return data;
    }

    public MutableLiveData<ItemList> getEventsBySeriesId(int seriesId){
        final MutableLiveData<ItemList> data = new MutableLiveData<>();

        dataService.getEventsBySeriesId(seriesId, Utils.getQueryMap()).enqueue(new ErrorHandlingCallback<Response>() {

            @Override
            public void onSuccess(Response response) {
                List<Item> events = response.getData().getResults();
                ItemList list = new ItemList(events, ItemList.Type.isEvent);

                data.setValue(list);
                analyticsManager.logDataRequestSuccessEvent("getEventsBySeriesId_" + seriesId);
            }

            @Override
            public void onFailed(Throwable throwable) {
                analyticsManager.logDataRequestFailureEvent("getEventsBySeriesId_" + seriesId, throwable.getMessage());
            }
        });
        return data;
    }



    public MutableLiveData<ItemList> getCharactersByEventId(int eventId){
        final MutableLiveData<ItemList> data = new MutableLiveData<>();

        dataService.getCharactersByEventId(eventId, Utils.getQueryMap()).enqueue(new ErrorHandlingCallback<Response>() {

            @Override
            public void onSuccess(Response response) {
                List<Item> characters = response.getData().getResults();
                ItemList list = new ItemList(characters, ItemList.Type.isCharacter);

                data.setValue(list);
                analyticsManager.logDataRequestSuccessEvent("getCharactersByEventId_" + eventId);
            }

            @Override
            public void onFailed(Throwable throwable) {
                analyticsManager.logDataRequestFailureEvent("getCharactersByEventId_" + eventId, throwable.getMessage());
            }
        });
        return data;
    }

    public MutableLiveData<ItemList> getComicsByEventId(int eventId){
        final MutableLiveData<ItemList> data = new MutableLiveData<>();

        dataService.getComicsByEventId(eventId, Utils.getQueryMap()).enqueue(new ErrorHandlingCallback<Response>() {

            @Override
            public void onSuccess(Response response) {
                List<Item> comics = response.getData().getResults();
                ItemList list = new ItemList(comics, ItemList.Type.isComic);

                data.setValue(list);
                analyticsManager.logDataRequestSuccessEvent("getComicsByEventId_" + eventId);
            }

            @Override
            public void onFailed(Throwable throwable) {
                analyticsManager.logDataRequestFailureEvent("getComicsByEventId_" + eventId, throwable.getMessage());
            }
        });
        return data;
    }

    public MutableLiveData<ItemList> getSeriesByEventId(int eventId){
        final MutableLiveData<ItemList> data = new MutableLiveData<>();

        dataService.getSeriesByEventId(eventId, Utils.getQueryMap()).enqueue(new ErrorHandlingCallback<Response>() {

            @Override
            public void onSuccess(Response response) {
                List<Item> series = response.getData().getResults();
                ItemList list = new ItemList(series, ItemList.Type.isSeries);

                data.setValue(list);
                analyticsManager.logDataRequestSuccessEvent("getSeriesByEventId_" + eventId);
            }

            @Override
            public void onFailed(Throwable throwable) {
                analyticsManager.logDataRequestFailureEvent("getSeriesByEventId_" + eventId, throwable.getMessage());
            }
        });
        return data;
    }

}
