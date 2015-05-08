package com.plumya.jurisprudenceon.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.noveogroup.android.log.Logger;
import com.noveogroup.android.log.LoggerManager;
import com.parse.CountCallback;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.plumya.jurisprudenceon.R;
import com.plumya.jurisprudenceon.app.CourtRoomAdapter;

import java.util.List;
import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by toml on 20.03.15.
 */
public class CourtRoomFragment extends Fragment {
    private static final Logger logger = LoggerManager.getLogger();
    public static final String ARG_COURT_ROOM_NUMBER = "court_room_number";
    private String courtRoom;
    private ParseQueryAdapter<ParseObject> mainAdapter;
    private int courtRoomNumber;

    private ListView listView;
    @InjectView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    public static CourtRoomFragment newInstance(int position) {
        CourtRoomFragment fragment = new CourtRoomFragment();
        Bundle args = new Bundle();
        args.putInt(CourtRoomFragment.ARG_COURT_ROOM_NUMBER, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        courtRoomNumber = getArguments().getInt(ARG_COURT_ROOM_NUMBER);
        courtRoom = getResources().getStringArray(R.array.court_rooms_array)[courtRoomNumber];
        int imageId = getResources().getIdentifier(courtRoom.toLowerCase(Locale.getDefault()),
                "drawable", getActivity().getPackageName());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.court_room_fragment, container, false);
        ButterKnife.inject(this, rootView);

        listView = (ListView) rootView.findViewById(R.id.judgement_list);
        // Initialize main ParseQueryAdapter
        mainAdapter = new CourtRoomAdapter(getActivity(), factory(courtRoomNumber));
        mainAdapter.setTextKey("signature");

        listView.setAdapter(mainAdapter);
        mainAdapter.loadObjects();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshContent();
            }

            private void refreshContent() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mainAdapter.loadObjects();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 1000);
            }
        });
        return rootView;
    }

    public RecyclerView.Adapter getAdapter(Cursor cursor) {
        return null;
    }

    private ParseQueryAdapter.QueryFactory<ParseObject> factory(int courtRoomNumber) {
        return new ParseQueryAdapter.QueryFactory<ParseObject>() {
            public ParseQuery create() {
                ParseQuery query = ParseQuery.getQuery("Judgement");
                query.whereEqualTo("highPri", true);
                return query;
            }
        };
    }
}