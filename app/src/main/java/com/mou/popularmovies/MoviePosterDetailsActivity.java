package com.mou.popularmovies;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mou.popularmovies.data.Repository;
import com.mou.popularmovies.databinding.MoviePosterDetailsBinding;

public class MoviePosterDetailsActivity extends AppCompatActivity implements TrailerNavigator{
    private MoviePosterDetailsBinding binding;

    private static final String YOUTUBE_BASE_URL = "http://www.youtube.com/watch?v=";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MoviePosterDetailsViewModel viewModel = new MoviePosterDetailsViewModel(getIntent().getExtras().getParcelable("movie"), Repository.getInstance());
        binding = DataBindingUtil.setContentView(this, R.layout.movie_poster_details);
        viewModel.setNavigator(this);
        binding.setVm(viewModel);
    }

    @Override
    public void display(String key) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(YOUTUBE_BASE_URL + key)));
    }
}
