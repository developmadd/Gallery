package com.madd.madd.gallery.UserGallery.Gallery;

public class Album {

    private String name;
    private String firstImagePath;

    public Album(String name, String firstImagePath) {
        this.name = name;
        this.firstImagePath = firstImagePath;
    }

    public String getName() {
        return name;
    }

    public String getFirstImagePath() {
        return firstImagePath;
    }

}


