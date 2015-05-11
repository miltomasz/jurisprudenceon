package com.plumya.jurisprudenceon.app;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.noveogroup.android.log.Logger;
import com.noveogroup.android.log.LoggerManager;
import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.plumya.jurisprudenceon.R;
import com.plumya.jurisprudenceon.fragments.AllRoomsFragment;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * Created by toml on 05.05.15.
 */
public class CourtRoomAdapter extends ParseQueryAdapter<ParseObject> {
    private static final Logger logger = LoggerManager.getLogger();
    final String ALL_JUDGEMENTS_LABEL = "all_judgements";
    private static final String CREATED_AT = "Data orzeczenia: ";
    private String[] mDataset;


    public CourtRoomAdapter(Context context, ParseQueryAdapter.QueryFactory<ParseObject> factory) {
        super(context, factory);
    }

    // Customize the layout by overriding getItemView
    @Override
    public View getItemView(ParseObject object, View view, ViewGroup parent) {
        if (view == null) {
            view = View.inflate(getContext(), R.layout.judgement_item, null);
        }

        super.getItemView(object, view, parent);

        TextView courtRoomLabel = (TextView) view.findViewById(R.id.courtroom_label);
        courtRoomLabel.setBackgroundResource(color(object));
        courtRoomLabel.setText(label(object));
        // Add the title view
        TextView signature = (TextView) view.findViewById(R.id.signature);
        signature.setText(object.getString("signature"));

        // Add a reminder of how long this item has been outstanding
        TextView judgementDate = (TextView) view.findViewById(R.id.judgement_date);
        judgementDate.setText(CREATED_AT + object.getString("judgement_date"));

        TextView issue = (TextView) view.findViewById(R.id.issue);
        issue.setText(StringUtils.abbreviate(object.getString("issue"), 100));

        return view;
    }


    private int color(ParseObject object) {
        switch(object.getString("courtRoom")) {
            case "IC" : return R.color.colorAccent;
            case "IK" : return R.color.lightRed;
            case "IPUSiSP" : return R.color.yellow;
            case "IW" : return R.color.golden;
            case "PS" : return R.color.colorAccent;
            default:return Color.WHITE;
        }
    }

    private String label(ParseObject object) {
        String[] courtRooms = getContext().getResources()
                                        .getStringArray(R.array.court_rooms_array);
        switch(object.getString("courtRoom")) {
            case "IC" : return courtRooms[1];
            case "IK" : return courtRooms[2];
            case "IPUSiSP" : return courtRooms[3];
            case "IW" : return courtRooms[4];
            case "PS" : return courtRooms[5];
            default:return courtRooms[0];
        }
    }
}