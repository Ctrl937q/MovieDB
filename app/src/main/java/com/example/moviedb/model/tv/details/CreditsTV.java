package com.example.moviedb.model.tv.details;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CreditsTV {

    @SerializedName("cast")
    @Expose
    private List<CastTV> castTV;
    @SerializedName("crewTV")
    @Expose
    private List<CrewTV> crewTV = null;

    public List<CastTV> getCastTV() {
        return castTV;
    }

    public void setCastTV(List<CastTV> castTV) {
        this.castTV = castTV;
    }

    public List<CrewTV> getCrewTV() {
        return crewTV;
    }

    public void setCrewTV(List<CrewTV> crewTV) {
        this.crewTV = crewTV;
    }
}
