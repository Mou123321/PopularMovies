package com.mou.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.PopupMenu;

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
    private RecyclerView recyclerView;
    private MovieService mService;

    public static List<MovieModel> movieList;

    public static String BASE_IMAGE_URL = "http://image.tmdb.org/t/p/w185/";

    public static String POP_ERROR_TAG = "Pop loading errors",
                        TOP_RATED_ERROR_TAG = "Top rated loading error";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        mService = ApiUtils.getMovieService();
        recyclerView = findViewById(R.id.recycler_view);

        movieList = new ArrayList<>();
        adapter = new MoviesRecyclerViewAdapter(new ArrayList<>(), this);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter);

        displayPopMovies();

        ImageView view = findViewById(R.id.select_button);
        view.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(MainActivity.this, view);
            popupMenu.getMenuInflater().inflate(R.menu.main_popup_menu, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(item -> {
                if (item.getTitle().equals(getString(R.string.popular_sort_text))) {
                    displayPopMovies();
                } else {
                    displayTopRatedMovies();
                }
                return true;
            });

            popupMenu.show();
        });
    }

    private void displayPopMovies() {
        mService.getPopMovies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(r -> adapter.update(getImageUrlList(r)),
                        e -> Log.e(e.getMessage(), POP_ERROR_TAG));
    }

    private void displayTopRatedMovies() {
        mService.getTopRatedMovies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(r -> adapter.update(getImageUrlList(r)),
                        e -> Log.e(e.getMessage(), TOP_RATED_ERROR_TAG));
    }

    private List<String> getImageUrlList(ListMovieModel listMovieModel) {
        List<MovieModel> movieList = listMovieModel.getmovieList();
        MainActivity.movieList = movieList;
        List<String> imageUrls = new ArrayList<>();
        for (MovieModel movie : movieList) {
            String imageUrl = BASE_IMAGE_URL + movie.getImageUrl();
            imageUrls.add(imageUrl);
        }
        return imageUrls;
    }

    @Override
    public void getMovieDetail(int position) {
        Intent intent = new Intent(this, MoviePosterDetailsActivity.class);
        intent.putExtra("movie", movieList.get(position));
        startActivity(intent);
    }
}
