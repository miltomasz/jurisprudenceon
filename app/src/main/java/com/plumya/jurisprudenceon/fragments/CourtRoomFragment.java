package com.plumya.jurisprudenceon.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.plumya.jurisprudenceon.R;

import java.util.Locale;

/**
 * Created by toml on 20.03.15.
 */
public class CourtRoomFragment extends Fragment {
    public static final String ARG_COURT_ROOM_NUMBER = "court_room_number";

    public CourtRoomFragment() {
        // Empty constructor required for fragment subclasses
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        int courtRoomNumber = getArguments().getInt(ARG_COURT_ROOM_NUMBER);

        String courtRoom = getResources().getStringArray(R.array.court_rooms_array)[courtRoomNumber];

        int imageId = getResources().getIdentifier(courtRoom.toLowerCase(Locale.getDefault()),
                "drawable", getActivity().getPackageName());

        // hit service to get judgments

        View rootView = inflater.inflate(R.layout.court_room_fragment, container, false);
        rootView.findViewById(R.id.list_of_judgments);

        getActivity().setTitle(courtRoom);

        return rootView;
    }

    private static class CourtRoomeService {
//        public static Judgment
    }
}