package com.plumya.jurisprudenceon.app;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            return inflater.inflate(R.layout.info_fragment, container, false);
        }
    }
}
