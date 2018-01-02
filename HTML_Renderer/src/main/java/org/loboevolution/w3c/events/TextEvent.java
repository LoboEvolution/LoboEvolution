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
package org.loboevolution.w3c.events;

import org.w3c.dom.views.AbstractView;

/**
 * The public interface TextEvent.
 */
public interface TextEvent extends UIEvent {

	/**
	 * Gets the data.
	 *
	 * @return the data
	 */
	String getData();

	/**
	 * Inits the text event.
	 *
	 * @param typeArg
	 *            the type arg
	 * @param canBubbleArg
	 *            the can bubble arg
	 * @param cancelableArg
	 *            the cancelable arg
	 * @param viewArg
	 *            the view arg
	 * @param dataArg
	 *            the data arg
	 */
	void initTextEvent(String typeArg, boolean canBubbleArg, boolean cancelableArg, AbstractView viewArg,
			String dataArg);
}
