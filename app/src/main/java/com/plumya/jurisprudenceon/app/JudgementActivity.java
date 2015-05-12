package com.plumya.jurisprudenceon.app;

import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;

import com.noveogroup.android.log.Logger;
import com.noveogroup.android.log.LoggerManager;
import com.plumya.jurisprudenceon.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by toml on 12.05.15.
 */
public class JudgementActivity extends AppCompatActivity {

    private static final Logger logger = LoggerManager.getLogger();

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
        getSupportActionBar().setDisplayShowTitleEnabled(true);
    }
}
