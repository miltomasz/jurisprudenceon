package com.plumya.jurisprudenceon;

import android.app.Application;
import android.content.Context;
import android.telephony.TelephonyManager;

import com.crashlytics.android.Crashlytics;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.SaveCallback;

import bolts.Task;
import io.fabric.sdk.android.Fabric;

/**
 * Created by toml on 24.03.15.
 */
public class JurisprudenceOnApplication extends Application {
    public static final String CHANNELS = "channels";
    public static final String JURISPRUDENCE_APP = "Orzecznictwo SN";

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());

        Parse.initialize(new Parse.Configuration.Builder(this)
//                .applicationId("MQrDm7XKd6KrMwOx1m89qMNRpSDRzePGfpea1BaZ")
                .applicationId("3jh1j321khg3k12312kj3h123kh12")
                .clientKey("YEqL5U0e9mPlR2DZxph17FA42l3uhW77yiOt4eia")
                .server("http://sno.readma.com/parse/")
                .build());

        final ParseInstallation currentInstallation = ParseInstallation.getCurrentInstallation();
        final Task<ParseObject> fetchedInstallation = currentInstallation.fetchIfNeededInBackground();
        if (fetchedInstallation != null && fetchedInstallation.getResult() != null) {
            if (fetchedInstallation.getResult().getObjectId() == null && fetchedInstallation.getResult().get("deviceToken") == null) {
                final TelephonyManager telephonyManager = (TelephonyManager) getBaseContext()
                        .getSystemService(Context.TELEPHONY_SERVICE);
                if (telephonyManager != null && telephonyManager.getDeviceId() != null) {
                    currentInstallation.put("deviceId", telephonyManager.getDeviceId());
                }
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
