package com.plumya.jurisprudenceon.app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.plumya.jurisprudenceon.MainActivity;
import com.plumya.jurisprudenceon.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Tomasz Milczarek on 11/11/15.
 */
public class LegalDeclarationActivity extends AppCompatActivity implements View.OnClickListener {

    @InjectView(R.id.toolbar)
    Toolbar mToolbar;

    @InjectView(R.id.acceptanceButton)
    Button mAcceptanceButton;

    @InjectView(R.id.legalDeclarationAcceptance)
    CheckBox mAcceptanceCheckbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.legal_declaration);
        ButterKnife.inject(this);
        setSupportActionBar(mToolbar);
        mAcceptanceButton.setOnClickListener(this);
        boolean declarationAccepted = getPreferences(Context.MODE_PRIVATE)
                .getBoolean(getString(R.string.declaration_accepted), false);
        if (declarationAccepted) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.reset(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.acceptanceButton : {
                if (mAcceptanceCheckbox.isChecked()) {
                    SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean(getString(R.string.declaration_accepted), true).commit();
                    startActivity(new Intent(this, MainActivity.class));
                }
                finish();
            }
        }
    }
}