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
package org.loboevolution.gui;

import java.util.EventListener;

/**
 * A listener of content events.
 *
 * @see FramePanel#addContentListener(ContentListener)
 * @see FramePanel#getComponentContent()
 */
public interface ContentListener extends EventListener {

	/** The Constant EMPTY_ARRAY. */
	ContentListener[] EMPTY_ARRAY = new ContentListener[0];

	/**
	 * Called as soon as the content has been set in a {@link FramePanel}. Note
	 * that content can be set before the originating document has been fully
	 * loaded, for example when incremental rendering is performed.
	 *
	 * @param event
	 *            The content event.
	 */
	void contentSet(ContentEvent event);
}
