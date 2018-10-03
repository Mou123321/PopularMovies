package com.mou.popularmovies;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.PopupMenu;

import com.mou.popularmovies.data.Repository;
import com.mou.popularmovies.data.model.MovieModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MovieNavigator {

    private MoviesRecyclerViewAdapter adapter;
    private MainActivityViewModel viewModel;

    public static List<MovieModel> movieList;

    private static RecyclerView recyclerView;

    private static Parcelable mBundleRecyclerViewState;

    private static int NUM_COL = 3;

    private boolean showFavored;

    private final String KEY_RECYCLER_STATE = "recycler_state";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init(savedInstanceState);
    }

    private void init(Bundle savedInstanceState) {
        recyclerView = findViewById(R.id.recycler_view);

        movieList = new ArrayList<>();
        adapter = new MoviesRecyclerViewAdapter(new ArrayList<>(), this);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, NUM_COL);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter);

        viewModel = ViewModelProviders.of(this, new MainViewModelFactory(Repository.getInstance(getApplicationContext()))).get(MainActivityViewModel.class);

        if (savedInstanceState != null) {
            adapter.update(viewModel.getImageUrlList(viewModel.getMovieList()));
        } else {
            viewModel.displayFilteredMovies(adapter, true);
        }

        showFavored = false;

        ImageView view = findViewById(R.id.select_button);
        view.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(MainActivity.this, view);
            popupMenu.getMenuInflater().inflate(R.menu.main_popup_menu, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(item -> {
                if (item.getTitle().equals(getString(R.string.popular_sort_text))) {
                    displayFilteredMovies(true);
                    showFavored = false;
                } else if (item.getTitle().equals(getString(R.string.top_rated_sort_text))) {
                    displayFilteredMovies(false);
                    showFavored = false;
                } else {
                    showFavored = true;
                    viewModel.getFavoriteMovieList().observe(this, entities -> {
                        if (showFavored) {
                            viewModel.setFavoriteMovie(adapter, entities);
                        }
                    });
                }
                return true;
            });
            popupMenu.show();
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(KEY_RECYCLER_STATE, recyclerView.getLayoutManager().onSaveInstanceState());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        mBundleRecyclerViewState = savedInstanceState.getParcelable(KEY_RECYCLER_STATE);
        super.onRestoreInstanceState(savedInstanceState);
    }

    private void displayFilteredMovies(boolean getTop) {
        viewModel.displayFilteredMovies(adapter, getTop);
    }

    public static void restoreState() {
        if (mBundleRecyclerViewState != null) {
            recyclerView.getLayoutManager().onRestoreInstanceState(mBundleRecyclerViewState);
            mBundleRecyclerViewState = null;
        }
    }

    @Override
    public void getMovieDetail(int position) {
        Intent intent = new Intent(this, MoviePosterDetailsActivity.class);
        intent.putExtra("movie", viewModel.getMovieList().get(position));
        startActivity(intent);
    }
}
