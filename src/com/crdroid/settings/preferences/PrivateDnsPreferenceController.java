package com.crdroid.settings.preferences;

import android.content.Context;
import android.provider.Settings;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

public class PrivateDnsPreferenceController extends Preference {

    private static final String PRIVATE_DNS_KEY = "private_dns";
    private static final String CONTENT_FILTERING_KEY = "content_filtering";

    public PrivateDnsPreferenceController(Context context) {
        super(context);
    }

    public void displayPreference(PreferenceScreen screen) {
        Preference privateDnsPref = screen.findPreference(PRIVATE_DNS_KEY);
        boolean isContentFilteringEnabled = Settings.Global.getInt(
            getContext().getContentResolver(), CONTENT_FILTERING_KEY, 0) == 1;

        // Disable Private DNS if content filtering is enabled
        if (privateDnsPref != null) {
            privateDnsPref.setEnabled(!isContentFilteringEnabled);
        }
    }
}
