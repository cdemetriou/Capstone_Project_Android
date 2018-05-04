package com.udacity.capstone.data.model;

import org.parceler.Parcel;

import java.util.List;

/**
 * Created by christosdemetriou on 04/05/2018.
 */

@Parcel
public class ItemList {

    public static class Type {
        public final static int isCharacter = 0;
        public final static int isComic = 1;
        public final static int isSeries = 2;
        public final static int isStory = 3;
    }

    List<Item> mylist;

    public boolean isCharacter = false;
    public boolean isComic = false;
    public boolean isSeries = false;
    public boolean isStory = false;

    public List<Item> getList() {
        return mylist;
    }

    public void setList(List<Item> list) {
        this.mylist = list;
    }

    public ItemList() {}

    public ItemList(List<Item> list, int type){
        mylist = list;
        switch (type){
            case Type.isCharacter:
                isCharacter = true;
                break;
            case Type.isComic:
                isComic = true;
                break;
            case Type.isSeries:
                isSeries = true;
                break;
            case Type.isStory:
                isStory = true;
                break;
        }
    }
}
