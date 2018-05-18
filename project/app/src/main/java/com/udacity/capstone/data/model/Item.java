package com.udacity.capstone.data.model;


import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;



@SuppressWarnings({"WeakerAccess", "unused"})
@Parcel
public class Item {


    Integer id;

    String name;

    String title;

    String description;

    String variantDescription;

    String modified;

    Thumbnail thumbnail;

    String resourceURI;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public String getVariantDescription() {
        return variantDescription;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public Thumbnail getThumbnail() {
        return thumbnail;
    }


    @SuppressWarnings("unused")
    @Parcel
    public static class Thumbnail {


        String path;

        String extension;


        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getExtension() {
            return extension;
        }

    }




    @Override
    public String toString() {
        return "Item{" +
                " id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", modified='" + modified + '\'' +
                ", thumbnail=" + thumbnail +
                ", resourceURI='" + resourceURI + '\'' +
                '}';
    }


    public static List<Item> unique(List<Item> list) {

        Set set = new TreeSet((o1, o2) -> {
            Item r1 = (Item) o1;
            Item r2 = (Item) o2;
            if (r1 != null && r2 != null)
                if (r1.id != null && r2.id != null)
                    if (r1.id.equals(r2.id))
                        return 0;
            return 1;
        });
        set.addAll(list);

        return new ArrayList<>(set);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Item item = (Item) o;

        return id.equals(item.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

}

