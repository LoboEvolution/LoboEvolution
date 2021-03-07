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

import org.loboevolution.html.dom.nodeimpl.DOMException;

/**
 * This interface defines the set of timing attributes that are common to all
 * timed elements.
 *
 * @author utente
 * @version $Id: $Id
 */
public interface ElementTime {

	// restartTypes
	/** Constant RESTART_ALWAYS=0 */
	short RESTART_ALWAYS = 0;
	/** Constant RESTART_NEVER=1 */
	short RESTART_NEVER = 1;
	/** Constant RESTART_WHEN_NOT_ACTIVE=2 */
	short RESTART_WHEN_NOT_ACTIVE = 2;

	// fillTypes
	/** Constant FILL_REMOVE=0 */
	short FILL_REMOVE = 0;
	/** Constant FILL_FREEZE=1 */
	short FILL_FREEZE = 1;

	/**
	 * The desired value (as a list of times) of the begin instant of this node.
	 *
	 * @exception DOMException
	 *                NO_MODIFICATION_ALLOWED_ERR: Raised if this attribute is
	 *                readonly.
	 * @return a {@link org.loboevolution.html.dom.smil.TimeList} object.
	 */
	TimeList getBegin();

	/**
	 * <p>setBegin.</p>
	 *
	 * @param begin a {@link org.loboevolution.html.dom.smil.TimeList} object.
	 */
	default void setBegin(TimeList begin) {}

	/**
	 * The list of active ends for this node.
	 *
	 * @exception DOMException
	 *                NO_MODIFICATION_ALLOWED_ERR: Raised if this attribute is
	 *                readonly.
	 * @return a {@link org.loboevolution.html.dom.smil.TimeList} object.
	 */
	TimeList getEnd();

	/**
	 * <p>setEnd.</p>
	 *
	 * @param end a {@link org.loboevolution.html.dom.smil.TimeList} object.
	 */
	default void setEnd(TimeList end) {}

	/**
	 * The desired simple duration value of this node in seconds. Negative value
	 * means "indefinite".
	 *
	 * @exception DOMException
	 *                NO_MODIFICATION_ALLOWED_ERR: Raised if this attribute is
	 *                readonly.
	 * @return a float.
	 */
	float getDur();

	/**
	 * <p>setDur.</p>
	 *
	 * @param dur a float.
	 * @throws org.w3c.dom.DOMException if any.
	 */
	void setDur(float dur) throws DOMException;

	/**
	 * A code representing the value of the restart attribute, as defined above.
	 * Default value is RESTART_ALWAYS .
	 *
	 * @exception DOMException
	 *                NO_MODIFICATION_ALLOWED_ERR: Raised if this attribute is
	 *                readonly.
	 * @return a short.
	 */
	default short getRestart() {return 0;}

	/**
	 * <p>setRestart.</p>
	 *
	 * @param restart a short.
	 */
	default void setRestart(short restart) {}

    /**
	 * A code representing the value of the fill attribute, as defined above.
	 * Default value is FILL_REMOVE .
	 *
	 * @exception DOMException
	 *                NO_MODIFICATION_ALLOWED_ERR: Raised if this attribute is
	 *                readonly.
	 * @return a {@link java.lang.String} object.
	 */
	String getFill();

	/**
	 * <p>setFill.</p>
	 *
	 * @param fill a {@link java.lang.String} object.
	 * @throws org.w3c.dom.DOMException if any.
	 */
	void setFill(String fill) throws DOMException;

	/**
	 * The repeatCount attribute causes the element to play repeatedly (loop)
	 * for the specified number of times. A negative value repeat the element
	 * indefinitely. Default value is 0 (unspecified).
	 *
	 * @exception DOMException
	 *                NO_MODIFICATION_ALLOWED_ERR: Raised if this attribute is
	 *                readonly.
	 * @return a float.
	 */
	float getRepeatCount();

	/**
	 * <p>setRepeatCount.</p>
	 *
	 * @param repeatCount a float.
	 * @throws org.w3c.dom.DOMException if any.
	 */
	void setRepeatCount(float repeatCount) throws DOMException;

	/**
	 * The repeatDur causes the element to play repeatedly (loop) for the
	 * specified duration in milliseconds. Negative means "indefinite".
	 *
	 * @exception DOMException
	 *                NO_MODIFICATION_ALLOWED_ERR: Raised if this attribute is
	 *                readonly.
	 * @return a float.
	 */
	float getRepeatDur();

	/**
	 * <p>setRepeatDur.</p>
	 *
	 * @param repeatDur a float.
	 * @throws org.w3c.dom.DOMException if any.
	 */
	void setRepeatDur(float repeatDur) throws DOMException;

	/**
	 * Causes this element to begin the local timeline (subject to sync
	 * constraints).
	 *
	 * @return true if the method call was successful and the
	 *         element was begun. false if the method call failed.
	 *         Possible reasons for failure include: The element doesn't support
	 *         the beginElement method. (the
	 *         beginEvent attribute is not set to
	 *         "undefinite" ) The element is already active and
	 *         can't be restart when it is active. (the restart
	 *         attribute is set to "whenNotActive" ) The element is
	 *         active or has been active and can't be restart. (the
	 *         restart attribute is set to "never" ).
	 */
	boolean beginElement();

	/**
	 * Causes this element to end the local timeline (subject to sync
	 * constraints).
	 *
	 * @return true if the method call was successful and the
	 *         element was endeed. false if method call failed.
	 *         Possible reasons for failure include: The element doesn't support
	 *         the endElement method. (the endEvent
	 *         attribute is not set to "undefinite" ) The element
	 *         is not active.
	 */
	boolean endElement();

	/**
	 * Causes this element to pause the local timeline (subject to sync
	 * constraints).
	 */
	default void pauseElement() {}

	/**
	 * Causes this element to resume a paused local timeline.
	 */
	default void resumeElement() {}

	/**
	 * Seeks this element to the specified point on the local timeline (subject
	 * to sync constraints). If this is a timeline, this must seek the entire
	 * timeline (i.e. propagate to all timeChildren).
	 *
	 * @param seekTo
	 *            The desired position on the local timeline in milliseconds.
	 */
	default void seekElement(float seekTo) {}

}
