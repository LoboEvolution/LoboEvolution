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

import org.lobobrowser.clientlet.Clientlet;
import org.lobobrowser.clientlet.ClientletRequest;
import org.lobobrowser.clientlet.ClientletResponse;
import org.lobobrowser.clientlet.ClientletSelector;
import org.lobobrowser.main.ExtensionManager;

/**
 * The Class CoreClientletSelector.
 */
public class CoreClientletSelector implements ClientletSelector {

    /*
     * (non-Javadoc)
     * @see
     * org.lobobrowser.clientlet.ClientletSelector#select(org.lobobrowser.clientlet
     * .ClientletRequest, org.lobobrowser.clientlet.ClientletResponse)
     */
    @Override
    public Clientlet select(ClientletRequest request, ClientletResponse response) {
        return ExtensionManager.getInstance().getClientlet(request, response);
    }

    /*
     * (non-Javadoc)
     * @see
     * org.lobobrowser.clientlet.ClientletSelector#lastResortSelect(org.lobobrowser
     * .clientlet.ClientletRequest, org.lobobrowser.clientlet.ClientletResponse)
     */
    @Override
    public Clientlet lastResortSelect(ClientletRequest request,
            ClientletResponse response) {
        throw new IllegalStateException("not expected to be called");
    }
}
