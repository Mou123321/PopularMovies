package com.mou.popularmovies;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mou.popularmovies.data.Repository;
import com.mou.popularmovies.databinding.MoviePosterDetailsBinding;

import rx.Completable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;

public class MoviePosterDetailsActivity extends AppCompatActivity implements TrailerNavigator{

    private static MoviePosterDetailsBinding binding;
    private MoviePosterDetailsViewModel viewModel;

    private static final String YOUTUBE_BASE_URL = "http://www.youtube.com/watch?v=";

    private static int[] position;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MoviePosterDetailsViewModel viewModel = new MoviePosterDetailsViewModel(getIntent().getExtras().getParcelable("movie"), Repository.getInstance(getApplicationContext()));
        binding = DataBindingUtil.setContentView(this, R.layout.movie_poster_details);
        viewModel.setNavigator(this);
        binding.setVm(viewModel);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putIntArray("ARTICLE_SCROLL_POSITION",
                new int[]{ binding.scrollView.getScrollX(), binding.scrollView.getScrollY()});
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        position = savedInstanceState.getIntArray("ARTICLE_SCROLL_POSITION");
    }

    public static void restoreState() {
        if(position != null)
            binding.scrollView.post(() -> {
                binding.scrollView.scrollTo(position[0], position[1]);
            });
    }

    @Override
    public void display(String key) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(YOUTUBE_BASE_URL + key)));
    }

    @Override
    public void favorite(Completable completable) {
        completable.observeOn(AndroidSchedulers.mainThread())
                .subscribe(() ->viewModel.favorietIt.set(!viewModel.favorietIt.get()));
    }

    @Override
    protected void onDestroy() {
        position = null;
        super.onDestroy();
    }
}
