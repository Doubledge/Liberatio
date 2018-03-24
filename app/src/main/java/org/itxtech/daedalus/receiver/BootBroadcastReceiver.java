package org.itxtech.daedalus.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import org.itxtech.daedalus.Liberatio;
import org.itxtech.daedalus.util.Logger;

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
public class BootBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Liberatio.getPrefs().getBoolean("settings_boot", false)) {
            Liberatio.activateService(context);
            Logger.info("Triggered boot receiver");
        }
    }
}
