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

package org.loboevolution.html.js.geolocation;

/**
 * The PositionOptions class provides a way for the application to set options
 * that the Geolocation object can then use when calculating the position. These
 * include parameters such as enabling high accuracy, API timeout, cache age, etc.
 *
 * Note: This class must not have any sub-classes to ensure W3C Specifications are being
 * strictly followed by the system or application that uses this geolocation package. These
 * specifications are avilable at http://www.w3.org/TR/geolocation-API/#position-options
 *
 * @author utente
 * @version $Id: $Id
 */
public final class PositionOptions {

    /*
     * Maximum time allowed (in milliseconds) to calculate position.
     */
    private long timeout;
    
    /*
     * Time (in milliseconds) for which a position must be cached (and provided in case the 
     * application asks for the cached position).
     */
    private long maximumAge;
    	
	/**
	 * Returns value of the "timeout". This is the maximum time allowed (in milliseconds) to
	 * calculate position.
	 *
	 * @return	the "timeout" value.
	 */
	public long getTimeout() {
		return timeout;
	}
	
	/**
	 * Sets the "timeout". This is the maximum time allowed (in milliseconds) to calculate position.
	 *
	 * @param timeout	the "timeout" value.
	 */
	public void setTimeout(long timeout) {
		this.timeout = timeout;
	}
	
	/**
	 * Returns value of the "maximum age". This is the time (in milliseconds) for which a position
	 * must be cached (and provided in case the application asks for the cached position).
	 *
	 * @return	the "maximum age" value.
	 */
	public long getMaximumAge() {
		return maximumAge;
	}
	
	/**
	 * Sets the "maximum age". This is the time (in milliseconds) for which a position  must be cached
	 * (and provided in case the application asks for the cached position).
	 *
	 * @param maximumAge	the "maximum age" value.
	 */
	public void setMaximumAge(long maximumAge) {
		this.maximumAge = maximumAge;
	}
    
}
