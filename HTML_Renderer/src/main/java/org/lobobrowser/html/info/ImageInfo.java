/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2016 Lobo Evolution

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
package org.lobobrowser.html.info;

import java.io.Serializable;
import java.util.ArrayList;

import org.lobobrowser.html.dombl.ImageEvent;
import org.lobobrowser.html.dombl.ImageListener;

/**
 * The Class ImageInfo.
 */
public class ImageInfo implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -7078463952419036104L;

	/** The image event. */
	public ImageEvent imageEvent;

	/** The loaded. */
	public boolean loaded;

	/** The listeners. */
	private ArrayList<ImageListener> listeners = new ArrayList<ImageListener>(1);

	/**
	 * Adds the listener.
	 *
	 * @param listener
	 *            the listener
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
		return this.listeners.toArray(ImageListener.EMPTY_ARRAY);
	}
}
