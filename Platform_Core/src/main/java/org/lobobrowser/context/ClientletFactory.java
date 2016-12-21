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
package org.lobobrowser.context;

import java.util.LinkedList;
import java.util.List;

import org.lobobrowser.clientlet.Clientlet;
import org.lobobrowser.clientlet.ClientletRequest;
import org.lobobrowser.clientlet.ClientletResponse;
import org.lobobrowser.clientlet.ClientletSelector;
import org.lobobrowser.security.GenericLocalPermission;

/**
 * A factory for creating Clientlet objects.
 */
public class ClientletFactory {
    
	/** The instance. */
    private static ClientletFactory instance;

    /**
     * Instantiates a new clientlet factory.
     */
    private ClientletFactory() {
        this.addClientletSelector(new CoreClientletSelector());
    }

     /** Gets the instance.
	 *
	 * @return the instance
	 */
    public synchronized static ClientletFactory getInstance() {
        if (instance == null) {
        	instance = new ClientletFactory();
        }
        return instance;
    }

    /** The selectors. */
    private final List<ClientletSelector> selectors = new LinkedList<ClientletSelector>();

    /**
     * Adds the clientlet selector.
     *
     * @param selector
     *            the selector
     */
    public void addClientletSelector(ClientletSelector selector) {
        SecurityManager sm = System.getSecurityManager();
        if (sm != null) {
            sm.checkPermission(GenericLocalPermission.EXT_GENERIC);
        }
        synchronized (this) {
            this.selectors.add(0, selector);
        }
    }

    /**
     * Gets the clientlet.
     *
     * @param request
     *            the request
     * @param response
     *            the response
     * @return the clientlet
     */
    public Clientlet getClientlet(ClientletRequest request,
            ClientletResponse response) {
        synchronized (this) {
            for (ClientletSelector selector : this.selectors) {
                Clientlet clientlet = selector.select(request, response);
                if (clientlet == null) {
                    continue;
                }
                return clientlet;
            }
        }
        throw new IllegalStateException("No clientlets found for response: "
                + response + ".");
    }
}
