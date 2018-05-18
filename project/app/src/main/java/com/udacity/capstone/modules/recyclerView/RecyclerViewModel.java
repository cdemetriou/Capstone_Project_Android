package com.udacity.capstone.modules.recyclerView;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;

import com.udacity.capstone.CapstoneApplication;
import com.udacity.capstone.data.model.ItemList;
import com.udacity.capstone.data.remote.Repository;

import javax.inject.Inject;



@SuppressWarnings({"WeakerAccess", "CanBeFinal"})
public class RecyclerViewModel {

    @Inject
    Repository repository;
    private int offset = 0;
    private String searchTerm;

    private MutableLiveData<ItemList> list;
    private ItemList givenList;


    public RecyclerViewModel(Context context, ItemList list, String searchTerm) {
        CapstoneApplication.getApplicationComponent(context).inject(this);

        givenList = list;
        if (givenList != null){
            this.searchTerm = searchTerm;
        }

    }

    public void getMore(){
        if (givenList == null || givenList.getList() == null) return;
        offset += 10;
        if (searchTerm != null){
            switch (givenList.getType()){
                case ItemList.Type.isCharacter:
                    list = repository.searchCharactersByName(searchTerm, offset);
                    break;
                case ItemList.Type.isComic:
                    list = repository.searchComicsByName(searchTerm, offset);
                    break;
                case ItemList.Type.isSeries:
                    list = repository.searchSeriesByName(searchTerm, offset);
                    break;
                case ItemList.Type.isEvent:
                    list = repository.searchEventsByName(searchTerm, offset);
                    break;
            }
        }
        else {
            switch (givenList.getType()){
                case ItemList.Type.isCharacter:
                    list = repository.getDefaultCharacters(offset);
                    break;
                case ItemList.Type.isComic:
                    list = repository.getDefaultComics(offset);
                    break;
                case ItemList.Type.isSeries:
                    list = repository.getDefaultSeries(offset);
                    break;
                case ItemList.Type.isEvent:
                    list = repository.getDefaultEvents(offset);
                    break;
            }
        }
    }

    public MutableLiveData<ItemList> getList() {
        if (list == null) {
            list = new MutableLiveData<>();
        }
        return list;
    }
}
