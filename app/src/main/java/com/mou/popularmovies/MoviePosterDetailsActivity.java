package com.mou.popularmovies;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mou.popularmovies.databinding.MoviePosterDetailsBinding;

public class MoviePosterDetailsActivity extends AppCompatActivity{
    private MoviePosterDetailsBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int position = (int) getIntent().getExtras().get("movie");
        MoviePosterDetailsViewModel viewModel = new MoviePosterDetailsViewModel(MainActivity.movieList.get(position));
        binding = DataBindingUtil.setContentView(this, R.layout.movie_poster_details);
        binding.setVm(viewModel);
    }
}
