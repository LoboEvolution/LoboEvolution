/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2016 Lobo Evolution

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

package org.lobobrowser.request;

import java.util.HashSet;
import java.util.Set;

/**
 * The Class VerifiedHostsStore.
 */
public class VerifiedHostsStore {

    /** The Constant instance. */
    private static final VerifiedHostsStore instance = new VerifiedHostsStore();

    /** The hosts. */
    private final Set hosts = new HashSet();

    /** Gets the Constant instance.
	 *
	 * @return the Constant instance
	 */
    public static VerifiedHostsStore getInstance() {
        return instance;
    }

    /**
     * Instantiates a new verified hosts store.
     */
    public VerifiedHostsStore() {
        super();
    }

    /**
     * Contains.
     *
     * @param host
     *            the host
     * @return true, if successful
     */
    public boolean contains(String host) {
        synchronized (this.hosts) {
            return this.hosts.contains(host);
        }
    }

    /**
     * Adds the.
     *
     * @param host
     *            the host
     */
    public void add(String host) {
        synchronized (this.hosts) {
            this.hosts.add(host);
        }
    }
}
