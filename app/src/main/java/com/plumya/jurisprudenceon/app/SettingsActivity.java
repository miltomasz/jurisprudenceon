package com.plumya.jurisprudenceon.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.SwitchPreference;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.PushService;
import com.parse.SaveCallback;
import com.plumya.jurisprudenceon.JurisprudenceOnApplication;
import com.plumya.jurisprudenceon.R;
import com.plumya.jurisprudenceon.utils.Utility;

import java.util.prefs.PreferenceChangeListener;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by miltomasz on 23/09/15.
 */
public class SettingsActivity extends AppCompatActivity {

    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.inject(this);
        setActionBar();
        getFragmentManager().beginTransaction()
                .replace(R.id.content_wrapper, new SettingsFragment())
                .commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.reset(this);
    }

    private void setActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
    }

    public static class SettingsFragment extends PreferenceFragment
                                    implements Preference.OnPreferenceChangeListener {

        public static final String NOTIFICATION_TOGGLE_KEY = "notificationToggle";
        public static final String SN = "sn";
        private final String TAG = SettingsFragment.class.getSimpleName();
        private SharedPreferences mPrefs;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);
            Preference notificationTogglePref = findPreference(NOTIFICATION_TOGGLE_KEY);
            notificationTogglePref.setOnPreferenceChangeListener(this);
            mPrefs = getActivity().getSharedPreferences(getString(R.string.preference_name),
                                                                            Context.MODE_PRIVATE);
        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            final SharedPreferences.Editor editor = mPrefs.edit();
            if (NOTIFICATION_TOGGLE_KEY.equals(preference.getKey())) {
                final Boolean prefValue = (Boolean) newValue;
                String action = prefValue ? "włączyć" : "wyłączyć";
                if (!Utility.isNetworkConnected(getActivity())) {
                    Toast.makeText(getActivity(),
                            "Nie można " + action + " powiadomień. Proszę sprawdzić połączenie z internetem!",
                            Toast.LENGTH_LONG).show();
                    return false;
                }
                final String objectId = ParseInstallation.getCurrentInstallation().getObjectId();
                if (prefValue) {
                    ParsePush.subscribeInBackground(SN, new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                editor.putBoolean(NOTIFICATION_TOGGLE_KEY, prefValue).commit();
                            } else {
                                Crashlytics.log(Log.ERROR, JurisprudenceOnApplication.JURISPRUDENCE_APP,
                                        "Exception while subscribing in for user: " + objectId + ", exception: " + e.getMessage());
                                Toast.makeText(getActivity(),
                                        getString(R.string.check_internet_connection), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                } else {
                    ParsePush.unsubscribeInBackground(SN, new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                editor.putBoolean(NOTIFICATION_TOGGLE_KEY, prefValue).commit();
                            } else {
                                Crashlytics.log(Log.ERROR, JurisprudenceOnApplication.JURISPRUDENCE_APP,
                                        "Exception while unsubscribing for user: " + objectId + ", exception: " + e.getMessage());
                                Toast.makeText(getActivity(),
                                        getString(R.string.check_internet_connection), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
                Log.v(TAG, "Enable notification? " + prefValue);
            }
            return true;
        }
    }
}
