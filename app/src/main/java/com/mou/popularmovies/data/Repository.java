package com.mou.popularmovies.data;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.mou.popularmovies.data.model.ListMovieModel;
import com.mou.popularmovies.data.model.ListVideoModel;
import com.mou.popularmovies.data.model.MovieModel;
import com.mou.popularmovies.data.model.VideoModel;
import com.mou.popularmovies.data.remote.MovieService;
import com.mou.popularmovies.utils.ApiUtils;

import java.util.List;

import rx.Observable;
import rx.schedulers.Schedulers;


public class Repository {
    @Nullable
    private static Repository INSTANCE;

    @NonNull
    private final MovieService mService;

    private Repository() {
        this.mService = ApiUtils.getMovieService();
    }

    public static Repository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Repository();
        }
        return INSTANCE;
    }

    public Observable<List<MovieModel>> getPopMovieList() {
        return mService.getPopMovies()
                .subscribeOn(Schedulers.io())
                .map(this::getMovieList);
    }

    public Observable<List<MovieModel>> getTopRatedMovieList() {
        return mService.getTopRatedMovies()
                .subscribeOn(Schedulers.io())
                .map(this::getMovieList);
    }

    public Observable<List<VideoModel>> getVideoList(String id) {
        return mService.getVideo(id)
                .subscribeOn(Schedulers.io())
                .map(this::getVideos);
    }

    private List<MovieModel> getMovieList(ListMovieModel listMovieModel) {
        return listMovieModel.getMovieList();
    }

    private List<VideoModel> getVideos(ListVideoModel listVideoModel) {
        return listVideoModel.getVideoList();
    }
}
