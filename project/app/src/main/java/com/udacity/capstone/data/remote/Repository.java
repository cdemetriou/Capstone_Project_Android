package com.udacity.capstone.data.remote;

import android.content.Context;

import com.udacity.capstone.data.Constants;
import com.udacity.capstone.data.model.Item;
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

    public void getCharacters(String startsWith, int offset, int limit){
        dataService.searchCharactersByName(startsWith, Utils.getQueryMap(), offset, limit).enqueue(new ErrorHandlingCallback<Response>() {

            @Override
            public void onSuccess(Response response) {
                List<Item> characters = response.getData().getResults();
            }

            @Override
            public void onFailed(Throwable throwable) {

            }
        });
    }


}
