package com.plumya.jurisprudenceon.app;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.plumya.jurisprudenceon.BuildConfig;
import com.plumya.jurisprudenceon.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Tomasz Milczarek on 16/01/16.
 */
public class InfoActivity extends AppCompatActivity {

    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        ButterKnife.inject(this);
        setActionBar();
        getFragmentManager().beginTransaction()
                .replace(R.id.content_wrapper, new InfoFragment())
                .commit();
    }

    private void setActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
    }

    public static class InfoFragment extends Fragment {
        @InjectView(R.id.version_id)
        TextView tvVersion;

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.info_fragment, container, false);
            ButterKnife.inject(this, view);
            return view;
        }

        @Override
        public void onViewCreated(View view, Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            tvVersion.setText(BuildConfig.VERSION_CODE + "-" + BuildConfig.VERSION_NAME);
        }
    }
}
