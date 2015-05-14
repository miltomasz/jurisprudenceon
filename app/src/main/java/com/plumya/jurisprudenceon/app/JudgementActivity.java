package com.plumya.jurisprudenceon.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.noveogroup.android.log.Logger;
import com.noveogroup.android.log.LoggerManager;
import com.plumya.jurisprudenceon.R;
import com.plumya.jurisprudenceon.model.Judgement;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by toml on 12.05.15.
 */
public class JudgementActivity extends AppCompatActivity {

    private static final Logger logger = LoggerManager.getLogger();
    public static final String JUDGEMENT_DATA = "judgement_data";

    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_judgement);
        ButterKnife.inject(this);
        setSupportActionBar(toolbar);
        logger.d("MainActivity created");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
//        getSupportActionBar().setDisplayShowTitleEnabled(true);

        Judgement judgement = (Judgement) getIntent().getSerializableExtra(JUDGEMENT_DATA);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.content_frame, PlaceholderFragment.newInstance(judgement)).commit();
        }
    }

    public static class PlaceholderFragment extends Fragment {

        private Judgement judgement;

        @InjectView(R.id.signature)
        TextView signatureTv;

        @InjectView(R.id.judgement_date)
        TextView judgementDateTv;

        @InjectView(R.id.bench)
        TextView benchTv;

        @InjectView(R.id.issue)
        TextView issueTv;

        @InjectView(R.id.decision)
        TextView decisionTv;

        @InjectView(R.id.justification)
        TextView justificationTv;

        public static PlaceholderFragment newInstance(Judgement judgement) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putSerializable(JUDGEMENT_DATA, judgement);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            judgement = (Judgement) getArguments().getSerializable(JUDGEMENT_DATA);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.judgement_fragment, container, false);
            ButterKnife.inject(this, rootView);
            signatureTv.setText(judgement.signature);
            judgementDateTv.setText(judgement.judgementDate);
            benchTv.setText(judgement.bench);
            issueTv.setText(judgement.issue);
            decisionTv.setText(judgement.decision);
            justificationTv.setText(judgement.justification);
            return rootView;
        }

        @Override
        public void onDestroyView() {
            super.onDestroyView();
            ButterKnife.reset(this);
        }
    }
}
