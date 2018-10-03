package com.mou.popularmovies.data.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {FavoriteMovieEntity.class}, version = 1, exportSchema = false)
public abstract class FavoriteMovieDatabase extends RoomDatabase{

    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "favoriteMovies";

    private static FavoriteMovieDatabase INSTANCE;

    public static FavoriteMovieDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (LOCK) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        FavoriteMovieDatabase.class, FavoriteMovieDatabase.DATABASE_NAME)
                        .build();
            }
        }

        return INSTANCE;
    }

    public abstract FavoriteMovieDao favoriteMovieDao();
}
