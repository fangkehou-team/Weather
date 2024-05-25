package org.eu.fangkehou.weather.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.preference.CheckBoxPreference;
import androidx.preference.EditTextPreference;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceGroup;
import androidx.preference.PreferenceScreen;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import org.eu.fangkehou.weather.R;

import java.io.IOException;

public class SettingsFragment extends PreferenceFragmentCompat implements
        OnSharedPreferenceChangeListener, Preference.OnPreferenceClickListener {

    public static final String SETTING_PREF_NAME = "settings_conf";

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        getPreferenceManager().setSharedPreferencesName(SettingsFragment.SETTING_PREF_NAME);
        setPreferencesFromResource(R.xml.settings, rootKey);
        initSummaries(getPreferenceScreen());
    }

    @Override
    public void onResume() {
        super.onResume();

        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();

        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        switch (preference.getKey()) {
            case "deleteall":
                AlertDialog alertDialog = new MaterialAlertDialogBuilder(getContext())
                        .setTitle("是否清除数据？")
                        .setMessage("是否清除所有数据并关闭应用？此操作不可逆")
                        .setIcon(R.mipmap.ic_launcher)
                        .setPositiveButton("取消", (dialogInterface, i) -> {})
                        .setNegativeButton("确定清除", (dialogInterface, i) -> {
                            clearAppUserData(getContext().getPackageName());
                        })
                        .create();
                alertDialog.show();
                return true;
            case "website":
                Uri uri = Uri.parse("https://github.com/fangkehou-team/Weather");
                startActivity(new Intent(Intent.ACTION_VIEW,uri));
            case "version":
            case "author":
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Preference pref = findPreference(key);
        setSummary(pref, true);
        switch (key) {
            case "stealth":
//                // set stealth mode
//                // Run app without launcher: am start -n ru.meefik.linuxdeploy/.MainActivity
//                int stealthFlag = PrefStore.isStealth(getContext()) ?
//                        PackageManager.COMPONENT_ENABLED_STATE_DISABLED
//                        : PackageManager.COMPONENT_ENABLED_STATE_ENABLED;
//                ComponentName mainComponent = new ComponentName(getContext().getPackageName(), getContext().getPackageName() + ".Launcher");
//                getContext().getPackageManager().setComponentEnabledSetting(mainComponent, stealthFlag,
//                        PackageManager.DONT_KILL_APP);
//                break;
        }
    }

    private void initSummaries(PreferenceGroup pg) {
        for (int i = 0; i < pg.getPreferenceCount(); ++i) {
            Preference p = pg.getPreference(i);
            if (p instanceof PreferenceGroup)
                initSummaries((PreferenceGroup) p);
            else
                setSummary(p, false);
            if (p instanceof PreferenceScreen)
                p.setOnPreferenceClickListener(this);
        }
    }

    private void setSummary(Preference pref, boolean init) {
        if (pref instanceof EditTextPreference) {
            EditTextPreference editPref = (EditTextPreference) pref;
            pref.setSummary(editPref.getText());
        }

        if (pref instanceof ListPreference) {
            ListPreference listPref = (ListPreference) pref;
            pref.setSummary(listPref.getEntry());
        }

        if (pref instanceof CheckBoxPreference) {
            CheckBoxPreference checkPref = (CheckBoxPreference) pref;

            if (checkPref.getKey().equals("logger") && checkPref.isChecked() && init) {
                requestWritePermissions();
            }
        }
    }

//    private void updateEnvDialog() {
//        final Context context = getContext();
//        new AlertDialog.Builder(getContext())
//                .setTitle(R.string.title_installenv_preference)
//                .setMessage(R.string.message_installenv_confirm_dialog)
//                .setIcon(android.R.drawable.ic_dialog_alert)
//                .setCancelable(false)
//                .setPositiveButton(android.R.string.yes,
//                        (dialog, id) -> new UpdateEnvTask(context).execute())
//                .setNegativeButton(android.R.string.no,
//                        (dialog, id) -> dialog.cancel()).show();
//    }
//
//    private void removeEnvDialog() {
//        final Context context = getContext();
//        new AlertDialog.Builder(getContext())
//                .setTitle(R.string.title_removeenv_preference)
//                .setMessage(R.string.message_removeenv_confirm_dialog)
//                .setIcon(android.R.drawable.ic_dialog_alert)
//                .setCancelable(false)
//                .setPositiveButton(android.R.string.yes,
//                        (dialog, id) -> new RemoveEnvTask(context).execute())
//                .setNegativeButton(android.R.string.no,
//                        (dialog, id) -> dialog.cancel()).show();
//    }

    /**
     * Request permission for write to storage
     */
    private void requestWritePermissions() {
        int REQUEST_WRITE_STORAGE = 112;
        boolean hasPermission = (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
        if (!hasPermission) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_STORAGE);
        }
    }

    public static Process clearAppUserData(String packageName) {
        Process p = execRuntimeProcess("pm clear " + packageName);
        return p;
    }

    public static  Process execRuntimeProcess(String commond) {
        Process p = null;
        try {
            p = Runtime.getRuntime().exec(commond);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return p;
    }
}