package com.example.moviedb.model.movie;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Trailers {

    @SerializedName("quicktime")
    @Expose
    private List<Object> quicktime;
    @SerializedName("youtube")
    @Expose
    private List<Youtube> youtube;

    public List<Object> getQuicktime() {
        return quicktime;
    }

    public void setQuicktime(List<Object> quicktime) {
        this.quicktime = quicktime;
    }

    public List<Youtube> getYoutube() {
        return youtube;
    }

    public void setYoutube(List<Youtube> youtube) {
        this.youtube = youtube;
    }

    public class Youtube {

        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("size")
        @Expose
        private String size;
        @SerializedName("source")
        @Expose
        private String source;
        @SerializedName("type")
        @Expose
        private String type;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
