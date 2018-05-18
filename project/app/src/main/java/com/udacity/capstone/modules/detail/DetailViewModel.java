package com.udacity.capstone.modules.detail;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;

import com.udacity.capstone.CapstoneApplication;
import com.udacity.capstone.data.model.ItemList;
import com.udacity.capstone.data.remote.Repository;

import javax.inject.Inject;

import static com.udacity.capstone.data.model.ItemList.Type.isCharacter;
import static com.udacity.capstone.data.model.ItemList.Type.isComic;
import static com.udacity.capstone.data.model.ItemList.Type.isEvent;
import static com.udacity.capstone.data.model.ItemList.Type.isSeries;



@SuppressWarnings({"WeakerAccess", "CanBeFinal"})
public class DetailViewModel {

    @Inject
    Repository repository;

    private int type;
    private MutableLiveData<ItemList> firstList;
    private MutableLiveData<ItemList> secondList;
    private MutableLiveData<ItemList> thirdList;



    DetailViewModel(Context context, Integer id, int t) {
        CapstoneApplication.getApplicationComponent(context).inject(this);
        type = t;
        getAll(id);

    }

    private void getAll(Integer id) {
        switch (type){
            case isCharacter:
                firstList = repository.getComicsByCharacterId(id);
                secondList = repository.getSeriesByCharacterId(id);
                thirdList = repository.getEventsByCharacterId(id);
                break;
            case isComic:
                firstList = repository.getCharactersByComicId(id);
                secondList = repository.getSeriesByComicId(id);
                thirdList = repository.getEventsByComicId(id);
                break;
            case isSeries:
                firstList = repository.getCharactersBySeriesId(id);
                secondList = repository.getComicsBySeriesId(id);
                thirdList = repository.getEventsBySeriesId(id);
                break;
            case isEvent:
                firstList = repository.getCharactersByEventId(id);
                secondList = repository.getComicsByEventId(id);
                thirdList = repository.getSeriesByEventId(id);
                break;
        }
    }


    MutableLiveData<ItemList> getFirstList() {
        if (firstList == null) {
            firstList = new MutableLiveData<>();
        }
        return firstList;
    }

    MutableLiveData<ItemList> getSecondList() {
        if (secondList == null) {
            secondList = new MutableLiveData<>();
        }
        return secondList;
    }

    MutableLiveData<ItemList> getThirdList() {
        if (thirdList == null) {
            thirdList = new MutableLiveData<>();
        }
        return thirdList;
    }
}

