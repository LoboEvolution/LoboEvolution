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

package org.loboevolution.html.dom.svgimpl;

import java.util.List;

import org.loboevolution.html.dom.smil.Time;
import org.loboevolution.html.dom.smil.TimeList;

/**
 * <p>TimeListImpl class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class TimeListImpl implements TimeList {

	private final List<Time> times;

	/**
	 * <p>Constructor for TimeListImpl.</p>
	 *
	 * @param times a {@link java.util.List} object.
	 */
	public TimeListImpl(List<Time> times) {
		this.times = times;
	}

	/** {@inheritDoc} */
	@Override
	public int getLength() {
		return this.times.size();
	}

	/** {@inheritDoc} */
	@Override
	public Time item(int index) {
		int size = this.times.size();
		if (size > index && index > -1) {
			return this.times.get(index);
		} else {
			return null;
		}
	}
}
