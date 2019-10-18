/*
    GNU LESSER GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 Lobo Evolution

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU Lesser General Public
    License as published by the Free Software Foundation; either
    version 2.1 of the License, or (at your option) any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
*/
/*
 * Created on Oct 9, 2005
 */
package org.loboevolution.html.dom.domimpl;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.DOMConfiguration;
import org.w3c.dom.DOMException;
import org.w3c.dom.DOMStringList;

public class DOMConfigurationImpl implements DOMConfiguration {
	private final Map<String, Object> parameters = new HashMap<String, Object>();

	public DOMConfigurationImpl() {
		super();
	}

	@Override
	public boolean canSetParameter(String name, Object value) {
		// TODO
		return true;
	}

	@Override
	public Object getParameter(String name) throws DOMException {
		synchronized (this) {
			return this.parameters.get(name);
		}
	}

	@Override
	public DOMStringList getParameterNames() {
		synchronized (this) {
			return new DOMStringListImpl(this.parameters.keySet());
		}
	}

	@Override
	public void setParameter(String name, Object value) throws DOMException {
		synchronized (this) {
			this.parameters.put(name, value);
		}
	}
}
