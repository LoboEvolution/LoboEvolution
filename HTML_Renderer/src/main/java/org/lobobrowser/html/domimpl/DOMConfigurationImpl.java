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
 * Created on Oct 9, 2005
 */
package org.lobobrowser.html.domimpl;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.DOMConfiguration;
import org.w3c.dom.DOMException;
import org.w3c.dom.DOMStringList;

/**
 * The Class DOMConfigurationImpl.
 */
public class DOMConfigurationImpl implements DOMConfiguration {

	/** The parameters. */
	private final Map<String, Object> parameters = new HashMap<String, Object>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.DOMConfiguration#setParameter(java.lang.String,
	 * java.lang.Object)
	 */
	@Override
	public void setParameter(String name, Object value) throws DOMException {
		synchronized (this) {
			this.parameters.put(name, value);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.DOMConfiguration#getParameter(java.lang.String)
	 */
	@Override
	public Object getParameter(String name) throws DOMException {
		synchronized (this) {
			return this.parameters.get(name);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.DOMConfiguration#canSetParameter(java.lang.String,
	 * java.lang.Object)
	 */
	@Override
	public boolean canSetParameter(String name, Object value) {
		// TODO
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.DOMConfiguration#getParameterNames()
	 */
	@Override
	public DOMStringList getParameterNames() {
		synchronized (this) {
			return new DOMStringListImpl(parameters.values());
		}
	}
}
