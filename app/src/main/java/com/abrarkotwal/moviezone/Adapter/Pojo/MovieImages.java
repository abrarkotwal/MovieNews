package com.abrarkotwal.moviezone.Adapter.Pojo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieImages {

    @SerializedName("file_path")
    private String imagePath;

    @SerializedName("backdrops")
    private List<MovieImages> results;

    public MovieImages(String imagePath, List<MovieImages> results) {
        this.imagePath = imagePath;
        this.results = results;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public List<MovieImages> getResults() {
        return results;
    }

    public void setResults(List<MovieImages> results) {
        this.results = results;
    }
}
