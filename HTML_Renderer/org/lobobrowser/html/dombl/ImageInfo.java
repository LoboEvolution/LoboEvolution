/*
    GNU LESSER GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The XAMJ Project

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU Lesser General Public
    License as published by the Free Software Foundation; either
    version 2.1 of the License, or (at your option) any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net
*/
package org.lobobrowser.html.dombl;

import java.util.ArrayList;


/**
 * The Class ImageInfo.
 */
public class ImageInfo {
	// Access to this class is synchronized on imageInfos.
	/** The image event. */
	public ImageEvent imageEvent;
	
	/** The loaded. */
	public boolean loaded;
	
	/** The listeners. */
	private ArrayList<ImageListener> listeners = new ArrayList<ImageListener>(1);

	/**
	 * Adds the listener.
	 *
	 * @param listener the listener
	 */
	public void addListener(ImageListener listener) {
		this.listeners.add(listener);
	}

	/**
	 * Gets the listeners.
	 *
	 * @return the listeners
	 */
	public ImageListener[] getListeners() {
		return (ImageListener[]) this.listeners.toArray(ImageListener.EMPTY_ARRAY);
	}
}