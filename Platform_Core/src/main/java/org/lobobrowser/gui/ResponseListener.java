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
package org.lobobrowser.gui;

import java.util.EventListener;

/**
 * A listener of response events.
 *
 * @see FramePanel#addResponseListener(ResponseListener)
 */
public interface ResponseListener extends EventListener {

	/** The Constant EMPTY_ARRAY. */
	ResponseListener[] EMPTY_ARRAY = new ResponseListener[0];

	/**
	 * This method is called as soon as a clientlet response intended for the
	 * event frame has been fully processed.
	 *
	 * @param event
	 *            A response event.
	 */
	void responseProcessed(ResponseEvent event);
}
