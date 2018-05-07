package com.udacity.capstone.modules.main;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;

import com.udacity.capstone.CapstoneApplication;
import com.udacity.capstone.data.model.ItemList;
import com.udacity.capstone.data.remote.Repository;

import javax.inject.Inject;

/**
 * Created by christosdemetriou on 04/05/2018.
 */

public class MainViewModel {

    @Inject
    Repository repository;
    
    MutableLiveData<ItemList> characterList;
    MutableLiveData<ItemList> comicsList;

    //private MutableLiveData<ItemList> searchList;




    public MainViewModel(Context context) {
        CapstoneApplication.getApplicationComponent(context).inject(this);

        getDefaultCharacters();
        getDefaultComics();
    }

    public void getDefaultCharacters(){
        characterList = repository.getDefaultCharacters(0, 10);
    }
    public void getDefaultComics(){
        comicsList = repository.getDefaultComics(0, 10);
    }

    public void searchCharacters(String nameStartWith){
        characterList = repository.searchCharactersByName(nameStartWith, 0, 10);
    }

    public void searchComics(String titleStartsWith) {
        comicsList = repository.searchComicsByName(titleStartsWith,0,10);
    }


    public MutableLiveData<ItemList> getCharacterList() {
        if (characterList == null) {
            characterList = new MutableLiveData<>();
        }
        return characterList;
    }

    public MutableLiveData<ItemList> getComicsList() {
        if (comicsList == null) {
            comicsList = new MutableLiveData<>();
        }
        return comicsList;
    }

}
