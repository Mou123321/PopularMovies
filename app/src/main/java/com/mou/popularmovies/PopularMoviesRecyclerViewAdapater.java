package com.mou.popularmovies;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.mou.popularmovies.databinding.MoviePosterListItemBinding;

import java.util.List;

public class PopularMoviesRecyclerViewAdapater extends RecyclerView.Adapter<PopularMoviesRecyclerViewAdapater.MoviePosterViewHolder>{

    List<MoviePosterModel> movieList;

    public PopularMoviesRecyclerViewAdapater(List<MoviePosterModel> movieList) {
        this.movieList = movieList;
    }

    @Override
    public MoviePosterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        MoviePosterListItemBinding binding =
                MoviePosterListItemBinding.inflate(layoutInflater, parent, false);
        return new MoviePosterViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(MoviePosterViewHolder holder, int position) {
        holder.bind(movieList.get(position));
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class MoviePosterViewHolder extends RecyclerView.ViewHolder {
        private final MoviePosterListItemBinding mBinding;

        public MoviePosterViewHolder(MoviePosterListItemBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        public void bind(MoviePosterModel moviePosterModel) {
            mBinding.setVm(new MoviePosterViewModel(moviePosterModel));
        }
    }
}
