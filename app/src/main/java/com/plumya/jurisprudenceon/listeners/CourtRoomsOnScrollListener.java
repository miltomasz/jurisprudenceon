package com.plumya.jurisprudenceon.listeners;

import android.widget.AbsListView;

import com.parse.ParseQueryAdapter;

/**
 * Created by miltomasz on 15/05/15.
 */
public class CourtRoomsOnScrollListener implements AbsListView.OnScrollListener {

    private ParseQueryAdapter adapter;
    private int preLast = 0;

    public CourtRoomsOnScrollListener(ParseQueryAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) { }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        final int lastItem = firstVisibleItem + visibleItemCount;
        if (lastItem == totalItemCount) {
            if (preLast != lastItem) { //this avoid multiple calls for the last item
                preLast = lastItem;
                adapter.loadNextPage();
            }
        }
    }
}