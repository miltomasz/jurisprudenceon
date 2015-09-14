package com.plumya.jurisprudenceon.app;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.plumya.jurisprudenceon.R;
import com.plumya.jurisprudenceon.model.Judgement;
import com.plumya.jurisprudenceon.utils.DownloadPdfTask;

import butterknife.ButterKnife;
import butterknife.InjectView;
import org.apache.commons.lang3.StringUtils;

import java.io.File;

import static com.plumya.jurisprudenceon.utils.JurisprudenceOnUtils.color;
import static com.plumya.jurisprudenceon.utils.JurisprudenceOnUtils.label;


/**
 * Created by toml on 12.05.15.
 */
public class JudgementActivity extends AppCompatActivity {

    public static final String JUDGEMENT_DATA = "judgement_data";
    private static final String TAG = JudgementActivity.class.getSimpleName();

    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_judgement);
        ButterKnife.inject(this);
        setSupportActionBar(toolbar);
        Log.v(TAG, "MainActivity created");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
//        getSupportActionBar().setDisplayShowTitleEnabled(true);

        Judgement judgement = (Judgement) getIntent().getSerializableExtra(JUDGEMENT_DATA);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.content_frame, PlaceholderFragment.newInstance(judgement)).commit();
        }
    }

    public static class PlaceholderFragment extends Fragment implements View.OnClickListener{

        private Judgement judgement;

        @InjectView(R.id.signature)
        TextView signatureTv;

        @InjectView(R.id.judgement_date)
        TextView judgementDateTv;

        @InjectView(R.id.bench)
        TextView benchTv;

        @InjectView(R.id.issue)
        TextView issueTv;

        @InjectView(R.id.decision_init)
        TextView decisionInitTv;

        @InjectView(R.id.decision)
        TextView decisionTv;

        @InjectView(R.id.justification)
        TextView justificationTv;

        @InjectView(R.id.courtroom_label)
        TextView courtroomLabelTv;

        @InjectView(R.id.rule)
        TextView ruleTv;

        @InjectView(R.id.attachement)
        TextView attachementTv;

        @InjectView(R.id.attachementBtn)
        Button attachementBtn;

        String judgementUrl;

        public static PlaceholderFragment newInstance(Judgement judgement) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putSerializable(JUDGEMENT_DATA, judgement);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public void onClick(View view) {
            switch(view.getId()) {
                case R.id.attachementBtn: {
                    String fileUrl = String.format("http://www.sn.pl%s", judgementUrl);
                    new DownloadPdfTask(getActivity(), fileUrl).execute();
                    break;
                }
                default: break;
            }
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
            attachementBtn.setOnClickListener(this);
            courtroomLabelTv.setBackgroundResource(color(judgement.courtRoom));
            courtroomLabelTv.setText(label(getActivity(), judgement.courtRoom));
            signatureTv.setText(judgement.signature);
            judgementDateTv.setText(judgement.judgementDate);
            benchTv.setText(judgement.bench);
            issueTv.setText(judgement.issue);
            decisionInitTv.setText(judgement.decisionInit);
            decisionTv.setText(Html.fromHtml(judgement.decision));
            addToView(justificationTv, judgement.justification);
            addToView(ruleTv, judgement.rule);
            addToView(attachementTv, judgement.attachement);
            if (StringUtils.isNotEmpty(judgement.justification)) {
                this.judgementUrl = judgement.justification;
                attachementBtn.setVisibility(View.VISIBLE);
            }
            return rootView;
        }

        @Override
        public void onDestroyView() {
            super.onDestroyView();
            ButterKnife.reset(this);
        }

        private void addToView(TextView tv, String judgementProperty) {
            if (StringUtils.isNotBlank(judgementProperty)) {
                tv.setText(judgementProperty);
                tv.setVisibility(View.VISIBLE);
            }
        }
    }
}
