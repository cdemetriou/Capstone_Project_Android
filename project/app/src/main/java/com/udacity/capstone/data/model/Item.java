package com.udacity.capstone.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.List;

/**
 * Created by christosdemetriou on 20/04/2018.
 */

@Parcel
public class Item {

    @SerializedName("id")
    @Expose
    Integer id;
    @SerializedName("name")
    @Expose
    String name;
    @SerializedName("description")
    @Expose
    String description;
    @SerializedName("modified")
    @Expose
    String modified;
    @SerializedName("thumbnail")
    @Expose
    Thumbnail thumbnail;
    @SerializedName("resourceURI")
    @Expose
    String resourceURI;
    @SerializedName("urls")
    @Expose
    List<Url> urls = null;

    public boolean isCharacter = false;
    public boolean isComic = false;
    public boolean isSeries = false;
    public boolean isStory = false;


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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public void setThumbnail(Thumbnail thumbnail) {
        this.thumbnail = thumbnail;
    }


    @Parcel
    static class Thumbnail {

        @SerializedName("path")
        @Expose
        String path;
        @SerializedName("extension")
        @Expose
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

        public void setExtension(String extension) {
            this.extension = extension;
        }

    }

    public String getResourceURI() {
        return resourceURI;
    }

    public void setResourceURI(String resourceURI) {
        this.resourceURI = resourceURI;
    }


    public List<Url> getUrls() {
        return urls;
    }

    public void setUrls(List<Url> urls) {
        this.urls = urls;
    }



    @Parcel
    static class Url {

        @SerializedName("type")
        @Expose
        String type;
        @SerializedName("url")
        @Expose
        String url;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
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
                ", urls=" + urls +
                '}';
    }
}

