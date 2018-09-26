package com.mou.popularmovies;

import android.util.Log;

import com.mou.popularmovies.data.Repository;
import com.mou.popularmovies.data.model.MovieModel;

import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;

public class MainActivityViewModel {
    private List<MovieModel> movieList;
    private Repository repository;

    private static String POP_ERROR_TAG = "Pop loading errors",
            TOP_RATED_ERROR_TAG = "Top rated loading error";

    public static String BASE_IMAGE_URL = "http://image.tmdb.org/t/p/w185/";

    public MainActivityViewModel(Repository repository) {
        this.repository = repository;
    }

    public List<MovieModel> getMovieList() {
        return movieList;
    }

    public void setPop(MoviesRecyclerViewAdapter adapter, boolean getTop) {
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

    private List<String> getImageUrlList(List<MovieModel> list) {
        List<String> imageUrls = new ArrayList<>();
        for (MovieModel movie : list) {
            String imageUrl = BASE_IMAGE_URL + movie.getImageUrl();
            imageUrls.add(imageUrl);
        }
        return imageUrls;
    }
}
