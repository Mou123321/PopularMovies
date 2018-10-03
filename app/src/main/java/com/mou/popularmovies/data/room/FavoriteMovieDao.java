package com.mou.popularmovies.data.room;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Observable;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface FavoriteMovieDao {

    @Insert(onConflict = REPLACE)
    void favored(FavoriteMovieEntity favoriteMovie);

    @Query("DELETE FROM favoriteMovies WHERE movieId = :movieId")
    void unFavored(String movieId);

    @Query("SELECT * FROM favoriteMovies")
    LiveData<List<FavoriteMovieEntity>> getAll();

    @Query("SELECT * FROM favoriteMovies WHERE movieId = :movieId LIMIT 1")
    FavoriteMovieEntity getMovie(String movieId);
}
