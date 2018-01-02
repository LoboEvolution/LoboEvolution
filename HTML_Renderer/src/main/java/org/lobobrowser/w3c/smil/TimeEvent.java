/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2018 Lobo Evolution

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

package org.lobobrowser.w3c.smil;

import org.w3c.dom.events.Event;
import org.w3c.dom.views.AbstractView;

/**
 * The <code>TimeEvent</code> interface provides specific contextual information
 * associated with Time events.
 */
public interface TimeEvent extends Event {
	/**
	 * The <code>view</code> attribute identifies the <code>AbstractView</code>
	 * from which the event was generated.
	 */
	public AbstractView getView();

	/**
	 * Specifies some detail information about the <code>Event</code> ,
	 * depending on the type of event.
	 */
	public int getDetail();

	/**
	 * The <code>initTimeEvent</code> method is used to initialize the value of
	 * a <code>TimeEvent</code> created through the <code>DocumentEvent</code>
	 * interface. This method may only be called before the
	 * <code>TimeEvent</code> has been dispatched via the
	 * <code>dispatchEvent</code> method, though it may be called multiple times
	 * during that phase if necessary. If called multiple times, the final
	 * invocation takes precedence.
	 * 
	 * @param typeArg
	 *            Specifies the event type.
	 * @param viewArg
	 *            Specifies the <code>Event</code> 's <code>AbstractView</code>
	 *            .
	 * @param detailArg
	 *            Specifies the <code>Event</code> 's detail.
	 */
	public void initTimeEvent(String typeArg, AbstractView viewArg, int detailArg);

}
