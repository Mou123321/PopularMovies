package com.mou.popularmovies.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MovieModel {
    private String title;
    @SerializedName("poster_path")
    @Expose
    private String imageUrl;
    private String overview;
    @SerializedName("release_date")
    @Expose
    private String releaseDate;

    public MovieModel(String title, String imageUrl, String overview, String releaseDate) {

        this.title = title;
        this.imageUrl = imageUrl;
        this.overview = overview;
        this.releaseDate = releaseDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }
}
