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

import org.loboevolution.clientlet.ClientletContext;
import org.loboevolution.clientlet.ClientletThreadGroup;

/**
 * The Class ClientletThreadGroupImpl.
 */
public class ClientletThreadGroupImpl extends ThreadGroup implements ClientletThreadGroup {

	/** The context. */
	private final ClientletContext context;

	/**
	 * Instantiates a new clientlet thread group impl.
	 *
	 * @param name
	 *            the name
	 * @param context
	 *            the context
	 */
	public ClientletThreadGroupImpl(String name, ClientletContext context) {
		super(name);
		this.context = context;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.clientlet.ClientletThreadGroup#getClientletContext()
	 */
	@Override
	public ClientletContext getClientletContext() {
		return this.context;
	}
}
