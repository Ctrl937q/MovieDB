package com.example.moviedb.model.tv.details;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ImagesTV {

    @SerializedName("backdropTVs")
    @Expose
    private List<BackdropTV> backdropTVs = null;
    @SerializedName("posters")
    @Expose
    private List<PosterTV> posters = null;

    public List<BackdropTV> getBackdropTVs() {
        return backdropTVs;
    }

    public void setBackdropTVs(List<BackdropTV> backdropTVs) {
        this.backdropTVs = backdropTVs;
    }

    public List<PosterTV> getPosters() {
        return posters;
    }

    public void setPosters(List<PosterTV> posters) {
        this.posters = posters;
    }
}
