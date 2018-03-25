package org.itxtech.daedalus.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import org.itxtech.daedalus.Liberatio;
import org.itxtech.daedalus.R;
import org.itxtech.daedalus.activity.MainActivity;
import org.itxtech.daedalus.service.DaedalusVpnService;

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
public class HomeFragment extends ToolbarFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        Button but = view.findViewById(R.id.button_activate);
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DaedalusVpnService.isActivated()) {
                    Liberatio.deactivateService(getActivity().getApplicationContext());
                } else {
                    startActivity(new Intent(getActivity(), MainActivity.class)
                            .putExtra(MainActivity.LAUNCH_ACTION, MainActivity.LAUNCH_ACTION_ACTIVATE));
                }
            }
        });

        return view;
    }

    @Override
    public void checkStatus() {
//        menu.findItem(R.id.nav_home).setChecked(true);
//        toolbar.setTitle(R.string.action_home);
        updateUserInterface();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            updateUserInterface();
        }
    }

    private void updateUserInterface() {
        Log.d("DMainFragment", "updateInterface");
        Button but = getView().findViewById(R.id.button_activate);
        if (DaedalusVpnService.isActivated()) {
            but.setText(R.string.button_text_deactivate);
        } else {
            but.setText(R.string.button_text_activate);
        }

    }
}
