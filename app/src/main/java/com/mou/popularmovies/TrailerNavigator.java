package com.mou.popularmovies;

import rx.Completable;

public interface TrailerNavigator {
    void display(String key);
    void favorite(Completable completable);
}
