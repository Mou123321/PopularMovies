package com.mou.popularmovies;

import android.databinding.ObservableArrayList;
import android.support.annotation.Nullable;

import com.mou.popularmovies.data.Repository;
import com.mou.popularmovies.data.model.MovieModel;
import com.mou.popularmovies.data.model.VideoModel;

import java.lang.ref.WeakReference;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;

public class MoviePosterDetailsViewModel {
    public final String title;
    public final String imageUrl;
    public final String voteAverage;
    public final String date;
    public final String overview;
    public final String id;
    public ObservableArrayList<String> name;
    public ObservableArrayList<String> key;

    private Repository repository;
    private List<VideoModel> videos;

    @Nullable
    private WeakReference<TrailerNavigator> mNavigator;

    public static final int TRAILER_LAYOUT_ID = R.layout.trailer_item;

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

        getVideos(id);
    }

    public void setNavigator(TrailerNavigator navigator) {
        mNavigator = new WeakReference<>(navigator);
    }

    private void getVideos(String id) {
        repository.getVideoList(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::getKeyAndName);
    }

    private void getKeyAndName(List<VideoModel> list) {
        videos = list;
        System.out.println("list is " + list);
        for (VideoModel video : list) {
            name.add(video.getName());
            key.add(video.getKey());
        }
    }

    public void displayTrailer(int index) {
        if (mNavigator != null  && mNavigator.get() != null) {
            mNavigator.get().display(key.get(index));
        }
    }
}
