/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2017 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 3 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
    

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
/*
 * Created on Jun 1, 2005
 */
package org.lobobrowser.security;

import java.net.URL;
import java.security.BasicPermission;

/**
 * Permission for restricted store access.
 */
public class StoreHostPermission extends BasicPermission {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new store host permission.
     *
     * @param name
     *            the name
     */
    public StoreHostPermission(String name) {
        super(name);
    }

    /**
     * For url.
     *
     * @param url
     *            the url
     * @return the store host permission
     */
    public static StoreHostPermission forURL(URL url) {
        if (LocalSecurityPolicy.isLocal(url)) {
            return new StoreHostPermission("*");
        } else {
            String hostName = url.getHost();
            if ((hostName != null) && (hostName.indexOf('*') != -1)) {
                throw new SecurityException("Invalid host: " + hostName);
            }
            return StoreHostPermission.forHost(hostName);
        }
    }

    /**
     * For host.
     *
     * @param hostName
     *            the host name
     * @return the store host permission
     */
    public static StoreHostPermission forHost(String hostName) {
        // TODO What about a JAR URL or a VC URL?
        String h = (hostName == null) || "".equals(hostName) ? "<<local>>"
                : hostName;
        return new StoreHostPermission(h);
    }
}
