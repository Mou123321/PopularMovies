package com.mou.popularmovies;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mou.popularmovies.data.model.MovieModel;
import com.mou.popularmovies.databinding.MoviePosterDetailsBinding;

public class MoviePosterDetailsActivity extends AppCompatActivity{
    private MoviePosterDetailsBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MoviePosterDetailsViewModel viewModel = new MoviePosterDetailsViewModel(getIntent().getExtras().getParcelable("movie"));
        binding = DataBindingUtil.setContentView(this, R.layout.movie_poster_details);
        binding.setVm(viewModel);
    }
}
