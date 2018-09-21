package com.mou.popularmovies.data.remote;

import android.os.Build;

import com.mou.popularmovies.BuildConfig;
import com.mou.popularmovies.data.model.ListMovieModel;

import retrofit2.http.GET;
import rx.Observable;

public interface MovieService {

    //api key here
    String API_KEY = BuildConfig.API_KEY;

    String POP_END_POINT = "/3/movie/popular?api_key=" + API_KEY;

    String TOP_RATED_END_POINT = "/3/movie/top_rated?api_key=" + API_KEY;

    @GET(POP_END_POINT)
    Observable<ListMovieModel> getPopMovies();

    @GET(TOP_RATED_END_POINT)
    Observable<ListMovieModel> getTopRatedMovies();
}
