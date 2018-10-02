package com.mou.popularmovies.data;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.databinding.ObservableArrayList;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.mou.popularmovies.data.model.ListMovieModel;
import com.mou.popularmovies.data.model.ListReviewModel;
import com.mou.popularmovies.data.model.ListVideoModel;
import com.mou.popularmovies.data.model.MovieModel;
import com.mou.popularmovies.data.model.ReviewModel;
import com.mou.popularmovies.data.model.VideoModel;
import com.mou.popularmovies.data.remote.MovieService;
import com.mou.popularmovies.data.room.FavoriteMovieDao;
import com.mou.popularmovies.data.room.FavoriteMovieDatabase;
import com.mou.popularmovies.data.room.FavoriteMovieEntity;
import com.mou.popularmovies.utils.ApiUtils;

import java.util.List;

import rx.Completable;
import rx.Observable;
import rx.schedulers.Schedulers;


public class Repository {
    @Nullable
    private static Repository INSTANCE;

    @NonNull
    private final MovieService mService;

    private final FavoriteMovieDao favoriteMovieDao;

    private Repository(Context context) {
        this.mService = ApiUtils.getMovieService();
        this.favoriteMovieDao = FavoriteMovieDatabase.getInstance(context).favoriteMovieDao();
    }

    public static Repository getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new Repository(context);
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

    public Observable<List<ReviewModel>> getReviewList(String id) {
        return mService.getReview(id)
                .subscribeOn(Schedulers.io())
                .map(this::getReviews);
    }

    private List<MovieModel> getMovieList(ListMovieModel listMovieModel) {
        return listMovieModel.getMovieList();
    }

    private List<VideoModel> getVideos(ListVideoModel listVideoModel) {
        return listVideoModel.getVideoList();
    }

    private List<ReviewModel> getReviews(ListReviewModel listReviewModel) {
        return listReviewModel.getReviewsList();
    }

    public LiveData<List<FavoriteMovieEntity>> getFavorite() {
        return favoriteMovieDao.getAll();
    }

    public Completable insertFavorite(FavoriteMovieEntity favoriteMovie) {
        return Completable.fromAction(() -> favoriteMovieDao.fovorited(favoriteMovie))
                .subscribeOn(Schedulers.io());
    }

    public Completable deleteFavorite(FavoriteMovieEntity unFavoriteMovie) {
        return Completable.fromAction(() -> favoriteMovieDao.unFavorited(unFavoriteMovie))
                .subscribeOn(Schedulers.io());
    }
}
