package com.crdroid.settings.fragments.misc;

import android.os.Bundle;
import android.provider.Settings;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;

import com.android.settings.R;

public class NetworkSettingsFragment extends PreferenceFragmentCompat {

    private SwitchPreferenceCompat mPrivateDnsToggle;

    // Key for the private DNS toggle in the settings
    private static final String PRIVATE_DNS_KEY = "private_dns_toggle";
    // The custom DNS to be set when the toggle is enabled
    private static final String CUSTOM_DNS = "adult-filter-dns.cleanbrowsing.org";

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        // Load the preferences from an XML resource
        setPreferencesFromResource(R.xml.crdroid_settings_misc, rootKey);

        // Find the private DNS toggle preference
        mPrivateDnsToggle = findPreference(PRIVATE_DNS_KEY);
        if (mPrivateDnsToggle != null) {
            // Set the default state of the toggle to enabled
            mPrivateDnsToggle.setChecked(true); // Default to enabled

            // Check current private DNS setting and set toggle accordingly
            updateToggleState();

            // Listener for changes in the toggle state
            mPrivateDnsToggle.setOnPreferenceChangeListener((preference, newValue) -> {
                boolean isEnabled = (Boolean) newValue;
                setPrivateDns(isEnabled); // Set the private DNS based on the toggle state
                return true; // Return true to update the state
            });
        }
    }

    private void updateToggleState() {
        // Retrieve the current private DNS mode
        String privateDnsMode = Settings.Global.getString(getContext().getContentResolver(), "private_dns_mode");
        // Determine if the custom DNS is currently enabled
        boolean isCustomDnsEnabled = "hostname".equals(privateDnsMode);
        // Update the toggle state accordingly
        mPrivateDnsToggle.setChecked(isCustomDnsEnabled);
    }

    private void setPrivateDns(boolean enable) {
        try {
            if (enable) {
                // Set custom private DNS when enabled
                Settings.Global.putString(getContext().getContentResolver(), "private_dns_mode", "hostname");
                Settings.Global.putString(getContext().getContentResolver(), "private_dns_specifier", CUSTOM_DNS);
            } else {
                // Reset to default private DNS when disabled
                Settings.Global.putString(getContext().getContentResolver(), "private_dns_mode", "off");
            }
        } catch (Exception e) {
            e.printStackTrace(); // Log the exception (consider more robust error handling)
        }
    }
}
