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
/*
 * Created on Apr 15, 2005
 */
package org.lobobrowser.util;

import java.util.EventObject;

/**
 * The Class InputProgressEvent.
 *
 * @author J. H. S.
 */
public class InputProgressEvent extends EventObject {
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -4768022316827973086L;
	/** The progress. */
	private final int progress;

	/**
	 * Instantiates a new input progress event.
	 *
	 * @param arg0
	 *            the arg0
	 * @param progress
	 *            the progress
	 */
	public InputProgressEvent(Object arg0, int progress) {
		super(arg0);
		this.progress = progress;
	}

	/**
	 * Gets the progress.
	 *
	 * @return the progress
	 */
	public int getProgress() {
		return this.progress;
	}
}
