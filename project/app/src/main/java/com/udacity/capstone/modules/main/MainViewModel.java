package com.udacity.capstone.modules.main;

import com.udacity.capstone.data.model.Item;

import java.util.List;

/**
 * Created by christosdemetriou on 04/05/2018.
 */

public class MainViewModel {

    public static class Type {
        public final static int CHARS = 0;
        public final static int COMICS = 1;
    }

    int currentType = Type.CHARS;
    public List<Item> items;

    public interface MainView {
        public void displayItems();
    }

    public MainViewModel(MainView mainView) {

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
}
