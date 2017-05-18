package com.example.moviedb.model.movie;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Images {

    @SerializedName("backdrops")
    @Expose
    private List<Backdrop> backdrops;
    @SerializedName("posters")
    @Expose
    private List<Poster> posters;

    public List<Backdrop> getBackdrops() {
        return backdrops;
    }

    public void setBackdrops(List<Backdrop> backdrops) {
        this.backdrops = backdrops;
    }

    public List<Poster> getPosters() {
        return posters;
    }

    public void setPosters(List<Poster> posters) {
        this.posters = posters;
    }

}
