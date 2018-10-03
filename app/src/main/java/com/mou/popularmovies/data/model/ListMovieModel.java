package com.mou.popularmovies.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListMovieModel {

    @SerializedName("results")
    @Expose
    private List<MovieModel> movieList = null;

    public ListMovieModel(List<MovieModel> movieList) {
        this.movieList = movieList;
    }

    public List<MovieModel> getMovieList() {
        return movieList;
    }

    public void setMovieList(List<MovieModel> movieList) {
        this.movieList = movieList;
    }
}
