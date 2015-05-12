package com.plumya.jurisprudenceon.fragments;

import android.content.Intent;
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
import android.widget.AdapterView;
import android.widget.ListView;

import com.noveogroup.android.log.Logger;
import com.noveogroup.android.log.LoggerManager;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.plumya.jurisprudenceon.R;
import com.plumya.jurisprudenceon.app.CourtRoomAdapter;
import com.plumya.jurisprudenceon.app.JudgementActivity;

import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by toml on 20.03.15.
 */
public abstract class CourtRoomFragment extends Fragment implements AdapterView.OnItemClickListener {
    private static final Logger logger = LoggerManager.getLogger();
    public static final String ARG_COURT_ROOM_NUMBER = "court_room_number";
    protected ParseQueryAdapter<ParseObject> mainAdapter;
    private String courtRoom;
    private int courtRoomNumber;

    protected ListView listView;

    @InjectView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

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
        mainAdapter = new CourtRoomAdapter(getActivity(), factory());
        mainAdapter.setTextKey("signature");

        listView.setAdapter(mainAdapter);
        listView.setOnItemClickListener(this);
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

    protected ParseQueryAdapter.QueryFactory<ParseObject> factory() {
        return new ParseQueryAdapter.QueryFactory<ParseObject>() {
            public ParseQuery create() {
                ParseQuery query = ParseQuery.getQuery("Judgement");
                query = whereConditions(query);
                return query;
            }
        };
    }

    protected abstract ParseQuery whereConditions(ParseQuery query);

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ParseObject judgement = (ParseObject) parent.getItemAtPosition(position);
        Intent intent = new Intent(getActivity(), JudgementActivity.class);
        intent.putExtra("signature", judgement.getString("signature"));
        intent.putExtra("judgement_date", judgement.getString("judgement_date"));
        intent.putExtra("bench", judgement.getString("bench"));
        intent.putExtra("issue", judgement.getString("issue"));
        intent.putExtra("decision", judgement.getString("decision"));
        intent.putExtra("justification", judgement.getString("justification"));
        intent.putExtra("rule", judgement.getString("rule"));
        intent.putExtra("attachement", judgement.getString("attachement"));
        startActivity(intent);
    }
}