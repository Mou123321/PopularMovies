package com.mou.popularmovies;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.mou.popularmovies.data.Repository;
import com.mou.popularmovies.data.model.MovieModel;
import com.mou.popularmovies.data.room.FavoriteMovieEntity;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class MainActivityViewModel extends ViewModel{
    private List<MovieModel> movieList;
    private Repository repository;

    private LiveData<List<FavoriteMovieEntity>> favoriteMovieList;

    private static String POP_ERROR_TAG = "Pop loading errors",
            TOP_RATED_ERROR_TAG = "Top rated loading error";

    public static String BASE_IMAGE_URL = "http://image.tmdb.org/t/p/w185/";

    public MainActivityViewModel(Repository repository) {
        this.repository = repository;
        favoriteMovieList = repository.getFavorite();
    }

    public LiveData<List<FavoriteMovieEntity>> getFavoriteMovieList() {
        return favoriteMovieList;
    }

    public List<MovieModel> getMovieList() {
        return movieList;
    }

    public void displayFilteredMovies(MoviesRecyclerViewAdapter adapter, boolean getTop) {
        if (getTop) {
            repository.getPopMovieList()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(list -> adapter.update(getImageUrlList(movieList = list)),
                            e -> Log.e(POP_ERROR_TAG, e.getMessage()));
        } else {
            repository.getTopRatedMovieList()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(list -> adapter.update(getImageUrlList(movieList = list)),
                            e -> Log.e(TOP_RATED_ERROR_TAG, e.getMessage()));
        }
    }

    public void setFavoriteMovie(MoviesRecyclerViewAdapter adapter, List<FavoriteMovieEntity> entities) {
        repository.getFavoriteMovieList(getIdList(entities))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> adapter.update(getImageUrlList(movieList = list)),
                        e -> System.out.println("error is " + e.getMessage()));
    }

    private List<String> getImageUrlList(List<MovieModel> list) {
        List<String> imageUrls = new ArrayList<>();
        for (MovieModel movie : list) {
            String imageUrl = BASE_IMAGE_URL + movie.getImageUrl();
            imageUrls.add(imageUrl);
        }
        return imageUrls;
    }

    private List<String> getIdList(List<FavoriteMovieEntity> entities) {
        List<String> ids = new ArrayList<>();
        for (FavoriteMovieEntity entity : entities) {
            ids.add(entity.getMovieId());
        }
        return ids;
    }
}
