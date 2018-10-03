package com.mou.popularmovies.data.remote;

import com.mou.popularmovies.BuildConfig;
import com.mou.popularmovies.data.model.ListMovieModel;
import com.mou.popularmovies.data.model.ListReviewModel;
import com.mou.popularmovies.data.model.ListVideoModel;
import com.mou.popularmovies.data.model.MovieModel;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MovieService {

    //api key here
    String API_KEY = BuildConfig.API_KEY;

    String POP_END_POINT = "/3/movie/popular?api_key=" + API_KEY;

    String TOP_RATED_END_POINT = "/3/movie/top_rated?api_key=" + API_KEY;

    String VIDEO_END_POINT = "/3/movie/{id}/videos?api_key=" + API_KEY;

    String REVIEW_END_POINT = "/3/movie/{id}/reviews?api_key=" + API_KEY;

    String MOVIE_DETAIL_END_POINT = "/3/movie/{id}?api_key=" + API_KEY;

    @GET(POP_END_POINT)
    Observable<ListMovieModel> getPopMovies();

    @GET(TOP_RATED_END_POINT)
    Observable<ListMovieModel> getTopRatedMovies();

    @GET(VIDEO_END_POINT)
    Observable<ListVideoModel> getVideo(@Path("id") String id);

    @GET(REVIEW_END_POINT)
    Observable<ListReviewModel> getReview(@Path("id") String id);

    @GET(MOVIE_DETAIL_END_POINT)
    Observable<MovieModel> getMovieDetail(@Path("id") String id);
}
