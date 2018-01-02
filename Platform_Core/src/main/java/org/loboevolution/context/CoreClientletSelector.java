/*
    GNU GENERAL LICENSE
    Copyright (C) 2014 - 2018 Lobo Evolution

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
    

    Contact info: ivan.difrancesco@yahoo.it
 */
package org.loboevolution.context;

import org.loboevolution.clientlet.Clientlet;
import org.loboevolution.clientlet.ClientletRequest;
import org.loboevolution.clientlet.ClientletResponse;
import org.loboevolution.clientlet.ClientletSelector;
import org.loboevolution.main.ExtensionManager;

/**
 * The Class CoreClientletSelector.
 */
public class CoreClientletSelector implements ClientletSelector {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.clientlet.ClientletSelector#select(org.loboevolution.
	 * clientlet .ClientletRequest, org.loboevolution.clientlet.ClientletResponse)
	 */
	@Override
	public Clientlet select(ClientletRequest request, ClientletResponse response) {
		return ExtensionManager.getInstance().getClientlet(request, response);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.clientlet.ClientletSelector#lastResortSelect(org.
	 * loboevolution .clientlet.ClientletRequest,
	 * org.loboevolution.clientlet.ClientletResponse)
	 */
	@Override
	public Clientlet lastResortSelect(ClientletRequest request, ClientletResponse response) {
		throw new IllegalStateException("not expected to be called");
	}
}
