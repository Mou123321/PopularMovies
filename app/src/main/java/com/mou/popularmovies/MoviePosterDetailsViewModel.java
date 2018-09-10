package com.mou.popularmovies;

import com.mou.popularmovies.data.model.MovieModel;

public class MoviePosterDetailsViewModel {
    public final String title;
    public final String imageUrl;
    public final String voteAverage;
    public final String date;
    public final String overview;

    public MoviePosterDetailsViewModel(MovieModel movieModel) {
        title = movieModel.getTitle();
        imageUrl = MainActivity.BASE_IMAGE_URL +  movieModel.getImageUrl();
        voteAverage = String.valueOf(movieModel.getVoteAverage() + "/10");
        date = movieModel.getReleaseDate();
        overview = movieModel.getOverview();
    }
}
