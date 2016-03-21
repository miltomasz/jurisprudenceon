package com.plumya.jurisprudenceon.listeners;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.parse.ParseObject;
import com.parse.ParseQueryAdapter;
import com.plumya.jurisprudenceon.fragments.CourtRoomFragment;

import java.util.List;

/**
 * Created by Tomasz Milczarek on 15/05/15.
 */
public class CourtRoomsOnQueryLoadListener implements ParseQueryAdapter.OnQueryLoadListener<ParseObject> {

    private ProgressBar mProgressBar;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private CourtRoomFragment.OnJudgementLoadedListener mCallback;

    public CourtRoomsOnQueryLoadListener(ProgressBar progressBar,
                                         SwipeRefreshLayout swipeRefreshLayout,
                                         CourtRoomFragment.OnJudgementLoadedListener callback) {
        mProgressBar = progressBar;
        mSwipeRefreshLayout = swipeRefreshLayout;
        mCallback = callback;
    }

    @Override
    public void onLoading() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onLoaded(List<ParseObject> list, Exception exception) {
        mProgressBar.setVisibility(View.GONE);
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
        mCallback.loaded(list);
    }
}