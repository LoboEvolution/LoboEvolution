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

package org.loboevolution.html.dom.smil;

import org.w3c.dom.DOMException;

/**
 * This interface defines the set of timing attributes that are common to all
 * timed elements.
 */
public interface ElementTime {

	// restartTypes
	public static final short RESTART_ALWAYS = 0;
	public static final short RESTART_NEVER = 1;
	public static final short RESTART_WHEN_NOT_ACTIVE = 2;

	// fillTypes
	public static final short FILL_REMOVE = 0;
	public static final short FILL_FREEZE = 1;

	/**
	 * The desired value (as a list of times) of the begin instant of this node.
	 * 
	 * @exception DOMException
	 *                NO_MODIFICATION_ALLOWED_ERR: Raised if this attribute is
	 *                readonly.
	 */
	public TimeList getBegin();

	public void setBegin(TimeList begin) throws DOMException;

	/**
	 * The list of active ends for this node.
	 * 
	 * @exception DOMException
	 *                NO_MODIFICATION_ALLOWED_ERR: Raised if this attribute is
	 *                readonly.
	 */
	public TimeList getEnd();

	public void setEnd(TimeList end) throws DOMException;

	/**
	 * The desired simple duration value of this node in seconds. Negative value
	 * means "indefinite".
	 * 
	 * @exception DOMException
	 *                NO_MODIFICATION_ALLOWED_ERR: Raised if this attribute is
	 *                readonly.
	 */
	public float getDur();

	public void setDur(float dur) throws DOMException;

	/**
	 * A code representing the value of the restart attribute, as defined above.
	 * Default value is <code>RESTART_ALWAYS</code> .
	 * 
	 * @exception DOMException
	 *                NO_MODIFICATION_ALLOWED_ERR: Raised if this attribute is
	 *                readonly.
	 */
	public short getRestart();

	public void setRestart(short restart) throws DOMException;

	/**
	 * A code representing the value of the fill attribute, as defined above.
	 * Default value is <code>FILL_REMOVE</code> .
	 * 
	 * @exception DOMException
	 *                NO_MODIFICATION_ALLOWED_ERR: Raised if this attribute is
	 *                readonly.
	 */
	public String getFill();

	public void setFill(String fill) throws DOMException;

	/**
	 * The repeatCount attribute causes the element to play repeatedly (loop)
	 * for the specified number of times. A negative value repeat the element
	 * indefinitely. Default value is 0 (unspecified).
	 * 
	 * @exception DOMException
	 *                NO_MODIFICATION_ALLOWED_ERR: Raised if this attribute is
	 *                readonly.
	 */
	public float getRepeatCount();

	public void setRepeatCount(float repeatCount) throws DOMException;

	/**
	 * The repeatDur causes the element to play repeatedly (loop) for the
	 * specified duration in milliseconds. Negative means "indefinite".
	 * 
	 * @exception DOMException
	 *                NO_MODIFICATION_ALLOWED_ERR: Raised if this attribute is
	 *                readonly.
	 */
	public float getRepeatDur();

	public void setRepeatDur(float repeatDur) throws DOMException;

	/**
	 * Causes this element to begin the local timeline (subject to sync
	 * constraints).
	 * 
	 * @return <code>true</code> if the method call was successful and the
	 *         element was begun. <code>false</code> if the method call failed.
	 *         Possible reasons for failure include: The element doesn't support
	 *         the <code>beginElement</code> method. (the
	 *         <code>beginEvent</code> attribute is not set to
	 *         <code>"undefinite"</code> ) The element is already active and
	 *         can't be restart when it is active. (the <code>restart</code>
	 *         attribute is set to <code>"whenNotActive"</code> ) The element is
	 *         active or has been active and can't be restart. (the
	 *         <code>restart</code> attribute is set to <code>"never"</code> ).
	 * 
	 */
	public boolean beginElement();

	/**
	 * Causes this element to end the local timeline (subject to sync
	 * constraints).
	 * 
	 * @return <code>true</code> if the method call was successful and the
	 *         element was endeed. <code>false</code> if method call failed.
	 *         Possible reasons for failure include: The element doesn't support
	 *         the <code>endElement</code> method. (the <code>endEvent</code>
	 *         attribute is not set to <code>"undefinite"</code> ) The element
	 *         is not active.
	 */
	public boolean endElement();

	/**
	 * Causes this element to pause the local timeline (subject to sync
	 * constraints).
	 */
	public void pauseElement();

	/**
	 * Causes this element to resume a paused local timeline.
	 */
	public void resumeElement();

	/**
	 * Seeks this element to the specified point on the local timeline (subject
	 * to sync constraints). If this is a timeline, this must seek the entire
	 * timeline (i.e. propagate to all timeChildren).
	 * 
	 * @param seekTo
	 *            The desired position on the local timeline in milliseconds.
	 */
	public void seekElement(float seekTo);

}
