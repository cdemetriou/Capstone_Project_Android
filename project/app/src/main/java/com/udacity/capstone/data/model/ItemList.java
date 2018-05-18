package com.udacity.capstone.data.model;

import org.parceler.Parcel;

import java.util.List;



@SuppressWarnings("WeakerAccess")
@Parcel
public class ItemList {


    public static class Type {

        public final static int isCharacter = 0;
        public final static int isComic = 1;
        public final static int isSeries = 2;
        public final static int isEvent = 3;

    }

    List<Item> mylist;

    int type;


    public ItemList() {}

    public ItemList(List<Item> list, int t){

        mylist = list;

        switch (t){
            case Type.isCharacter:
                type = Type.isCharacter;
                break;
            case Type.isComic:
                type = Type.isComic;
                break;
            case Type.isSeries:
                type = Type.isSeries;
                break;
            case Type.isEvent:
                type = Type.isEvent;
                break;
        }
    }

    public List<Item> getList() {
        return mylist;
    }

    public void setList(List<Item> list) {
        this.mylist = list;
    }

    public int getType() {
        return type;
    }

    public void setType(@SuppressWarnings("SameParameterValue") int type) {
        this.type = type;
    }

}
