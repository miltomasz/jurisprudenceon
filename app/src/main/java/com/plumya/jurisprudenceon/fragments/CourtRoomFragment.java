package com.plumya.jurisprudenceon.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.plumya.jurisprudenceon.R;

import java.util.Locale;

/**
 * Created by toml on 20.03.15.
 */
public class CourtRoomFragment extends Fragment {
    public static final String ARG_COURT_ROOM_NUMBER = "court_room_number";
    private String courtRoom;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

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
        int courtRoomNumber = getArguments().getInt(ARG_COURT_ROOM_NUMBER);
        courtRoom = getResources().getStringArray(R.array.court_rooms_array)[courtRoomNumber];
        int imageId = getResources().getIdentifier(courtRoom.toLowerCase(Locale.getDefault()),
                "drawable", getActivity().getPackageName());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.court_room_fragment, container, false);
        rootView.findViewById(R.id.judgment_list);
        getActivity().setTitle(courtRoom);
        return rootView;
    }

    public RecyclerView.Adapter getAdapter(Cursor cursor) {
        return null;
    }

    private class CourtRoomAdapter extends RecyclerView.Adapter<CourtRoomAdapter.ViewHolder> {
        private String[] mDataset;

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return null;
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int i) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            // each data item is just a string in this case
            public TextView mTextView;
            public ViewHolder(TextView v) {
                super(v);
                mTextView = v;
            }
        }
    }

}