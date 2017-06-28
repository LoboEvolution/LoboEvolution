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
package org.lobobrowser.http;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.ws.Response;

/**
 * Represents a header field in an http {@link Request} or {@link Response}.
 *
 * @author rbair
 */
public class Header extends NameValuePair {
	/** The elements. */
	private List<Element> elements = new ArrayList<Element>();

	/**
	 * Creates a new Header with a null name and value, and no elements.
	 */
	public Header() {
	}

	/**
	 * Creates a new Header with the given name and value, and no elements.
	 *
	 * @param name
	 *            The name. May be null.
	 * @param value
	 *            The value. May be null.
	 */
	public Header(String name, String value) {
		super(name, value);
	}

	/**
	 * Creates a new Header with the given name, value, and elements. If
	 * <code>elements</code> is null, then an empty set of elements will be used
	 * instead.
	 *
	 * @param name
	 *            The name. May be null.
	 * @param value
	 *            The value. May be null.
	 * @param elements
	 *            The elements. May be null. If null, then getElements() will
	 *            return an empty array, rather than null.
	 */
	public Header(String name, String value, Element... elements) {
		super(name, value);
		setElements(elements);
	}

	/**
	 * Gets the elements.
	 *
	 * @return the elements
	 */
	public Element[] getElements() {
		return elements.toArray(new Element[0]);
	}

	/**
	 * Sets the elements.
	 *
	 * @param elements
	 *            the new elements
	 */
	public void setElements(Element... elements) {
		Element[] old = getElements();
		this.elements.clear();
		if (elements != null) {
			this.elements.addAll(Arrays.asList(elements));
		}
		firePropertyChange("elements", old, getElements());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.util.NameValuePair#toString()
	 */
	@Override
	public String toString() {
		return getName() + ": " + getValue();
	}

	/**
	 * A representation of an Element within a Header.
	 */
	public static final class Element {
		/** The params. */
		private Parameter[] params = new Parameter[0];

		/**
		 * Create a new instance of Element with the given params.
		 *
		 * @param params
		 *            the Parameters. May be null.
		 */
		public Element(Parameter... params) {
			if (params == null) {
				this.params = new Parameter[0];
			} else {
				this.params = new Parameter[params.length];
				System.arraycopy(params, 0, this.params, 0, this.params.length);
			}
		}

		/**
		 * Gets the parameters.
		 *
		 * @return the parameters
		 */
		public Parameter[] getParameters() {
			Parameter[] dest = new Parameter[params.length];
			System.arraycopy(params, 0, dest, 0, params.length);
			return dest;
		}
	}
}
