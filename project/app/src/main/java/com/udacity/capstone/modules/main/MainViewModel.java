package com.udacity.capstone.modules.main;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;

import com.udacity.capstone.CapstoneApplication;
import com.udacity.capstone.data.model.ItemList;
import com.udacity.capstone.data.remote.Repository;

import javax.inject.Inject;


@SuppressWarnings("WeakerAccess")
public class MainViewModel {

    @Inject
    Repository repository;
    
    private MutableLiveData<ItemList> characterList;
    private MutableLiveData<ItemList> comicsList;
    private MutableLiveData<ItemList> seriesList;
    private MutableLiveData<ItemList> eventsList;


    MainViewModel(Context context) {
        CapstoneApplication.getApplicationComponent(context).inject(this);

        getDefaultCharacters();
        getDefaultComics();
        getDefaultSeries();
        getDefaultEvents();
    }

    private void getDefaultCharacters(){
        characterList = repository.getDefaultCharacters(0);
    }
    private void getDefaultComics(){
        comicsList = repository.getDefaultComics(0);
    }
    private void getDefaultSeries(){
        seriesList = repository.getDefaultSeries(0);
    }
    private void getDefaultEvents(){
        eventsList = repository.getDefaultEvents(0);
    }

    void searchCharacters(String nameStartWith){
        characterList = repository.searchCharactersByName(nameStartWith, 0);
    }

    void searchComics(String titleStartsWith) {
        comicsList = repository.searchComicsByName(titleStartsWith,0);
    }

    void searchSeries(String titleStartsWith){
        seriesList = repository.searchSeriesByName(titleStartsWith, 0);
    }

    void searchEvents(String titleStartsWith) {
        eventsList = repository.searchEventsByName(titleStartsWith,0);
    }


    MutableLiveData<ItemList> getCharacterList() {
        if (characterList == null) {
            characterList = new MutableLiveData<>();
        }
        return characterList;
    }

    MutableLiveData<ItemList> getComicsList() {
        if (comicsList == null) {
            comicsList = new MutableLiveData<>();
        }
        return comicsList;
    }

    MutableLiveData<ItemList> getSeriesList() {
        if (seriesList == null) {
            seriesList = new MutableLiveData<>();
        }
        return seriesList;
    }

    MutableLiveData<ItemList> getEventsList() {
        if (eventsList == null) {
            eventsList = new MutableLiveData<>();
        }
        return eventsList;
    }

}
