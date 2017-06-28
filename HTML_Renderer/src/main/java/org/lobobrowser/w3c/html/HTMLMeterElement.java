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

package org.lobobrowser.w3c.html;

import org.w3c.dom.NodeList;

/**
 * The Interface HTMLMeterElement.
 */
public interface HTMLMeterElement extends HTMLElement {

	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	// HTMLMeterElement
	public double getValue();

	/**
	 * Sets the value.
	 *
	 * @param value
	 *            the new value
	 */
	public void setValue(double value);

	/**
	 * Gets the min.
	 *
	 * @return the min
	 */
	public double getMin();

	/**
	 * Sets the min.
	 *
	 * @param min
	 *            the new min
	 */
	public void setMin(double min);

	/**
	 * Gets the max.
	 *
	 * @return the max
	 */
	public double getMax();

	/**
	 * Sets the max.
	 *
	 * @param max
	 *            the new max
	 */
	public void setMax(double max);

	/**
	 * Gets the low.
	 *
	 * @return the low
	 */
	public double getLow();

	/**
	 * Sets the low.
	 *
	 * @param low
	 *            the new low
	 */
	public void setLow(double low);

	/**
	 * Gets the high.
	 *
	 * @return the high
	 */
	public double getHigh();

	/**
	 * Sets the high.
	 *
	 * @param high
	 *            the new high
	 */
	public void setHigh(double high);

	/**
	 * Gets the optimum.
	 *
	 * @return the optimum
	 */
	public double getOptimum();

	/**
	 * Sets the optimum.
	 *
	 * @param optimum
	 *            the new optimum
	 */
	public void setOptimum(double optimum);

	/**
	 * Gets the labels.
	 *
	 * @return the labels
	 */
	public NodeList getLabels();
}
