package org.itxtech.daedalus.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import org.itxtech.daedalus.Liberatio;
import org.itxtech.daedalus.R;
import org.itxtech.daedalus.activity.ConfigActivity;
import org.itxtech.daedalus.util.server.CustomDNSServer;
import org.itxtech.daedalus.util.server.DNSServer;

/**
 * Liberatio Project
 *
 * @author iTX Technologies
 * @link https://itxtech.org
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 */
public class DNSServerConfigFragment extends ConfigFragment {
    private int index;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.perf_server);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        EditTextPreference serverName = (EditTextPreference) findPreference("serverName");
        serverName.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                preference.setSummary((String) newValue);
                return true;
            }
        });

        EditTextPreference serverAddress = (EditTextPreference) findPreference("serverAddress");
        serverAddress.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                preference.setSummary((String) newValue);
                return true;
            }
        });

        EditTextPreference serverPort = (EditTextPreference) findPreference("serverPort");
        serverPort.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                preference.setSummary((String) newValue);
                return true;
            }
        });


        index = intent.getIntExtra(ConfigActivity.LAUNCH_ACTION_ID, ConfigActivity.ID_NONE);
        if (index != ConfigActivity.ID_NONE) {
            CustomDNSServer server = Liberatio.configurations.getCustomDNSServers().get(index);
            serverName.setText(server.getName());
            serverName.setSummary(server.getName());
            serverAddress.setText(server.getAddress());
            serverAddress.setSummary(server.getAddress());
            serverPort.setText(String.valueOf(server.getPort()));
            serverPort.setSummary(String.valueOf(server.getPort()));
        } else {
            serverName.setText("");
            serverAddress.setText("");
            String port = String.valueOf(DNSServer.DNS_SERVER_DEFAULT_PORT);
            serverPort.setText(port);
            serverPort.setSummary(port);
        }
        return view;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_apply:
                String serverName = ((EditTextPreference) findPreference("serverName")).getText();
                String serverAddress = ((EditTextPreference) findPreference("serverAddress")).getText();
                String serverPort = ((EditTextPreference) findPreference("serverPort")).getText();

                if (serverName.equals("") | serverAddress.equals("") | serverPort.equals("")) {
                    Snackbar.make(getView(), R.string.notice_fill_in_all, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    break;
                }

                if (index == ConfigActivity.ID_NONE) {
                    Liberatio.configurations.getCustomDNSServers().add(new CustomDNSServer(serverName, serverAddress, Integer.parseInt(serverPort)));
                } else {
                    CustomDNSServer server = Liberatio.configurations.getCustomDNSServers().get(index);
                    server.setName(serverName);
                    server.setAddress(serverAddress);
                    server.setPort(Integer.parseInt(serverPort));
                }
                Liberatio.setRulesChanged();
                getActivity().finish();
                break;
            case R.id.action_delete:
                if (index != ConfigActivity.ID_NONE) {
                    new AlertDialog.Builder(getActivity())
                            .setTitle(R.string.notice_delete_confirm_prompt)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Liberatio.configurations.getCustomDNSServers().remove(index);
                                    getActivity().finish();
                                }
                            })
                            .setNegativeButton(android.R.string.no, null)
                            .create()
                            .show();
                } else {
                    Liberatio.setRulesChanged();
                    getActivity().finish();
                }
                break;
        }

        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Liberatio.configurations.save();
    }
}
