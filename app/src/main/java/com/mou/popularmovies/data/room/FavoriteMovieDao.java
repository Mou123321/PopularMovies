package com.mou.popularmovies.data.room;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface FavoriteMovieDao {

    @Insert(onConflict = REPLACE)
    void fovorited(FavoriteMovieEntity favoriteMovie);

    @Query("Delete FROM favoriteMovies WHERE movieId = :movieId")
    void unFavorited(String movieId);

    @Query("SELECT * FROM favoriteMovies")
    LiveData<List<FavoriteMovieEntity>> getAll();
}
