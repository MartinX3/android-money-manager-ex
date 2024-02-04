/*
 * Copyright (C) 2012-2018 The Android Money Manager Ex Project Team
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.money.manager.ex.settings;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceScreen;

import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.mmex_icon_font_typeface_library.MMXIconFont;
import com.money.manager.ex.Constants;
import com.money.manager.ex.R;
import com.money.manager.ex.about.AboutActivity;
import com.money.manager.ex.core.UIHelper;

import timber.log.Timber;

/**
 * Root preferences fragment.
 */
public class SettingsFragment extends PreferenceFragmentCompat {
    public static final int REQUEST_GENERAL_PREFERENCES = 1;

    private UIHelper uiHelper;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        initGeneralSettings();

        // Per-Database preferences
        final Preference perDbPreference = findPreference(getString(R.string.pref_per_database));
        if (null != perDbPreference) {
            perDbPreference.setIcon(uiHelper.getIcon(GoogleMaterial.Icon.gmd_settings_applications)
                    .color(uiHelper.getSecondaryTextColor()));

            perDbPreference.setOnPreferenceClickListener(p -> {
                final Intent intent = new Intent(getActivity(), SettingsActivity.class);
                intent.putExtra(SettingsActivity.EXTRA_FRAGMENT, PerDatabaseFragment.class.getSimpleName());
                startActivity(intent);
                return true;
            });
        }

        final Preference lookAndFeelPreference = findPreference(getString(PreferenceConstants.PREF_LOOK_FEEL));
        if (null != lookAndFeelPreference) {
            lookAndFeelPreference.setIcon(uiHelper.getIcon(GoogleMaterial.Icon.gmd_wallpaper)
                    .color(uiHelper.getSecondaryTextColor()));
            lookAndFeelPreference.setOnPreferenceClickListener(p -> {
                startActivity(new Intent(getActivity(), LookFeelSettingsActivity.class));
                return true;
            });
        }

        final Preference behaviourPreference = findPreference(getString(R.string.pref_behaviour));
        if (null != behaviourPreference) {
            behaviourPreference.setIcon(uiHelper.getIcon(GoogleMaterial.Icon.gmd_play_circle_outline)
                    .color(uiHelper.getSecondaryTextColor()));
            behaviourPreference.setOnPreferenceClickListener(p -> {
                startActivity(new Intent(getActivity(), BehaviourSettingsActivity.class));
                return true;
            });
        }

        final Preference investmentPreference = findPreference(getString(R.string.pref_investment));
        if (null != investmentPreference) {
            investmentPreference.setIcon(uiHelper.getIcon(GoogleMaterial.Icon.gmd_trending_up)
                    .color(uiHelper.getSecondaryTextColor()));
            investmentPreference.setOnPreferenceClickListener(p -> {
                startActivity(new Intent(getActivity(), InvestmentSettingsActivity.class));
                return true;
            });
        }

        final Preference budgetPreference = findPreference(getString(R.string.pref_budget));
        if (null != budgetPreference) {
            budgetPreference.setIcon(uiHelper.getIcon(MMXIconFont.Icon.mmx_law)
                    .color(uiHelper.getSecondaryTextColor()));
            budgetPreference.setOnPreferenceClickListener(p -> {
                startActivity(new Intent(getActivity(), BudgetSettingsActivity.class));
                return true;
            });
        }

        final Preference passcodePreference = findPreference(getString(PreferenceConstants.PREF_SECURITY));
        if (null != passcodePreference) {
            passcodePreference.setIcon(uiHelper.getIcon(GoogleMaterial.Icon.gmd_lock)
                    .color(uiHelper.getSecondaryTextColor()));
            passcodePreference.setOnPreferenceClickListener(p -> {
                startActivity(new Intent(getActivity(), SecuritySettingsActivity.class));
                return true;
            });
        }

        final Preference databasesPreference = findPreference(getString(PreferenceConstants.PREF_DATABASE));
        if (null != databasesPreference) {
            databasesPreference.setIcon(uiHelper.getIcon(GoogleMaterial.Icon.gmd_storage)
                    .color(uiHelper.getSecondaryTextColor()));
            databasesPreference.setOnPreferenceClickListener(p -> {
                startActivity(new Intent(getActivity(), DatabaseSettingsActivity.class));
                return true;
            });
        }

        // Synchronisation
        final Preference syncPreference = findPreference(getString(R.string.pref_synchronization));
        if (null != syncPreference) {
            syncPreference.setIcon(uiHelper.getIcon(GoogleMaterial.Icon.gmd_sync)
                    .color(uiHelper.getSecondaryTextColor()));
            syncPreference.setOnPreferenceClickListener(p -> {
                startActivity(new Intent(getActivity(), SyncPreferencesActivity.class));
                return true;
            });
        }

        final Preference infoPreference = findPreference(getString(PreferenceConstants.PREF_VERSION_NAME));
        if (null != infoPreference) {
            infoPreference.setIcon(uiHelper.getIcon(GoogleMaterial.Icon.gmd_info_outline)
                    .color(uiHelper.getSecondaryTextColor()));
            infoPreference.setOnPreferenceClickListener(p -> {
                startActivity(new Intent(getActivity(), AboutActivity.class));
                return true;
            });
        }

        // manage intent
        if (null != getActivity().getIntent()) {
            if (!TextUtils.isEmpty(getActivity().getIntent()
                    .getStringExtra(Constants.INTENT_REQUEST_PREFERENCES_SCREEN))) {
                try {
                    final PreferenceScreen screen = getPreferenceScreen();
                    final Preference preference = findPreference(getActivity().getIntent()
                            .getStringExtra(Constants.INTENT_REQUEST_PREFERENCES_SCREEN));
                    if (null != preference) {
                        //screen.onItemClick(null, null, preference.getOrder(), 0);
                        screen.performClick();
                    }
                } catch (final Exception e) {
                    Timber.e(e, "opening preferences screen");
                }
            }
        }
    }

    @Override
    public void onCreatePreferences(final Bundle savedInstanceState, final String rootKey) {
        // Timber.d("creating preferences");
    }

    @Override
    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (REQUEST_GENERAL_PREFERENCES == requestCode) {// always recreate activity when returning from general preferences, instead of
            // trying to figure out if something has changed.
            getActivity().recreate();
        }
    }

    private UIHelper getUiHelper() {
        if (null == uiHelper) {
            uiHelper = new UIHelper(getActivity());
        }
        return uiHelper;
    }

    private void initGeneralSettings() {
        // General Settings

        final Preference generalPreference = findPreference(getString(PreferenceConstants.PREF_GENERAL));
        if (null == generalPreference) return;

        generalPreference.setIcon(getUiHelper().getIcon(GoogleMaterial.Icon.gmd_build)
                .color(uiHelper.getSecondaryTextColor()));

        generalPreference.setOnPreferenceClickListener(p -> {
            final Intent intent = new Intent(getActivity(), GeneralSettingsActivity.class);
            startActivityForResult(intent, REQUEST_GENERAL_PREFERENCES);
            return true;
        });
    }
}
