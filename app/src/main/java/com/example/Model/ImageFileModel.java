package com.example.Model;

public class ImageFileModel {
    String imageurl;
     String name;
     String id  ;

    public ImageFileModel() {
    }

    public ImageFileModel(String imageurl, String name,String id ) {
        this.imageurl = imageurl;
        this.name = name;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
