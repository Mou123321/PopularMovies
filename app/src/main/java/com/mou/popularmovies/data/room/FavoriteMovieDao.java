package com.mou.popularmovies.data.room;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface FavoriteMovieDao {

    @Insert(onConflict = REPLACE)
    void fovorited(FavoriteMovieEntity favoriteMovie);

    @Delete
    void unFavorited(FavoriteMovieEntity unFavoriteMovie);

    @Query("SELECT * FROM favoriteMovies")
    LiveData<List<FavoriteMovieEntity>> getAll();
}
