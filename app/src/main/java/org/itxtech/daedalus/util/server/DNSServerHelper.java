package org.itxtech.daedalus.util.server;

import android.content.Context;
import org.itxtech.daedalus.Liberatio;
import org.itxtech.daedalus.service.DaedalusVpnService;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;

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
public class DNSServerHelper {
    private static HashMap<String, Integer> portCache = null;

    public static void clearPortCache() {
        portCache = null;
    }

    public static void buildPortCache() {
        portCache = new HashMap<>();
        for (DNSServer server : Liberatio.DNS_SERVERS) {
            portCache.put(server.getAddress(), server.getPort());
        }

        for (CustomDNSServer server : Liberatio.configurations.getCustomDNSServers()) {
            portCache.put(server.getAddress(), server.getPort());
        }

    }

    public static int getPortOrDefault(InetAddress address, int defaultPort) {
        String hostAddress = address.getHostAddress();

        if (portCache.containsKey(hostAddress)) {
            return portCache.get(hostAddress);
        }

        return defaultPort;
    }

    public static int getPosition(String id) {
        int intId = Integer.parseInt(id);
        if (intId < Liberatio.DNS_SERVERS.size()) {
            return intId;
        }

        for (int i = 0; i < Liberatio.configurations.getCustomDNSServers().size(); i++) {
            if (Liberatio.configurations.getCustomDNSServers().get(i).getId().equals(id)) {
                return i + Liberatio.DNS_SERVERS.size();
            }
        }
        return 0;
    }

    public static String getPrimary() {
        return String.valueOf(DNSServerHelper.checkServerId(Integer.parseInt(Liberatio.getPrefs().getString("primary_server", "0"))));
    }

    public static String getSecondary() {
        return String.valueOf(DNSServerHelper.checkServerId(Integer.parseInt(Liberatio.getPrefs().getString("secondary_server", "1"))));
    }

    private static int checkServerId(int id) {
        if (id < Liberatio.DNS_SERVERS.size()) {
            return id;
        }
        for (CustomDNSServer server : Liberatio.configurations.getCustomDNSServers()) {
            if (server.getId().equals(String.valueOf(id))) {
                return id;
            }
        }
        return 0;
    }

    public static String getAddressById(String id) {
        for (DNSServer server : Liberatio.DNS_SERVERS) {
            if (server.getId().equals(id)) {
                return server.getAddress();
            }
        }
        for (CustomDNSServer customDNSServer : Liberatio.configurations.getCustomDNSServers()) {
            if (customDNSServer.getId().equals(id)) {
                return customDNSServer.getAddress();
            }
        }
        return Liberatio.DNS_SERVERS.get(0).getAddress();
    }

    public static String[] getIds() {
        ArrayList<String> servers = new ArrayList<>(Liberatio.DNS_SERVERS.size());
        for (DNSServer server : Liberatio.DNS_SERVERS) {
            servers.add(server.getId());
        }
        for (CustomDNSServer customDNSServer : Liberatio.configurations.getCustomDNSServers()) {
            servers.add(customDNSServer.getId());
        }
        String[] stringServers = new String[Liberatio.DNS_SERVERS.size()];
        return servers.toArray(stringServers);
    }

    public static String[] getNames(Context context) {
        ArrayList<String> servers = new ArrayList<>(Liberatio.DNS_SERVERS.size());
        for (DNSServer server : Liberatio.DNS_SERVERS) {
            servers.add(server.getStringDescription(context));
        }
        for (CustomDNSServer customDNSServer : Liberatio.configurations.getCustomDNSServers()) {
            servers.add(customDNSServer.getName());
        }
        String[] stringServers = new String[Liberatio.DNS_SERVERS.size()];
        return servers.toArray(stringServers);
    }

    public static ArrayList<AbstractDNSServer> getAllServers() {
        ArrayList<AbstractDNSServer> servers = new ArrayList<>(Liberatio.DNS_SERVERS.size());
        servers.addAll(Liberatio.DNS_SERVERS);
        servers.addAll(Liberatio.configurations.getCustomDNSServers());
        return servers;
    }

    public static String getDescription(String id, Context context) {
        for (DNSServer server : Liberatio.DNS_SERVERS) {
            if (server.getId().equals(id)) {
                return server.getStringDescription(context);
            }
        }
        for (CustomDNSServer customDNSServer : Liberatio.configurations.getCustomDNSServers()) {
            if (customDNSServer.getId().equals(id)) {
                return customDNSServer.getName();
            }
        }
        return Liberatio.DNS_SERVERS.get(0).getStringDescription(context);
    }

    public static boolean isInUsing(CustomDNSServer server) {
        return DaedalusVpnService.isActivated() && (server.getId().equals(getPrimary()) || server.getId().equals(getSecondary()));
    }
}
