package com.mou.popularmovies;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

public class MoviesRecyclerViewAdapter extends RecyclerView.Adapter<MoviesRecyclerViewAdapter.MoviePosterViewHolder>{

    private List<String> imageUrls;

    private MovieNavigator mNavigator;

    public MoviesRecyclerViewAdapter(List<String> imageUrls, MovieNavigator navigator) {
        this.imageUrls = imageUrls;
        mNavigator = navigator;
    }

    @Override
    public MoviePosterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding binding = DataBindingUtil.inflate(layoutInflater, viewType, parent, false);

        return new MoviePosterViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(MoviePosterViewHolder holder, int position) {
        holder.bind(imageUrls.get(position), position);
    }

    @Override
    public int getItemCount() {
        return imageUrls.size();
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.movie_poster_list_item;
    }

    public class MoviePosterViewHolder extends RecyclerView.ViewHolder {
        private final ViewDataBinding mBinding;

        public MoviePosterViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        public void bind(String imageUrl, int position) {
            MoviePosterViewModel vm = new MoviePosterViewModel(imageUrl, position);
            vm.setNavigator(mNavigator);
            mBinding.setVariable(BR.vm, vm);
            mBinding.executePendingBindings();
        }
    }
}
