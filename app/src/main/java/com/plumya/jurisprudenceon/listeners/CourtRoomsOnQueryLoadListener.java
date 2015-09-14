package com.plumya.jurisprudenceon.listeners;

import android.view.View;
import android.widget.ProgressBar;

import com.parse.ParseObject;
import com.parse.ParseQueryAdapter;

import java.util.List;

/**
 * Created by miltomasz on 15/05/15.
 */
public class CourtRoomsOnQueryLoadListener implements ParseQueryAdapter.OnQueryLoadListener<ParseObject> {

    private ProgressBar progressBar;

    public CourtRoomsOnQueryLoadListener(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    @Override
    public void onLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onLoaded(List<ParseObject> list, Exception e) {
        progressBar.setVisibility(View.GONE);
    }
}