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

/**
 * The Interface VideoTrack.
 */
public interface VideoTrack {

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	// VideoTrack
	public String getId();

	/**
	 * Gets the kind.
	 *
	 * @return the kind
	 */
	public String getKind();

	/**
	 * Gets the label.
	 *
	 * @return the label
	 */
	public String getLabel();

	/**
	 * Gets the language.
	 *
	 * @return the language
	 */
	public String getLanguage();

	/**
	 * Gets the selected.
	 *
	 * @return the selected
	 */
	public boolean getSelected();

	/**
	 * Sets the selected.
	 *
	 * @param selected
	 *            the new selected
	 */
	public void setSelected(boolean selected);
}
