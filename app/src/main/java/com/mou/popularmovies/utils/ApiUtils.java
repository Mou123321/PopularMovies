package com.mou.popularmovies.utils;

import com.mou.popularmovies.data.remote.MovieService;
import com.mou.popularmovies.data.remote.RetrofitClient;

public class ApiUtils {

    public static final String BASE_URL = "https://api.themoviedb.org/";

    public static MovieService getMovieService() {
        return RetrofitClient.getClient(BASE_URL).create(MovieService.class);
    }
}
