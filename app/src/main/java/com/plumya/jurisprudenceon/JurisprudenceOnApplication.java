package com.plumya.jurisprudenceon;

import android.app.Application;

import com.noveogroup.android.log.Logger;
import com.noveogroup.android.log.LoggerManager;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.SaveCallback;

/**
 * Created by toml on 24.03.15.
 */
public class JurisprudenceOnApplication extends Application {
    private static final Logger logger = LoggerManager.getLogger();

    @Override
    public void onCreate() {
        super.onCreate();
        // Enable Local Datastore.
//        Parse.enableLocalDatastore(this);

        Parse.initialize(this, "MQrDm7XKd6KrMwOx1m89qMNRpSDRzePGfpea1BaZ", "YEqL5U0e9mPlR2DZxph17FA42l3uhW77yiOt4eia");
        ParseInstallation.getCurrentInstallation().saveInBackground();

        ParsePush.subscribeInBackground("", new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    logger.d("com.parse.push", "successfully subscribed to the broadcast channel.");
                } else {
                    logger.e("com.parse.push", "failed to subscribe for push", e);
                }
            }
        });
    }
}
