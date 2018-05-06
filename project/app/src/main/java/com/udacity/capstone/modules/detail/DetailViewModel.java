package com.udacity.capstone.modules.detail;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;

import com.udacity.capstone.CapstoneApplication;
import com.udacity.capstone.data.model.Item;
import com.udacity.capstone.data.model.ItemList;
import com.udacity.capstone.data.remote.Repository;

import javax.inject.Inject;

import static com.udacity.capstone.data.model.ItemList.Type.isCharacter;
import static com.udacity.capstone.data.model.ItemList.Type.isComic;

/**
 * Created by christosdemetriou on 05/05/2018.
 */

public class DetailViewModel {

    @Inject
    Repository repository;

    private MutableLiveData<ItemList> list;



    public DetailViewModel(Context context, Integer id, int type) {
        CapstoneApplication.getApplicationComponent(context).inject(this);

        switch (type){
            case isCharacter:
                getComs(id);
                break;
            case isComic:
                getChars(id);
                break;
        }
    }


    public void getChars(Integer id){
        list = repository.getCharactersByComicId(id);
    }
    public void getComs(Integer id){
        list = repository.getComicsByCharacterId(id);
    }


    public MutableLiveData<ItemList> getList() {
        if (list == null) {
            list = new MutableLiveData<>();
        }
        return list;
    }
}

