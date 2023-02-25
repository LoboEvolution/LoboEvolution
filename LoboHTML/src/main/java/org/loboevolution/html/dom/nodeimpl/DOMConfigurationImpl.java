/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2023 Lobo Evolution
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
package org.loboevolution.html.dom.nodeimpl;

import org.loboevolution.html.node.DOMConfiguration;
import org.loboevolution.html.node.DOMStringList;
import org.loboevolution.html.node.Node;

import java.util.Map;
import java.util.TreeMap;

/**
 * <p>DOMConfigurationImpl class.</p>
 */
public class DOMConfigurationImpl implements DOMConfiguration {
	private static Map<String, Object> parameters = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);


	static {

		parameters.put("namespace-declarations", true);
		parameters.put("element-content-whitespace", true);
		parameters.put("normalize-characters", false);
		parameters.put("canonical-form", false);
		parameters.put("check-character-normalization", false);
		parameters.put("validate-if-schema", false);

	}


	/**
	 * <p>Constructor for DOMConfigurationImpl.</p>
	 */
	public DOMConfigurationImpl() {

	}

	/** {@inheritDoc} */
	@Override
	public boolean canSetParameter(String name, Object value) {
		setParameter(name, value);
		return true;
	}

	/** {@inheritDoc} */
	@Override
	public Object getParameter(String name) {
		synchronized (this) {
			return parameters.get(name);
		}
	}

	/** {@inheritDoc} */
	@Override
	public DOMStringList getParameterNames() {
		synchronized (this) {
			return new DOMStringListImpl(parameters.keySet());
		}
	}

	/** {@inheritDoc} */
	@Override
	public void setParameter(String name, Object value) {
		synchronized (this) {
			parameters.put(name, value);
		}
	}
}
