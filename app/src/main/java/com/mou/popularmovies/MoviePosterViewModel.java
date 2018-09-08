package com.mou.popularmovies;

import android.support.annotation.Nullable;

import java.lang.ref.WeakReference;

public class MoviePosterViewModel {

    public final String imageString;
    public final int position;

    @Nullable
    private WeakReference<MovieNavigator> mNavigator;

    public MoviePosterViewModel(String imageUrl, int position) {
        imageString = imageUrl;
        this.position = position;
    }

    public void setNavigator(MovieNavigator navigator) {
        mNavigator = new WeakReference<>(navigator);
    }

    public void posterClicked() {
        if (mNavigator != null && mNavigator.get() != null) {
            mNavigator.get().getMovieDetail(position);
        }
    }
}
