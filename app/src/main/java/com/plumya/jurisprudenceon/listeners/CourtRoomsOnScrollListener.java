package com.plumya.jurisprudenceon.listeners;

import android.util.Log;
import android.widget.AbsListView;

import com.crashlytics.android.Crashlytics;
import com.parse.ParseQueryAdapter;

/**
 * Created by miltomasz on 15/05/15.
 */
public class CourtRoomsOnScrollListener implements AbsListView.OnScrollListener {

    private static final String TAG = CourtRoomsOnScrollListener.class.getSimpleName();
    private ParseQueryAdapter mParseQueryAdapter;
    private int preLast = 0;

    public CourtRoomsOnScrollListener(ParseQueryAdapter adapter) {
        this.mParseQueryAdapter = adapter;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) { }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        final int lastItem = firstVisibleItem + visibleItemCount;
        if (lastItem == totalItemCount) {
            if (preLast != lastItem) { //this avoid multiple calls for the last item
                preLast = lastItem;
                try {
                    mParseQueryAdapter.loadNextPage();
                } catch (IndexOutOfBoundsException iobe) {
                    Crashlytics.logException(iobe);
                    Log.d(TAG, "Exception at loading next page: " + iobe.getMessage());
                    // Stop loading next records
                }
            }
        }
    }
}