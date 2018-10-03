package com.mou.popularmovies.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListVideoModel {
    private int id;
    @SerializedName("results")
    @Expose
    private List<VideoModel> videoList;

    public ListVideoModel(int id, List<VideoModel> videoList) {
        this.id = id;
        this.videoList = videoList;
    }

    public int getId() {
        return id;
    }

    private void setId(int id) {
        this.id = id;
    }

    public List<VideoModel> getVideoList() {
        return videoList;
    }

    private void setVideoList(List<VideoModel> videoList) {
        this.videoList = videoList;
    }
}
