package com.udacity.capstone.data.remote;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;

import com.udacity.capstone.data.model.Item;
import com.udacity.capstone.data.model.ItemList;
import com.udacity.capstone.data.model.Response;
import com.udacity.capstone.utils.Utils;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by christosdemetriou on 20/04/2018.
 */

public class Repository {


    private final static String TAG = "Repository";

    private Context mContext;
    private MarvelService dataService;

    @Inject
    public Repository(Context context, MarvelService dataService){
        this.mContext = context;
        this.dataService = dataService;
    }

    public MutableLiveData<ItemList> getDefaultCharacters(int offset, int limit){
        final MutableLiveData<ItemList> data = new MutableLiveData<>();

        dataService.getDefaultCharacters("-modified", Utils.getQueryMap(), offset, limit).enqueue(new ErrorHandlingCallback<Response>() {

            @Override
            public void onSuccess(Response response) {
                List<Item> characters = response.getData().getResults();
                ItemList list = new ItemList(characters, ItemList.Type.isCharacter);

                data.setValue(list);
            }

            @Override
            public void onFailed(Throwable throwable) {

            }
        });
        return data;
    }

    public MutableLiveData<ItemList> getDefaultComics(int offset, int limit){
        final MutableLiveData<ItemList> data = new MutableLiveData<>();

        dataService.getDefaultComics("-modified", Utils.getQueryMap(), offset, limit).enqueue(new ErrorHandlingCallback<Response>() {

            @Override
            public void onSuccess(Response response) {
                List<Item> characters = response.getData().getResults();
                ItemList list = new ItemList(characters, ItemList.Type.isComic);

                data.setValue(list);
            }

            @Override
            public void onFailed(Throwable throwable) {

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
            }

            @Override
            public void onFailed(Throwable throwable) {

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
            }

            @Override
            public void onFailed(Throwable throwable) {

            }
        });
        return data;
    }

    public MutableLiveData<ItemList> searchCharactersByName(String nameStartsWith, int offset, int limit){
        final MutableLiveData<ItemList> data = new MutableLiveData<>();

        dataService.searchCharactersByName(nameStartsWith, Utils.getQueryMap(), offset, limit).enqueue(new ErrorHandlingCallback<Response>() {

            @Override
            public void onSuccess(Response response) {
                List<Item> characters = response.getData().getResults();
                ItemList list = new ItemList(characters, ItemList.Type.isCharacter);

                data.setValue(list);
            }

            @Override
            public void onFailed(Throwable throwable) {

            }
        });
        return data;
    }

    public MutableLiveData<ItemList> searchComicsByName(String titleStartsWith, int offset, int limit){
        final MutableLiveData<ItemList> data = new MutableLiveData<>();

        dataService.searchComicsByName(titleStartsWith, Utils.getQueryMap(), offset, limit).enqueue(new ErrorHandlingCallback<Response>() {

            @Override
            public void onSuccess(Response response) {
                List<Item> characters = response.getData().getResults();
                ItemList list = new ItemList(characters, ItemList.Type.isComic);

                data.setValue(list);
            }

            @Override
            public void onFailed(Throwable throwable) {

            }
        });
        return data;
    }


}
