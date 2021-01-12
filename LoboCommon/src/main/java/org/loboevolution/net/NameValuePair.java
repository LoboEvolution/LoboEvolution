/*
 *
 *     GNU GENERAL LICENSE
 *     Copyright (C) 2014 - 2021 Lobo Evolution
 *
 *     This program is free software; you can redistribute it and/or
 *     modify it under the terms of the GNU General Public
 *     License as published by the Free Software Foundation; either
 *     verion 3 of the License, or (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *     General License for more details.
 *
 *     You should have received a copy of the GNU General Public
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 *     Contact info: ivan.difrancesco@yahoo.it
 *
 */
/*
 * Created on Jun 12, 2005
 */
package org.loboevolution.net;

/**
 * The Class NameValuePair.
 *
 * @author J. H. S.
 * @version $Id: $Id
 */
public class NameValuePair extends AbstractBean implements Cloneable {
	
	/** The name. */
	public String name;
	
	/** The value. */
	public String value;

	/**
	 * Instantiates a new name value pair.
	 *
	 * @param name  the name
	 * @param value the value
	 */
	public NameValuePair(String name, String value) {
		this.name = name;
		this.value = value;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		String old = getName();
		this.name = name;
		firePropertyChange("name", old, name);
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public final String getName() {
		return name;
	}

	/**
	 * Sets the value.
	 *
	 * @param value the new value
	 */
	public void setValue(String value) {
		String old = getValue();
		this.value = value;
		firePropertyChange("value", old, value);
	}

	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public final String getValue() {
		return value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	/** {@inheritDoc} */
	@Override
	public String toString() {
		return name + "=" + value;
	}
}
