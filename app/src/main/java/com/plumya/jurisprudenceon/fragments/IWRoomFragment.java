package com.plumya.jurisprudenceon.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.parse.ParseQuery;

/**
 * Created by toml on 11.05.15.
 */
public class IWRoomFragment extends CourtRoomFragment {

    public static CourtRoomFragment newInstance(int position) {
        IWRoomFragment fragment = new IWRoomFragment();
        Bundle args = new Bundle();
        args.putInt(CourtRoomFragment.ARG_COURT_ROOM_NUMBER, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected ParseQuery whereConditions(ParseQuery query) {
        query.whereEqualTo("courtRoom", "IW");
        return query;
    }
}
