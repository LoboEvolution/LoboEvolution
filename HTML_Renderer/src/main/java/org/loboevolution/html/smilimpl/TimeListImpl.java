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
package org.loboevolution.html.smilimpl;

import java.util.ArrayList;

import org.loboevolution.w3c.smil.Time;
import org.loboevolution.w3c.smil.TimeList;

public class TimeListImpl implements TimeList {

	private final ArrayList<Time> times;

	public TimeListImpl(ArrayList<Time> times) {
		this.times = times;
	}

	@Override
	public int getLength() {
		return this.times.size();
	}

	@Override
	public Time item(int index) {
		Time time = null;
		try {
			time = this.times.get(index);
		} catch (IndexOutOfBoundsException e) {
			// Do nothing and return null
		}
		return time;
	}
}