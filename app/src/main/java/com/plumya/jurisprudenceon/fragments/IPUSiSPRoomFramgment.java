package com.plumya.jurisprudenceon.fragments;

import android.os.Bundle;

import com.parse.ParseQuery;

/**
 * Created by toml on 11.05.15.
 */
public class IPUSiSPRoomFramgment extends CourtRoomFragment {

    public static CourtRoomFragment newInstance(int position) {
        IPUSiSPRoomFramgment fragment = new IPUSiSPRoomFramgment();
        Bundle args = new Bundle();
        args.putInt(CourtRoomFragment.ARG_COURT_ROOM_NUMBER, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected ParseQuery whereConditions(ParseQuery query) {
        query.whereEqualTo("courtRoom", "IPUSiSP");
        return query;
    }
}
