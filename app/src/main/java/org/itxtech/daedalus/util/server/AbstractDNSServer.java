package org.itxtech.daedalus.util.server;

/**
 * Daedalus Project
 *
 * @author iTX Technologies
 * @link https://itxtech.org
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 */
public class AbstractDNSServer {
    public static final int DNS_SERVER_DEFAULT_PORT = 53;

    protected String address;
    protected int port;

    public AbstractDNSServer(String address, int port) {
        this.address = address;
        this.port = port;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getAddress() {
        return address;
    }

    public int getPort() {
        return port;
    }

    public String getName() {
        return "";
    }

    @Override
    public String toString() {
        return getName();
    }
}
