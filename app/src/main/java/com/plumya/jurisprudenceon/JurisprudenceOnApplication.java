package com.plumya.jurisprudenceon;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.noveogroup.android.log.Logger;
import com.noveogroup.android.log.LoggerManager;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.PushService;
import com.parse.SaveCallback;
import com.plumya.jurisprudenceon.app.SettingsActivity;

import bolts.Task;

/**
 * Created by toml on 24.03.15.
 */
public class JurisprudenceOnApplication extends Application {
    public static final String CHANNELS = "channels";
    private static final Logger logger = LoggerManager.getLogger();

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(this, "MQrDm7XKd6KrMwOx1m89qMNRpSDRzePGfpea1BaZ", "YEqL5U0e9mPlR2DZxph17FA42l3uhW77yiOt4eia");
        final ParseInstallation currentInstallation = ParseInstallation.getCurrentInstallation();
        Task<ParseObject> fetchedInstallation = currentInstallation.fetchIfNeededInBackground();
        if (fetchedInstallation != null && fetchedInstallation.getResult() != null) {
            if (fetchedInstallation.getResult().getObjectId() == null && fetchedInstallation.getResult().get("deviceToken") == null) {
                currentInstallation.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (firstLaunch(currentInstallation)) {
                            ParsePush.subscribeInBackground("sn");
                        }
                    }
                });
            }
        }
    }

    private boolean firstLaunch(ParseInstallation currentInstallation) {
        return currentInstallation.getList(CHANNELS) == null;
    }
}
