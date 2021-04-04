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

package org.loboevolution.html.js.geolocation;

import org.loboevolution.js.AbstractScriptableDelegate;

/**
 * he Position class provides Java implementation of the "Position Interface" as
 * detailed out in the W3C Specifications (http://www.w3.org/TR/geolocation-API/#position).
 *
 * Note: This class must not have any sub-classes to ensure W3C Specifications are being
 * strictly followed by the system or application that uses this geolocation package.
 */
public class Position extends AbstractScriptableDelegate {
	
	/*
	 * The position coordinates.
	 */
	private final Coordinates coords;
	
	/*
	 * The time (in milliseconds since January 1, 1970 GMT) when this position was found (or calculated). 
	 */
	private final long timestamp;
	
	/**
	 * Constructs a Position object.
	 *
	 * @param coords	the coordinates of the position.
	 * @param timestamp	the timestamp of the position.
	 */
	public Position(final Coordinates coords, final long timestamp) {
		this.coords = coords;
		this.timestamp = timestamp;
	}

	/**
	 * Returns the position coordinates.
	 *
	 * @return	the position coordinates.
	 */
	public Coordinates getCoords() {
		return coords;
	}

	/**
	 * Returns the time (in milliseconds since January 1, 1970 GMT) when this position was found (or
	 * calculated).
	 *
	 * @return a long.
	 */
	public long getTimestamp() {
		return timestamp;
	}
}
