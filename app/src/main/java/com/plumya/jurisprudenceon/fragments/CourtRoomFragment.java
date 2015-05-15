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
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.noveogroup.android.log.Logger;
import com.noveogroup.android.log.LoggerManager;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.plumya.jurisprudenceon.R;
import com.plumya.jurisprudenceon.app.CourtRoomAdapter;
import com.plumya.jurisprudenceon.app.InfiniteScrollListener;
import com.plumya.jurisprudenceon.app.JudgementActivity;
import com.plumya.jurisprudenceon.model.Judgement;

import org.apache.commons.lang3.SerializationUtils;

import java.util.HashMap;
import java.util.List;
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

    @InjectView(R.id.marker_progress)
    ProgressBar markerProgress;
    private CourRoomsOnQueryLoadListener listener;


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
//        mainAdapter.setTextKey("signature");

        // Perhaps set a callback to be fired upon successful loading of a new set of ParseObjects.
        listener = new CourRoomsOnQueryLoadListener(markerProgress);
        mainAdapter.addOnQueryLoadListener(listener);
        mainAdapter.setObjectsPerPage(6);

        listView.setOnScrollListener(new CourRoomsOnScrollListener(mainAdapter));
        listView.setAdapter(mainAdapter);
        listView.setOnItemClickListener(this);
//        mainAdapter.loadObjects();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshContent();
            }

            private void refreshContent() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mainAdapter.removeOnQueryLoadListener(listener);
                        mainAdapter.loadObjects();
                        swipeRefreshLayout.setRefreshing(false);
                        mainAdapter.addOnQueryLoadListener(listener);
                    }
                }, 1000);
            }
        });
        return rootView;
    }

    private static class CourRoomsOnQueryLoadListener implements ParseQueryAdapter.OnQueryLoadListener<ParseObject> {

        private ProgressBar progressBar;

        public CourRoomsOnQueryLoadListener(ProgressBar progressBar) {
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

    private static class CourRoomsOnScrollListener implements AbsListView.OnScrollListener {

        private ParseQueryAdapter adapter;
        private int preLast = 0;

        public CourRoomsOnScrollListener(ParseQueryAdapter adapter) {

            this.adapter = adapter;
        }
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {

        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            final int lastItem = firstVisibleItem + visibleItemCount;
            if (lastItem == totalItemCount) {
                if (preLast != lastItem) { //this avoid multiple calls for the last item
                    preLast = lastItem;
                    adapter.loadNextPage();
                }
            }
        }
    }


    public RecyclerView.Adapter getAdapter(Cursor cursor) {
        return null;
    }

    protected ParseQueryAdapter.QueryFactory<ParseObject> factory() {
        return new ParseQueryAdapter.QueryFactory<ParseObject>() {
            public ParseQuery create() {
                ParseQuery query = ParseQuery.getQuery("Judgement");
                query = whereConditions(query);
//                query.setLimit(6);
                return query;
            }
        };
    }

    protected abstract ParseQuery whereConditions(ParseQuery query);

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ParseObject judgement = (ParseObject) parent.getItemAtPosition(position);
        Intent intent = new Intent(getActivity(), JudgementActivity.class);
        intent.putExtra(JudgementActivity.JUDGEMENT_DATA, new Judgement(judgement));
        startActivity(intent);
    }
}