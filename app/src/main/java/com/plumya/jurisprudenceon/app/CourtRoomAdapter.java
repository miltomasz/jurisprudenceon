package com.plumya.jurisprudenceon.app;

import android.content.Context;
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
}