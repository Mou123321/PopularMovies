package com.mou.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.PersistableBundle;
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

    private static RecyclerView recyclerView;

    private static Parcelable mBundleRecyclerViewState;

    private static int NUM_COL = 3;

    private final String KEY_RECYCLER_STATE = "recycler_state";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        recyclerView = findViewById(R.id.recycler_view);

        movieList = new ArrayList<>();
        adapter = new MoviesRecyclerViewAdapter(new ArrayList<>(), this);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, NUM_COL);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter);

        viewModel = new MainActivityViewModel(Repository.getInstance(getApplicationContext()));
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

    private void displayPopMovies(boolean getTop) {
        viewModel.setPop(adapter, getTop);
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
