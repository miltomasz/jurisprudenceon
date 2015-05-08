package com.plumya.jurisprudenceon;

import android.app.Application;

import com.parse.Parse;

/**
 * Created by toml on 24.03.15.
 */
public class JurisprudenceOnApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // Enable Local Datastore.
//        Parse.enableLocalDatastore(this);

        Parse.initialize(this, "MQrDm7XKd6KrMwOx1m89qMNRpSDRzePGfpea1BaZ", "YEqL5U0e9mPlR2DZxph17FA42l3uhW77yiOt4eia");

    }
}
