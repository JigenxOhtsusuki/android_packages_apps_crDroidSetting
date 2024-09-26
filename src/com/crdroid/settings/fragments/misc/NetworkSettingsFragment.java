package com.crdroid.settings.fragments.misc;

import android.content.Context;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;
import com.android.settings.R;

public class NetworkSettingsFragment extends PreferenceFragmentCompat implements Preference.OnPreferenceChangeListener {

    private static final String KEY_CONTENT_FILTERING = "content_filtering";
    private static final String PRIVATE_DNS_MODE = "private_dns_mode";
    private static final String PRIVATE_DNS_SPECIFIER = "private_dns_specifier";

    // DNS hostname for blocking adult content
    private static final String BLOCK_ADULT_CONTENT_DNS = "adult-filter-dns.cleanbrowsing.org";

    private SwitchPreferenceCompat mContentFilteringPref;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.crdroid_settings_misc);

        mContentFilteringPref = findPreference(KEY_CONTENT_FILTERING);

        if (mContentFilteringPref != null) {
            mContentFilteringPref.setOnPreferenceChangeListener(this);
        }
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if (preference.getKey().equals(KEY_CONTENT_FILTERING)) {
            boolean enabled = (boolean) newValue;
            handleContentFilteringToggle(enabled);
            return true;
        }
        return false;
    }

    private void handleContentFilteringToggle(boolean enabled) {
        if (enabled) {
            // Enable Private DNS and set it to the provided DNS hostname
            Settings.Global.putString(getActivity().getContentResolver(), PRIVATE_DNS_MODE, "hostname");
            Settings.Global.putString(getActivity().getContentResolver(), PRIVATE_DNS_SPECIFIER, BLOCK_ADULT_CONTENT_DNS);

            // Show toast message when content filtering is turned on
            showToast(getContext(), "Good Job Master! I really appreciate guys like you");
        } else {
            // Turn off Private DNS when content filtering is disabled
            Settings.Global.putString(getActivity().getContentResolver(), PRIVATE_DNS_MODE, "off");

            // Show toast message when content filtering is turned off
            showToast(getContext(), "Enjoy Pervert! No one can save you");
        }
    }

    private void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
