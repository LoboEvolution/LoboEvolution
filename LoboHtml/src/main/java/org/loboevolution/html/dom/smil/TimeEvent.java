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

import org.w3c.dom.events.Event;
import org.w3c.dom.views.AbstractView;

/**
 * The TimeEvent interface provides specific contextual information
 * associated with Time events.
 *
 * @author utente
 * @version $Id: $Id
 */
public interface TimeEvent extends Event {
	/**
	 * The view attribute identifies the AbstractView
	 * from which the event was generated.
	 *
	 * @return a {@link org.w3c.dom.views.AbstractView} object.
	 */
    AbstractView getView();

	/**
	 * Specifies some detail information about the Event ,
	 * depending on the type of event.
	 *
	 * @return a int.
	 */
    int getDetail();

	/**
	 * The initTimeEvent method is used to initialize the value of
	 * a TimeEvent created through the DocumentEvent
	 * interface. This method may only be called before the
	 * TimeEvent has been dispatched via the
	 * dispatchEvent method, though it may be called multiple times
	 * during that phase if necessary. If called multiple times, the final
	 * invocation takes precedence.
	 *
	 * @param typeArg
	 *            Specifies the event type.
	 * @param viewArg
	 *            Specifies the Event 's AbstractView
	 *            .
	 * @param detailArg
	 *            Specifies the Event 's detail.
	 */
    void initTimeEvent(String typeArg, AbstractView viewArg, int detailArg);

}
