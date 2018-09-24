package com.mou.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.PopupMenu;

import com.mou.popularmovies.data.Repository;
import com.mou.popularmovies.data.model.ListMovieModel;
import com.mou.popularmovies.data.model.MovieModel;
import com.mou.popularmovies.data.remote.MovieService;
import com.mou.popularmovies.utils.ApiUtils;

import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements MovieNavigator {

    private MoviesRecyclerViewAdapter adapter;
    private MainActivityViewModel viewModel;

    public static List<MovieModel> movieList;

    private static int NUM_COL = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        RecyclerView recyclerView = findViewById(R.id.recycler_view);

        movieList = new ArrayList<>();
        adapter = new MoviesRecyclerViewAdapter(new ArrayList<>(), this);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, NUM_COL);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter);

        viewModel = new MainActivityViewModel(Repository.getInstance());
        viewModel.setPop(adapter, true);

        ImageView view = findViewById(R.id.select_button);
        view.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(MainActivity.this, view);
            popupMenu.getMenuInflater().inflate(R.menu.main_popup_menu, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(item -> {
                if (item.getTitle().equals(getString(R.string.popular_sort_text))) {
                    displayPopMovies(true);
                } else {
                    displayPopMovies(false);
                }
                return true;
            });
            popupMenu.show();
        });
    }

    private void displayPopMovies(boolean getTop) {
        viewModel.setPop(adapter, getTop);
    }

    @Override
    public void getMovieDetail(int position) {
        Intent intent = new Intent(this, MoviePosterDetailsActivity.class);
        intent.putExtra("movie", viewModel.getMovieList().get(position));
        startActivity(intent);
    }
}
