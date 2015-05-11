package com.plumya.jurisprudenceon.fragments;

import android.os.Bundle;

import com.parse.ParseQuery;

/**
 * Created by toml on 11.05.15.
 */
public class ICRoomFragment extends CourtRoomFragment {

    public static CourtRoomFragment newInstance(int position) {
        ICRoomFragment fragment = new ICRoomFragment();
        Bundle args = new Bundle();
        args.putInt(CourtRoomFragment.ARG_COURT_ROOM_NUMBER, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected ParseQuery whereConditions(ParseQuery query) {
        query.whereEqualTo("courtRoom", "IC");
        return query;
    }
}
