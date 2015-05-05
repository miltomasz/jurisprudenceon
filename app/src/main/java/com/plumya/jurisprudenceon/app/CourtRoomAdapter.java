package com.plumya.jurisprudenceon.app;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.plumya.jurisprudenceon.R;

/**
 * Created by toml on 05.05.15.
 */
public class CourtRoomAdapter extends RecyclerView.Adapter<CourtRoomAdapter.ViewHolder> {
    private String[] mDataset;

    public CourtRoomAdapter(String[] myDataset) {
        mDataset = myDataset;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.judgement_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public View mTextView;
        public ViewHolder(View view) {
            super(view);
            mTextView = view;
        }
    }
}