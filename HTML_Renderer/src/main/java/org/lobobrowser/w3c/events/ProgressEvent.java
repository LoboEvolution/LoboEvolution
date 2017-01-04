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


/**
 * The public interface ProgressEvent.
 */
public interface ProgressEvent extends Event {
	// ProgressEvent
	/**
	 * Gets the length computable.
	 *
	 * @return the length computable
	 */
	boolean getLengthComputable();

	/**
	 * Gets the loaded.
	 *
	 * @return the loaded
	 */
	int getLoaded();

	/**
	 * Gets the total.
	 *
	 * @return the total
	 */
	int getTotal();

	/**
	 * Inits the progress event.
	 *
	 * @param typeArg
	 *            the type arg
	 * @param canBubbleArg
	 *            the can bubble arg
	 * @param cancelableArg
	 *            the cancelable arg
	 * @param lengthComputableArg
	 *            the length computable arg
	 * @param loadedArg
	 *            the loaded arg
	 * @param totalArg
	 *            the total arg
	 */
	void initProgressEvent(String typeArg, boolean canBubbleArg, boolean cancelableArg, boolean lengthComputableArg,
			int loadedArg, int totalArg);
}
