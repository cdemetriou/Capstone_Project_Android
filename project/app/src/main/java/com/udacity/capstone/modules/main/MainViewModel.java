package com.udacity.capstone.modules.main;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;

import com.udacity.capstone.CapstoneApplication;
import com.udacity.capstone.data.model.Item;
import com.udacity.capstone.data.model.ItemList;
import com.udacity.capstone.data.remote.Repository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by christosdemetriou on 04/05/2018.
 */

public class MainViewModel {

    @Inject
    Repository repository;
    private MutableLiveData<ItemList> characterList;
    private MutableLiveData<ItemList> comicsList;

    public static class Type {
        public final static int CHARS = 0;
        public final static int COMICS = 1;
    }

    int currentType = Type.CHARS;



    public MainViewModel(Context context) {
        CapstoneApplication.getApplicationComponent(context).inject(this);

        getChars();
        getComs();
    }

    public void getChars(){
        characterList = repository.getCharacters("Iron", 0, 10);
    }
    public void getComs(){
        comicsList = repository.getComics("Iron", 0, 10);
    }

    public void search(int type) {
        currentType = type;

        switch (type){
            case Type.CHARS:
                // show chars
                break;
            case Type.COMICS:
                // search
                break;
        }
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
