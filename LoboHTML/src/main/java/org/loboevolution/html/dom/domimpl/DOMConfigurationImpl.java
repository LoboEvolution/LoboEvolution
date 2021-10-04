/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2021 Lobo Evolution
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * verion 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General License for more details.
 *
 * You should have received a copy of the GNU General Public
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */
/*
 * Created on Oct 9, 2005
 */
package org.loboevolution.html.dom.domimpl;

import java.util.HashMap;
import java.util.Map;

import org.loboevolution.html.node.DOMConfiguration;

import org.loboevolution.html.node.DOMStringList;

/**
 * <p>DOMConfigurationImpl class.</p>
 *
 *
 *
 */
public class DOMConfigurationImpl implements DOMConfiguration {
	private final Map<String, Object> parameters = new HashMap<>();

	/**
	 * <p>Constructor for DOMConfigurationImpl.</p>
	 */
	public DOMConfigurationImpl() {
		super();
	}

	/** {@inheritDoc} */
	@Override
	public boolean canSetParameter(String name, Object value) {
		// TODO
		return true;
	}

	/** {@inheritDoc} */
	@Override
	public Object getParameter(String name) {
		synchronized (this) {
			return this.parameters.get(name);
		}
	}

	/** {@inheritDoc} */
	@Override
	public DOMStringList getParameterNames() {
		synchronized (this) {
			return new DOMStringListImpl(this.parameters.keySet());
		}
	}

	/** {@inheritDoc} */
	@Override
	public void setParameter(String name, Object value) {
		synchronized (this) {
			this.parameters.put(name, value);
		}
	}
}
