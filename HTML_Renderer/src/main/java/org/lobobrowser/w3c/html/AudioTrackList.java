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

package org.lobobrowser.w3c.html;

import org.mozilla.javascript.Function;

/**
 * The Interface AudioTrackList.
 */
public interface AudioTrackList {

	/**
	 * Gets the length.
	 *
	 * @return the length
	 */
	// AudioTrackList
	public int getLength();

	/**
	 * Gets the element.
	 *
	 * @param index
	 *            the index
	 * @return the element
	 */
	public AudioTrack getElement(int index);

	/**
	 * Gets the track by id.
	 *
	 * @param id
	 *            the id
	 * @return the track by id
	 */
	public AudioTrack getTrackById(String id);

	/**
	 * Gets the onchange.
	 *
	 * @return the onchange
	 */
	public Function getOnchange();

	/**
	 * Sets the onchange.
	 *
	 * @param onchange
	 *            the new onchange
	 */
	public void setOnchange(Function onchange);
}
