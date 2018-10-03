package com.mou.popularmovies;

import android.arch.lifecycle.LiveData;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.support.annotation.Nullable;
import android.util.Pair;

import com.mou.popularmovies.data.Repository;
import com.mou.popularmovies.data.model.MovieModel;
import com.mou.popularmovies.data.model.ReviewModel;
import com.mou.popularmovies.data.model.VideoModel;
import com.mou.popularmovies.data.room.FavoriteMovieEntity;

import java.lang.ref.WeakReference;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;


public class MoviePosterDetailsViewModel {
    public final String title;
    public final String imageUrl;
    public final String voteAverage;
    public final String date;
    public final String overview;
    public final String id;
    public ObservableArrayList<String> name;
    public ObservableArrayList<String> key;
    public ObservableArrayList<Pair<String, String>> reviews;

    public ObservableField<Boolean> favored;

    public Repository repository;
    private List<VideoModel> videos;

    @Nullable
    private WeakReference<TrailerNavigator> mNavigator;

    public static final int TRAILER_LAYOUT_ID = R.layout.trailer_item;
    public static final int REVIEW_LAYOUT_ID = R.layout.review_item;


    public MoviePosterDetailsViewModel(MovieModel movieModel, Repository repository) {
        title = movieModel.getTitle();
        imageUrl = MainActivityViewModel.BASE_IMAGE_URL +  movieModel.getImageUrl();
        voteAverage = String.valueOf(movieModel.getVoteAverage() + "/10");
        date = movieModel.getReleaseDate();
        overview = movieModel.getOverview();
        id = String.valueOf(movieModel.getId());
        this.repository = repository;

        name = new ObservableArrayList<>();
        key = new ObservableArrayList<>();
        reviews = new ObservableArrayList<>();

        favored = new ObservableField<>(false);

        this.repository.checkIfInDatabase(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(m -> favored.set(true), e -> favored.set(false));

        getVideos(id);
        getReviews(id);
    }

    public void setNavigator(TrailerNavigator navigator) {
        mNavigator = new WeakReference<>(navigator);
    }

    private void getVideos(String id) {
        repository.getVideoList(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::getKeyAndName);
    }

    private void getReviews(String id) {
        repository.getReviewList(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::getReview);
    }

    private void getKeyAndName(List<VideoModel> list) {
        videos = list;
        for (VideoModel video : list) {
            name.add(video.getName());
            key.add(video.getKey());
        }
    }

    private void getReview(List<ReviewModel> list) {
        for (ReviewModel review : list) {
            reviews.add(new Pair<>(review.getAuthor(), review.getContent()));
        }
    }

    public void displayTrailer(int index) {
        if (mNavigator != null  && mNavigator.get() != null) {
            mNavigator.get().display(key.get(index));
        }
    }

    public void favoriteMovie() {
        if (!favored.get()) {
            repository.insertFavorite(new FavoriteMovieEntity(title, id))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(() -> favored.set(true), Throwable::getCause);
        } else {
            repository.deleteFavorite(id)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(() -> favored.set(false), Throwable::getCause);
        }
    }
}
