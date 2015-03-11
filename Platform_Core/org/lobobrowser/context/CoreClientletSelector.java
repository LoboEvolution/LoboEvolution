/*
    GNU GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2015 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 2 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General Public License for more details.

    You should have received a copy of the GNU General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

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
	
	/* (non-Javadoc)
	 * @see org.lobobrowser.clientlet.ClientletSelector#select(org.lobobrowser.clientlet.ClientletRequest, org.lobobrowser.clientlet.ClientletResponse)
	 */
	public Clientlet select(ClientletRequest request, ClientletResponse response) {
		return ExtensionManager.getInstance().getClientlet(request, response);
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.clientlet.ClientletSelector#lastResortSelect(org.lobobrowser.clientlet.ClientletRequest, org.lobobrowser.clientlet.ClientletResponse)
	 */
	public Clientlet lastResortSelect(ClientletRequest request,
			ClientletResponse response) {
		throw new IllegalStateException("not expected to be called");
	}
}
