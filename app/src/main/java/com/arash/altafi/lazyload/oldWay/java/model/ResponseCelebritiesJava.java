package com.arash.altafi.lazyload.oldWay.java.model;

import com.google.gson.annotations.SerializedName;

public class ResponseCelebritiesJava {

    @SerializedName("image")
    private String image;

    @SerializedName("name")
    private String name;

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

}