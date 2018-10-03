package com.mou.popularmovies.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListReviewModel {

    private int id;

    @SerializedName("results")
    @Expose
    private List<ReviewModel> reviewsList;

    public ListReviewModel(int id, List<ReviewModel> reviewsList) {
        this.id = id;
        this.reviewsList = reviewsList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<ReviewModel> getReviewsList() {
        return reviewsList;
    }

    public void setReviewsList(List<ReviewModel> reviewsList) {
        this.reviewsList = reviewsList;
    }
}
