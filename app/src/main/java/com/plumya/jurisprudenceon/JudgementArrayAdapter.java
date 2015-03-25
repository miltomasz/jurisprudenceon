package com.plumya.jurisprudenceon;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;

import com.plumya.jurisprudenceon.model.Judgement;

/**
 * Created by toml on 25.03.15.
 */
public class JudgementArrayAdapter extends ArrayAdapter<Judgement> {

    private final LayoutInflater mLayoutInflater;

    private final int mResourceId;


    public JudgementArrayAdapter(Context context, int resource) {
        super(context, resource);

        mLayoutInflater = LayoutInflater.from(context);
        mResourceId = resource;
    }
}
