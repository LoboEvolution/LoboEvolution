/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2017 Lobo Evolution

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
package org.lobobrowser.w3c.events;

import org.w3c.dom.views.AbstractView;


/**
 * The public interface FocusEvent.
 */
public interface FocusEvent extends UIEvent {
	// FocusEvent
	/**
	 * Gets the related target.
	 *
	 * @return the related target
	 */
	EventTarget getRelatedTarget();

	/**
	 * Inits the focus event.
	 *
	 * @param typeArg
	 *            the type arg
	 * @param canBubbleArg
	 *            the can bubble arg
	 * @param cancelableArg
	 *            the cancelable arg
	 * @param viewArg
	 *            the view arg
	 * @param detailArg
	 *            the detail arg
	 * @param relatedTargetArg
	 *            the related target arg
	 */
	void initFocusEvent(String typeArg, boolean canBubbleArg, boolean cancelableArg, AbstractView viewArg,
			int detailArg, EventTarget relatedTargetArg);
}
