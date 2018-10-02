package com.mou.popularmovies.data.room;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity (tableName = "favoriteMovies")
public class FavoriteMovieEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String movieTitle;
    private String movieId;

    @Ignore
    public FavoriteMovieEntity(String movieTitle, String movieId) {
        this.movieTitle = movieTitle;
        this.movieId = movieId;
    }

    public FavoriteMovieEntity(int id, String movieTitle, String movieId) {
        this.id = id;
        this.movieTitle = movieTitle;
        this.movieId = movieId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }
}
