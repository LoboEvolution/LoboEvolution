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

package org.loboevolution.w3c.html;

import org.mozilla.javascript.Function;

/**
 * The Interface VideoTrackList.
 */
public interface VideoTrackList {

	/**
	 * Gets the length.
	 *
	 * @return the length
	 */
	// VideoTrackList
	public int getLength();

	/**
	 * Gets the element.
	 *
	 * @param index
	 *            the index
	 * @return the element
	 */
	public VideoTrack getElement(int index);

	/**
	 * Gets the track by id.
	 *
	 * @param id
	 *            the id
	 * @return the track by id
	 */
	public VideoTrack getTrackById(String id);

	/**
	 * Gets the selected index.
	 *
	 * @return the selected index
	 */
	public int getSelectedIndex();

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
