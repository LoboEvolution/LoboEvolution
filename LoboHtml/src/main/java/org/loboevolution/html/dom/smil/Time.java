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

package org.loboevolution.html.dom.smil;

import org.w3c.dom.DOMException;
import org.w3c.dom.Element;

/**
 * The Time interface is a datatype that represents times within
 * the timegraph. A Time has a type, key values to describe the
 * time, and a boolean to indicate whether the values are currently unresolved.
 * Still need to address the wallclock values.
 *
 * @author utente
 * @version $Id: $Id
 */
public interface Time {

	// TimeTypes
	/** Constant SMIL_TIME_INDEFINITE=0 */
    short SMIL_TIME_INDEFINITE = 0;

	/** Constant SMIL_TIME_OFFSET=1 */
    short SMIL_TIME_OFFSET = 1;

	/** Constant SMIL_TIME_SYNC_BASED=2 */
    short SMIL_TIME_SYNC_BASED = 2;

	/** Constant SMIL_TIME_EVENT_BASED=3 */
    short SMIL_TIME_EVENT_BASED = 3;

	/** Constant SMIL_TIME_WALLCLOCK=4 */
    short SMIL_TIME_WALLCLOCK = 4;

	/** Constant SMIL_TIME_MEDIA_MARKER=5 */
    short SMIL_TIME_MEDIA_MARKER = 5;

	/** Constant SMIL_TIME_REPEAT=6 */
    short SMIL_TIME_REPEAT = 6;

	/** Constant SMIL_TIME_ACCESSKEY=7 */
    short SMIL_TIME_ACCESSKEY = 7;
		
	/**
	 * A boolean indicating whether the current Time has been fully
	 * resolved to the document schedule. Note that for this to be true, the
	 * current Time must be defined (not indefinite), the syncbase
	 * and all Time 's that the syncbase depends on must be defined
	 * (not indefinite), and the begin Time of all ascendent time
	 * containers of this element and all Time elements that this
	 * depends upon must be defined (not indefinite). <br>
	 * If this Time is based upon an event, this Time
	 * will only be resolved once the specified event has happened, subject to
	 * the constraints of the time container. <br>
	 * Note that this may change from true to false when the parent time
	 * container ends its simple duration (including when it repeats or
	 * restarts).
	 *
	 * @return a boolean.
	 */
    boolean getResolved();

	/**
	 * The clock value in seconds relative to the parent time container begin.
	 * This indicates the resolved time relationship to the parent time
	 * container. This is only valid if resolved is true.
	 *
	 * @return a double.
	 */
    double getResolvedOffset();

	/**
	 * A code representing the type of the underlying object, as defined above.
	 *
	 * @return a short.
	 */
    short getTimeType();

	/**
	 * The clock value in seconds relative to the syncbase or eventbase. Default
	 * value is 0 .
	 *
	 * @exception DOMException
	 *                NO_MODIFICATION_ALLOWED_ERR: Raised on attempts to modify
	 *                this readonly attribute.
	 * @return a double.
	 */
    double getOffset();

	/**
	 * <p>setOffset.</p>
	 *
	 * @param offset a double.
	 * @throws org.w3c.dom.DOMException if any.
	 */
    void setOffset(double offset) throws DOMException;

	/**
	 * The base element for a sync-based or event-based time.
	 *
	 * @exception DOMException
	 *                NO_MODIFICATION_ALLOWED_ERR: Raised on attempts to modify
	 *                this readonly attribute.
	 * @return a {@link org.w3c.dom.Element} object.
	 */
    Element getBaseElement();

	/**
	 * <p>setBaseElement.</p>
	 *
	 * @param baseElement a {@link org.w3c.dom.Element} object.
	 * @throws org.w3c.dom.DOMException if any.
	 */
    void setBaseElement(Element baseElement) throws DOMException;

	/**
	 * If true , indicates that a sync-based time is relative to
	 * the begin of the baseElement. If false , indicates that a
	 * sync-based time is relative to the active end of the baseElement.
	 *
	 * @exception DOMException
	 *                NO_MODIFICATION_ALLOWED_ERR: Raised on attempts to modify
	 *                this readonly attribute.
	 * @return a boolean.
	 */
    boolean getBaseBegin();

	/**
	 * <p>setBaseBegin.</p>
	 *
	 * @param baseBegin a boolean.
	 * @throws org.w3c.dom.DOMException if any.
	 */
    void setBaseBegin(boolean baseBegin) throws DOMException;

	/**
	 * The name of the event for an event-based time. Default value is
	 * null .
	 *
	 * @exception DOMException
	 *                NO_MODIFICATION_ALLOWED_ERR: Raised on attempts to modify
	 *                this readonly attribute.
	 * @return a {@link java.lang.String} object.
	 */
    String getEvent();

	/**
	 * <p>setEvent.</p>
	 *
	 * @param event a {@link java.lang.String} object.
	 * @throws org.w3c.dom.DOMException if any.
	 */
    void setEvent(String event) throws DOMException;

	/**
	 * The name of the marker from the media element, for media marker times.
	 * Default value is null .
	 *
	 * @exception DOMException
	 *                NO_MODIFICATION_ALLOWED_ERR: Raised on attempts to modify
	 *                this readonly attribute.
	 * @return a {@link java.lang.String} object.
	 */
    String getMarker();

	/**
	 * <p>setMarker.</p>
	 *
	 * @param marker a {@link java.lang.String} object.
	 * @throws org.w3c.dom.DOMException if any.
	 */
    void setMarker(String marker) throws DOMException;

}
