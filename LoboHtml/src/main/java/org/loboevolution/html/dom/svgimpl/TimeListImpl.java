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
package org.loboevolution.html.dom.svgimpl;

import java.util.List;

import org.loboevolution.html.dom.smil.Time;
import org.loboevolution.html.dom.smil.TimeList;

public class TimeListImpl implements TimeList {

	private final List<Time> times;

	public TimeListImpl(List<Time> times) {
		this.times = times;
	}

	@Override
	public int getLength() {
		return this.times.size();
	}

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